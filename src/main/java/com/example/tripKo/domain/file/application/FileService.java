package com.example.tripKo.domain.file.application;

import com.amazonaws.util.IOUtils;
import com.example.tripKo._core.S3.ImageS3Service;
import com.example.tripKo._core.errors.exception.Exception500;
import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;

@RequiredArgsConstructor
@Service
public class FileService {
    private final ImageS3Service imageS3Service;

    public boolean initImages() {
        File file = new File(new File("").getAbsolutePath() + "image/restaurant/CocamomeMangmi/main.png");
        FileItem fileItem;

        try {
            fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            // Or faster..
            // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());

            //jpa.png -> multipart 변환
            MultipartFile  mFile = new CommonsMultipartFile(fileItem);
            imageS3Service.uploadImage(mFile);
        } catch (IOException ex) {
            throw new Exception500("이미지 저장을 실패하였습니다.");
        }

        return true;
    }
}
