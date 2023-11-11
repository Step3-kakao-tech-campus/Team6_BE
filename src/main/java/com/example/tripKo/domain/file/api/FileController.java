package com.example.tripKo.domain.file.api;

import com.amazonaws.util.IOUtils;
import com.example.tripKo._core.S3.ImageS3Service;
import com.example.tripKo._core.security.data.JwtUserDetails;
import com.example.tripKo.domain.file.application.FileService;
import com.example.tripKo.domain.file.dto.FileRequest;
import com.example.tripKo.domain.place.application.ContentsService;
import com.example.tripKo.domain.place.dto.request.ReviewRequest;
import com.example.tripKo.domain.place.dto.response.info.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileInputStream;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final ImageS3Service imageS3Service;

    /*
    @PostMapping(path = "/file-init/yzzxet", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> getRestaurantInfo(@ModelAttribute FileRequest fileRequest) {
        boolean result = fileService.initImages(fileRequest);

        return ResponseEntity.ok(result);
    }

     */
    @PostMapping(path = "/file-init/yzzxet")
    public ResponseEntity<?> getRestaurantInfo() {
        boolean result = fileService.initImages();

        return ResponseEntity.ok(result);
    }
}
