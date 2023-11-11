package com.example.tripKo.domain.place.application;

import com.example.tripKo._core.S3.ImageS3Service;
import com.example.tripKo._core.errors.exception.BusinessException;
import com.example.tripKo._core.errors.exception.Exception400;
import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo._core.errors.exception.Exception500;
import com.example.tripKo.domain.file.dao.FileRepository;
import com.example.tripKo.domain.member.MemberReservationStatus;
import com.example.tripKo.domain.member.dao.MemberReservationInfoRepository;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.member.entity.MemberReservationInfo;
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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import static com.example.tripKo._core.errors.ErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final FileRepository fileRepository;
    private final PlaceRepository placeRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final MemberReservationInfoRepository memberReservationInfoRepository;
    private final ImageS3Service imageS3Service;

  @Transactional
  public void createPlaceReview(ReviewRequest reviewRequest, PlaceType placeType, Member member) {
    //place 불러오기
    //만약 받은 placeId가 URL의 PlaceType과 다르면 리뷰 작성 불가능
    Place place = placeRepository.findById(reviewRequest.getPlaceId())
        .orElseThrow(() -> new BusinessException(reviewRequest.getPlaceId(), "id", PLACE_ID_CANNOT_FOUND));
      if (place.getPlaceType() != placeType) {
          throw new BusinessException(place.getPlaceType(), "type", PLACE_NOT_MATCH);
      }

    //관광지 -> usageDate 작성 기준
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String usageDate = date.format(new Date()).split(" ")[0];

    //식당 & 축제 -> usageDate 예약 기준, 리뷰 제한
    if (placeType == PlaceType.RESTAURANT || placeType == PlaceType.FESTIVAL) {
      MemberReservationInfo memberReservationInfo = memberReservationInfoRepository.findByMemberAndPlaceAndStatus(
          member, place, MemberReservationStatus.예약완료);
      //예약 완료인지 체크
      if (Objects.isNull(memberReservationInfo)) {
        throw new BusinessException(null, "", REVIEW_BEFORE_RESERVATION);
      }
      String reservedDate = memberReservationInfo.getReservationDate();
      String reservedTime = memberReservationInfo.getReservationTime() + ":00";

      //리뷰 작성 날짜가 예약 날짜보다 앞인 경우 예외 처리
      Timestamp ts1 = Timestamp.valueOf(date.format(new Date()));
      Timestamp ts2 = Timestamp.valueOf(reservedDate + " " + reservedTime);
      //시간도 체크
      if (ts1.before(ts2)) {
          throw new BusinessException(ts2, "time", REVIEW_BEFORE_RESERVATION_TIME);
      } else {
        usageDate = reservedDate;
      }

      //리뷰 완료 상태일 경우 리뷰 작성 불가
      String status = memberReservationInfo.getStatus().name();
      //같은 날짜에 동일한 가게를 리뷰했었다면 더이상 리뷰 작성 불가능
      Review sameReview = reviewRepository.findReviewByMemberIdAndPlaceIdAndUsageDate(member.getId(),
          reviewRequest.getPlaceId(), usageDate);
        if (!Objects.isNull(sameReview) || status.equals(MemberReservationStatus.리뷰완료.name())) {
            throw new BusinessException(sameReview.getId(), "id", REVIEW_ALREADY_DONE);
        }

      memberReservationInfo.setStatus(MemberReservationStatus.리뷰완료);
    }
    //리뷰 별점 6점 이상일 경우 리뷰 작성 불가
      if (reviewRequest.getRating() > 5 || reviewRequest.getRating() < 1) {
          throw new Exception400("리뷰 별점은 1~5점 사이어야 합니다.");
      }

    Review review = Review.builder()
        .placeType(placeType)
        .place(place)
        .member(member)
        .score(reviewRequest.getRating())
        .description(reviewRequest.getDescription())
        .usageDate(usageDate)
        .build();

    reviewRepository.save(review);


        //리뷰에 이미지가 있다면 이미지를 리소스 폴더에 저장하고 정보를 File 테이블에 저장
        if (!reviewRequest.getImage().isEmpty()) {
            List<com.example.tripKo.domain.file.entity.File> fileEntities = new ArrayList<>();
            for(MultipartFile i : reviewRequest.getImage()) {
                fileEntities.add(imageS3Service.uploadImage(i));
            }

            fileRepository.saveAll(fileEntities);

            List<ReviewHasFile> reviewHasFiles = createReviewHasFile(fileEntities, review);

            reviewFileRepository.saveAll(reviewHasFiles);
        }

    //업데이트 된 평균 별점 저장
    int reviewNumbers = place.getReviewNumbers();
    double average = place.getAverageRating();

    //실수 -> 정수 과정에서 데이터가 손실될 수도 있을 것 같다.
    average = ((double) reviewRequest.getRating() + average * reviewNumbers) / (reviewNumbers + 1);
    average = Math.round(average * 10) / 10.0;
    place.setReviewNumbers(reviewNumbers + 1);
    place.setAverageRating(average);
  }

  @Transactional
  public ReviewsResponse getReviewsByPlaceId(Long placeId, PlaceType placeType, int page) {
    //리뷰에 저장할 placeRestaurant을 가져오는 부분
    Place place = placeRepository.findById(placeId)
        .orElseThrow(() -> new BusinessException(placeId, "id", PLACE_ID_CANNOT_FOUND));
      if (place.getPlaceType() != placeType) {
          throw new BusinessException(place.getPlaceType(), "type", PLACE_NOT_MATCH);
      }

    Pageable pageable = PageRequest.of(page, 10);
    List<Review> reviews = reviewRepository.findAllByPlaceId(place.getId(), pageable);

    if (reviews.isEmpty()) {
        if (page == 0) {
//            throw new Exception404("현재 이 플레이스는 리뷰가 없습니다. id : " + placeId);
        } else {
            throw new BusinessException(page, "page", REVIEW_PAGE_CANNOT_FOUND);
        }
    }

    ReviewsResponse reviewsResponse = new ReviewsResponse(reviews, place);

    return reviewsResponse;
  }

  @Transactional
  public void updateReview(Long reviewId, ReviewUpdateRequest reviewUpdateRequest) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new BusinessException(reviewId, "id", REVIEW_CANNOT_FOUND));

    Place place = placeRepository.findById(reviewUpdateRequest.getPlaceId())
        .orElseThrow(() -> new BusinessException(reviewUpdateRequest.getPlaceId(), "id", PLACE_ID_CANNOT_FOUND));

    int originalRate = review.getScore();
    review.update(reviewUpdateRequest);

        List<ReviewHasFile> reviewHasFiles = deleteImages(reviewId, reviewUpdateRequest.getDeleteImage());
        reviewFileRepository.deleteAll(reviewHasFiles);

        //리뷰에 이미지가 있다면 이미지를 리소스 폴더에 저장하고 정보를 File 테이블에 저장
        if (!reviewUpdateRequest.getImage().isEmpty()) {
            List<com.example.tripKo.domain.file.entity.File> fileEntities = new ArrayList<>();
            for(MultipartFile i : reviewUpdateRequest.getImage()) {
                fileEntities.add(imageS3Service.uploadImage(i));
            }
            fileRepository.saveAll(fileEntities);
            List<ReviewHasFile> reviewHasFilesForSave = createReviewHasFile(fileEntities, review);
            reviewFileRepository.saveAll(reviewHasFilesForSave);
        }

    //평균 별점 업데이트
    int reviewNumbers = place.getReviewNumbers();
    double average = place.getAverageRating();

    average = ((double) reviewUpdateRequest.getRating() + (average * reviewNumbers) - originalRate) / (reviewNumbers);
    average = Math.round(average * 10) / 10.0;
    place.setReviewNumbers(reviewNumbers);
    place.setAverageRating(average);

  }

  @Transactional
  public void deleteReview(Long reviewId) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new BusinessException(reviewId, "id", REVIEW_CANNOT_FOUND));
    Place place = placeRepository.findById(review.getPlace().getId()).orElseThrow();

    deleteAllImages(reviewId);

    int reviewNumbers = place.getReviewNumbers();
    double average = place.getAverageRating();

    if (reviewNumbers == 1) {
      average = 0;
    } else {
      average = ((double) average * reviewNumbers - review.getScore()) / (reviewNumbers - 1);
      average = Math.round(average * 10) / 10.0;
    }

    place.setReviewNumbers(reviewNumbers - 1);
    place.setAverageRating(average);

    reviewFileRepository.deleteAllByReviewId(reviewId);
    reviewRepository.delete(review);
  }


  private List<ReviewHasFile> createReviewHasFile(List<com.example.tripKo.domain.file.entity.File> fileEntities,
      Review review) {
    List<ReviewHasFile> reviewHasFiles = new ArrayList<>();

    for (com.example.tripKo.domain.file.entity.File fileEntity : fileEntities) {
      reviewHasFiles.add(ReviewHasFile.builder().review(review).file(fileEntity).build());
    }

    return reviewHasFiles;
  }

