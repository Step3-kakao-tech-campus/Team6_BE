package com.example.tripKo.domain.file.api;

import com.amazonaws.util.IOUtils;
import com.example.tripKo._core.S3.ImageS3Service;
import com.example.tripKo._core.security.data.JwtUserDetails;
import com.example.tripKo.domain.file.application.FileService;
import com.example.tripKo.domain.place.application.ContentsService;
import com.example.tripKo.domain.place.dto.response.info.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final ImageS3Service imageS3Service;

    @GetMapping("/file-initializer/qwerwer")
    public ResponseEntity<?> getRestaurantInfo() {
        boolean result = fileService.initImages();

        return ResponseEntity.ok(result);
    }
}
