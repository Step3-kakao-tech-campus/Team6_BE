package com.example.tripKo.domain.place.application;

import com.example.tripKo._core.errors.exception.Exception400;
import com.example.tripKo._core.errors.exception.Exception500;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.dao.ReviewRepository;
import com.example.tripKo.domain.place.dto.request.ReviewRequest;
import com.example.tripKo.domain.place.entity.ReviewHasFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public void createReview(ReviewRequest reviewRequest, Member member) {
        saveImages(reviewRequest.getImages());

    }

    private List<com.example.tripKo.domain.file.entity.File> saveImages(List<MultipartFile> images) {
        List<com.example.tripKo.domain.file.entity.File> fileEntities = new ArrayList<>();

        String imagesPath = new File("").getAbsolutePath() + File.separator
                + "src" + File.separator
                + "main" + File.separator
                + "resources" + File.separator
                + "reviews" + File.separator
                + "images";

        File imageFile = new File(imagesPath);

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
