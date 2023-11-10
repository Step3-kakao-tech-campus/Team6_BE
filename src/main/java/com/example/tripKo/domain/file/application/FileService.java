package com.example.tripKo.domain.file.application;

import com.amazonaws.util.IOUtils;
import com.example.tripKo._core.S3.ImageS3Service;
import com.example.tripKo._core.errors.exception.Exception500;
import com.example.tripKo.domain.file.dao.FileRepository;
import com.example.tripKo.domain.file.dto.FileRequest;
import com.example.tripKo.domain.file.entity.File;
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
}