//    private List<com.example.tripKo.domain.file.entity.File> saveImages(List<MultipartFile> images) {
//        List<com.example.tripKo.domain.file.entity.File> fileEntities = new ArrayList<>();
//
//        //이미지가 저장될 경로는 /src/main/resources/reviews/images/
//        String imagesPath = new File("").getAbsolutePath() + File.separator
//                + "src" + File.separator
//                + "main" + File.separator
//                + "resources" + File.separator
//                + "reviews" + File.separator
//                + "images";
//
//        File imageFile = new File(imagesPath);
//
//        //resources/review/images에 디렉토리 생성
//        if (!imageFile.exists()) {
//            boolean isExists = imageFile.mkdirs();
//            if (!isExists) {
//                throw new Exception500("경로 생성에 실패하였습니다.");
//            }
//        }
//
//        for (MultipartFile image : images) {
//            String contentType = image.getContentType();
//            String imageName = image.getOriginalFilename();
//            String fileExtension;
//
//            //jpeg, jpg, png만 허용
//            if (Objects.isNull(contentType)) {
//                throw new Exception400("올바르지 않은 파일 확장자 형식입니다.");
//            }
//            else if (contentType.contains("image/jpeg") ||
//                    contentType.contains("image/jpg"))
//                fileExtension = ".jpg";
//            else if (contentType.contains("image/png"))
//                fileExtension = ".png";
//            else throw new Exception400("올바르지 않은 파일 확장자 형식입니다.");
//
//            String newFileName = String.format("%1$016x",System.nanoTime()) + "_" + imageName;
//            System.out.println("====================");
//            System.out.println(newFileName);
//
//            com.example.tripKo.domain.file.entity.File fileEntity = com.example.tripKo.domain.file.entity.File.builder()
//                    .type(contentType)
//                    .name(newFileName)
//                    .build();
//
//            fileEntities.add(fileEntity);
//
//            String imagePath = imagesPath + File.separator + newFileName;
//            File savedImage = new File(imagePath);
//            try {
//                image.transferTo(savedImage);
//            } catch (IOException e) {
//                throw new Exception500("이미지를 저장하는 중 문제가 발생하였습니다.");
//            }
//        }
//
//        return fileEntities;
//    }


    private List<ReviewHasFile> deleteImages(Long reviewId, List<String> deleteImage) {
        List<ReviewHasFile> reviewHasFiles = reviewFileRepository.findAllByReviewId(reviewId);
        List<ReviewHasFile> reviewHasFilesForReturn = new ArrayList<>();
        // cascade로 대체
        for(ReviewHasFile reviewHasFile : reviewHasFiles) {
            if (deleteImage.contains(reviewHasFile.getFile().getUrl())) {
                com.example.tripKo.domain.file.entity.File fileEntity = fileRepository.findById(reviewHasFile.getFile().getId())
                        .orElseThrow(() -> new Exception404("지울 파일이 없습니다. id : " + reviewHasFile.getFile().getId()));
                imageS3Service.deleteImageFromS3(fileEntity.getUrl());
                reviewHasFilesForReturn.add(reviewHasFile);
            }
        }
        return reviewHasFilesForReturn;
    }

    private void deleteAllImages(Long reviewId) {
        List<ReviewHasFile> reviewHasFiles = reviewFileRepository.findAllByReviewId(reviewId);

        // cascade로 대체
        for(ReviewHasFile reviewHasFile : reviewHasFiles) {
            com.example.tripKo.domain.file.entity.File fileEntity = fileRepository.findById(reviewHasFile.getFile().getId())
                    .orElseThrow(() -> new Exception404("지울 파일이 없습니다. id : " + reviewHasFile.getFile().getId()));

            imageS3Service.deleteImageFromS3(fileEntity.getUrl());
        }
    }

}
