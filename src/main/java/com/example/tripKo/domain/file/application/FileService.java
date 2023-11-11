package com.example.tripKo.domain.file.application;

import com.amazonaws.util.IOUtils;
import com.example.tripKo._core.S3.ImageS3Service;
import com.example.tripKo._core.errors.exception.Exception500;
import com.example.tripKo.domain.file.dao.FileRepository;
import com.example.tripKo.domain.file.dto.FileRequest;
import com.example.tripKo.domain.place.entity.ReviewHasFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {
    private final ImageS3Service imageS3Service;
    private final FileRepository fileRepository;

    /*
    public boolean initImages(FileRequest fileRequest) {
        if (!fileRequest.getImage().isEmpty()) {
            List<File> fileEntities = new ArrayList<>();
            for(MultipartFile i : fileRequest.getImage()) {
                fileEntities.add(imageS3Service.uploadImage(i));
            }

            fileRepository.saveAll(fileEntities);
        }

        return true;
    }

     */

    public boolean initImages() {
        List<com.example.tripKo.domain.file.entity.File> fileEntities = new ArrayList<>();
        List<MultipartFile> mFiles = new ArrayList<>();

        try {
            File file = new File(new File("").getAbsolutePath() +
                    File.separator + "image" +
                    File.separator + "restaurant" +
                    File.separator + "CocamomeMangmi" +
                    File.separator + "main.png");
            FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            // Or faster..
            // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());

            mFiles.add(new CommonsMultipartFile(fileItem));
        } catch (IOException ex) {
            // do something.
        }


        for(MultipartFile i : mFiles) {
                fileEntities.add(imageS3Service.uploadImage(i));
        }

        fileRepository.saveAll(fileEntities);

        return true;
    }
}
