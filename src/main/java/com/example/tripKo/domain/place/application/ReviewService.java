package com.example.tripKo.domain.place.application;

import com.example.tripKo._core.errors.exception.Exception400;
import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo._core.errors.exception.Exception500;
import com.example.tripKo.domain.file.dao.FileRepository;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.PlaceType;
import com.example.tripKo.domain.place.dao.PlaceRepository;
import com.example.tripKo.domain.place.dao.ReviewFileRepository;
import com.example.tripKo.domain.place.dao.ReviewRepository;
import com.example.tripKo.domain.place.dto.request.ReviewRequest;
import com.example.tripKo.domain.place.dto.request.ReviewUpdateRequest;
import com.example.tripKo.domain.place.dto.response.review.ReviewsResponse;
import com.example.tripKo.domain.place.entity.Place;
import com.example.tripKo.domain.place.entity.Review;
import com.example.tripKo.domain.place.entity.ReviewHasFile;
import java.util.stream.Collectors;
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
    private final PlaceRepository placeRepository;
    private final ReviewFileRepository reviewFileRepository;

    @Transactional
    public void createPlaceReview(ReviewRequest reviewRequest, PlaceType placeType, Member member) {
        //place 불러오기
        //만약 받은 placeId가 URL의 PlaceType과 다르면 리뷰 작성 불가능
        Place place = placeRepository.findById(reviewRequest.getPlaceId()).orElseThrow(() -> new Exception404("해당하는 플레이스를 찾을 수 없습니다. id : " + reviewRequest.getPlaceId()));
        if (place.getPlaceType() != placeType) throw new Exception400("요청한 URL의 Place 타입과 요청한 id의 Place 타입이 다릅니다. 요청한 URL : " + placeType + ", id의 Place 타입 : " + place.getPlaceType());

        //usageDate는 일단 review를 작성한 날짜로 하였다. 나중에 로직 수정 필요
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String usageDate = LocalDate.now().format(formatter);

        //같은 날짜에 동일한 가게를 리뷰했었다면 더이상 리뷰 작성 불가능
        Review sameReview = reviewRepository.findReviewByMemberIdAndPlaceIdAndUsageDate(reviewRequest.getMemberId(), reviewRequest.getPlaceId(), usageDate);
        if (!Objects.isNull(sameReview)) throw new Exception400("이미 작성한 리뷰가 존재합니다.");

        Review review = Review.builder()
                .place(place)
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
        int reviewNumbers = place.getReviewNumbers();
        float average = place.getAverageRating();

        //실수 -> 정수 과정에서 데이터가 손실될 수도 있을 것 같다.
        average = ((float)reviewRequest.getRating() + average * reviewNumbers) / (reviewNumbers + 1);
        place.setReviewNumbers(reviewNumbers + 1);
        place.setAverageRating(average);
    }

    public ReviewsResponse getReviewsByPlaceId(Long placeId, PlaceType placeType, int page) {
        //리뷰에 저장할 placeRestaurant을 가져오는 부분
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new Exception404("해당하는 플레이스를 찾을 수 없습니다. id : " + placeId));
        if (place.getPlaceType() != placeType) throw new Exception400("요청한 URL의 Place 타입과 요청한 id의 Place 타입이 다릅니다. 요청한 URL : " + placeType + ", id의 Place 타입 : " + place.getPlaceType());

        Pageable pageable = PageRequest.of(page, 10);
        List<Review> reviews = reviewRepository.findAllByPlaceId(place.getId(), pageable);

        if (reviews.isEmpty()) {
            if (page == 0) throw new Exception404("현재 이 플레이스는 리뷰가 없습니다. id : " + placeId);
            else throw new Exception404("더이상 리뷰가 없습니다. page : " + page);
        }

        ReviewsResponse reviewsResponse = new ReviewsResponse(reviews, place);

        return reviewsResponse;
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewUpdateRequest reviewUpdateRequest) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception404("해당하는 리뷰를 찾을 수 없습니다. id : " + reviewId));

        Place place = placeRepository.findById(reviewUpdateRequest.getPlaceId())
                .orElseThrow(() -> new Exception404("해당하는 플레이스를 찾을 수 없습니다. id : " + reviewUpdateRequest.getPlaceId()));

        review.update(reviewUpdateRequest);


        // 이미지 파일을 업로드한 시간 정보가 들어간 이름으로 저장해서 업데이트한 사진이 기존 사진과 같은지 알 수 없음 -> 다 지우고 다시 저장
        deleteImages(reviewId);

        if (!reviewUpdateRequest.getImages().isEmpty()) {
            List<com.example.tripKo.domain.file.entity.File> fileEntities = saveImages(reviewUpdateRequest.getImages());

            List<ReviewHasFile> reviewHasFiles = createReviewHasFile(fileEntities, review);

            fileRepository.saveAll(fileEntities);
            reviewFileRepository.saveAll(reviewHasFiles);
        }

        //평균 별점 업데이트
        int reviewNumbers = place.getReviewNumbers();
        float average = place.getAverageRating();

        average = ((float)reviewUpdateRequest.getRating() + average * reviewNumbers) / (reviewNumbers + 1);
        place.setReviewNumbers(reviewNumbers + 1);
        place.setAverageRating(average);

    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception404("해당하는 리뷰를 찾을 수 없습니다. id : " + reviewId));
        Place place = placeRepository.findById(review.getPlace().getId()).orElseThrow();

        int reviewNumbers = place.getReviewNumbers();
        float average = place.getAverageRating();

        average = ((float) average * reviewNumbers - review.getScore()) / (reviewNumbers - 1);
        place.setReviewNumbers(reviewNumbers - 1);
        place.setAverageRating(average);

        reviewRepository.deleteById(reviewId);
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

    private void deleteImages(Long reviewId) {


        String imagesPath = new File("").getAbsolutePath() + File.separator
                + "src" + File.separator
                + "main" + File.separator
                + "resources" + File.separator
                + "reviews" + File.separator
                + "images";

        File imageFile = new File(imagesPath);

        if (!imageFile.exists()) {
            throw new Exception500("삭제할 이미지가 없습니다.");
        }


        List<ReviewHasFile> reviewHasFiles = reviewFileRepository.findAllByReviewId(reviewId);

        // 파일 엔티티들 먼저 지우고
        for(ReviewHasFile reviewHasFile : reviewHasFiles) {
            com.example.tripKo.domain.file.entity.File fileEntity = fileRepository.findById(reviewHasFile.getFile().getId())
                    .orElseThrow(() -> new Exception404("지울 파일이 없습니다. id : " + reviewHasFile.getFile().getId()));

            String fileName = fileEntity.getName();

            String imagePath = imagesPath + File.separator + fileName;
            File imageToDelete = new File(imagePath);

            if (imageToDelete.exists()) {
                if (imageToDelete.delete()) {
                    System.out.println("File deleted successfully: " + imageToDelete.getAbsolutePath());
                } else {
                    throw new Exception500("이미지를 삭제하는 중 문제가 발생하였습니다.");
                }
            } else {
                throw new Exception500("삭제하려는 이미지가 없습니다.");
            }

            fileRepository.deleteById(reviewHasFile.getFile().getId());
        }
        // 리뷰 파일 엔티티들 지우기
        reviewFileRepository.deleteAllByReviewId(reviewId);
    }

}
