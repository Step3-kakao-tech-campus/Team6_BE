package com.example.tripKo.domain.place.application;

import com.example.tripKo._core.errors.exception.Exception400;
import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo._core.errors.exception.Exception500;
import com.example.tripKo.domain.file.dao.FileRepository;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.dao.PlaceRepository;
import com.example.tripKo.domain.place.dao.PlaceRestaurantRepository;
import com.example.tripKo.domain.place.dao.ReviewFileRepository;
import com.example.tripKo.domain.place.dao.ReviewRepository;
import com.example.tripKo.domain.place.dto.request.ReviewRequest;
import com.example.tripKo.domain.place.dto.response.review.ReviewsResponse;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.PlaceRestaurant;
import com.example.tripKo.domain.place.entity.Review;
import com.example.tripKo.domain.place.entity.ReviewHasFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final FileRepository fileRepository;
    private final PlaceRestaurantRepository placeRestaurantRepository;
    private final ReviewFileRepository reviewFileRepository;

    @Transactional
    public void createPlaceRestaurantReview(ReviewRequest reviewRequest, Member member) {
        //API에서는 placeId로 되어 있는데 placeRestaurantId으로 받아야 할 것 같다.
        //리뷰에 저장할 placeRestaurant을 가져오는 부분
        Long placeRestaurantId = reviewRequest.getPlaceId();
        PlaceRestaurant placeRestaurant = placeRestaurantRepository.findById(placeRestaurantId)
                .orElseThrow(() -> new Exception404("해당하는 식당을 찾을 수 없습니다. id : " + placeRestaurantId));

        //usageDate는 일단 review를 작성한 날짜로 하였다. 나중에 로직 수정 필요
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String usageDate = LocalDate.now().format(formatter);

        //같은 날짜에 동일한 가게를 리뷰했었다면 더이상 리뷰 작성 불가능
        Review sameReview = reviewRepository.findReviewByMemberIdAndPlaceIdAndUsageDate(reviewRequest.getMemberId(), placeRestaurant.getPlace().getId(), usageDate);
        if (!Objects.isNull(sameReview)) throw new Exception400("이미 작성한 리뷰가 존재합니다.");

        Review review = Review.builder()
                .placeRestaurant(placeRestaurant)
                .member(member)
                .score(reviewRequest.getRating())
                .description(reviewRequest.getDescription())
                .build();

        reviewRepository.save(review);

        //리뷰에 이미지가 있다면 이미지를 리소스 폴더에 저장하고 정보를 File 테이블에 저장
        if (!reviewRequest.getImages().isEmpty()) {
            List<com.example.tripKo.domain.file.entity.File> fileEntities = saveImages(reviewRequest.getImages());

            List<ReviewHasFile> reviewHasFiles = createReviewHasFile(fileEntities, review);

            fileRepository.saveAll(fileEntities);
            reviewFileRepository.saveAll(reviewHasFiles);
        }

        //업데이트 된 평균 별점 저장
        Place place = placeRestaurant.getPlace();

        int reviewNumbers = place.getReviewNumbers();
        float average = place.getAverageRating();

        //실수 -> 정수 과정에서 데이터가 손실될 수도 있을 것 같다.
        average = ((float)reviewRequest.getRating() + average * reviewNumbers) / (reviewNumbers + 1);
        place.setReviewNumbers(reviewNumbers + 1);
        place.setAverageRating(average);

        placeRestaurantRepository.save(placeRestaurant);
    }

    public ReviewsResponse getPlaceRestaurantReviewsByPlaceRestaurantId(Long placeRestaurantId, int page) {
        //리뷰에 저장할 placeRestaurant을 가져오는 부분
        PlaceRestaurant placeRestaurant = placeRestaurantRepository.findById(placeRestaurantId)
                .orElseThrow(() -> new Exception404("해당하는 식당을 찾을 수 없습니다. id : " + placeRestaurantId));

        Pageable pageable = PageRequest.of(page, 10);
        List<Review> reviews = reviewRepository.findAllByPlaceId(placeRestaurant.getPlace().getId(), pageable);

        if (reviews.isEmpty()) throw new Exception404("현재 이 식당은 리뷰가 없습니다. id : " + placeRestaurantId);

        ReviewsResponse reviewsResponse = new ReviewsResponse(reviews, placeRestaurant.getPlace());

        return reviewsResponse;
    }

    private List<ReviewHasFile> createReviewHasFile(List<com.example.tripKo.domain.file.entity.File> fileEntities, Review review) {
        List<ReviewHasFile> reviewHasFiles = new ArrayList<>();

        for (com.example.tripKo.domain.file.entity.File fileEntity : fileEntities) {
            reviewHasFiles.add(ReviewHasFile.builder().review(review).file(fileEntity).build());
        }

        return reviewHasFiles;
    }



    private List<com.example.tripKo.domain.file.entity.File> saveImages(List<MultipartFile> images) {
        List<com.example.tripKo.domain.file.entity.File> fileEntities = new ArrayList<>();

        //이미지가 저장될 경로는 /src/main/resources/reviews/images/
        String imagesPath = new File("").getAbsolutePath() + File.separator
                + "src" + File.separator
                + "main" + File.separator
                + "resources" + File.separator
                + "reviews" + File.separator
                + "images";

        File imageFile = new File(imagesPath);

        //resources/review/images에 디렉토리 생성
        if (!imageFile.exists()) {
            boolean isExists = imageFile.mkdirs();
            if (!isExists) {
                throw new Exception500("경로 생성에 실패하였습니다.");
            }
        }

        for (MultipartFile image : images) {
            String contentType = image.getContentType();
            String imageName = image.getOriginalFilename();
            String fileExtension;

            //jpeg, jpg, png만 허용
            if (Objects.isNull(contentType)) {
                throw new Exception400("올바르지 않은 파일 확장자 형식입니다.");
            }
            else if (contentType.contains("image/jpeg") ||
                    contentType.contains("image/jpg"))
                fileExtension = ".jpg";
            else if (contentType.contains("image/png"))
                fileExtension = ".png";
            else throw new Exception400("올바르지 않은 파일 확장자 형식입니다.");

            String newFileName = String.format("%1$016x",System.nanoTime()) + "_" + imageName;
            System.out.println("====================");
            System.out.println(newFileName);

            com.example.tripKo.domain.file.entity.File fileEntity = com.example.tripKo.domain.file.entity.File.builder()
                    .type(contentType)
                    .name(newFileName)
                    .build();

            fileEntities.add(fileEntity);

            String imagePath = imagesPath + File.separator + newFileName;
            File savedImage = new File(imagePath);
            try {
                image.transferTo(savedImage);
            } catch (IOException e) {
                throw new Exception500("이미지를 저장하는 중 문제가 발생하였습니다.");
            }
        }

        return fileEntities;
    }
}
