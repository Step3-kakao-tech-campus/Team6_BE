package com.example.tripKo.domain.place.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewRequest {
    @NotNull
    private Long placeId;

    @NotNull
    private Long memberId; //Spring Security 개발되면 삭제

    @NotNull
    private int rating;

    private String description;

    @Size(max = 10)
    List<MultipartFile> images = new ArrayList<>();
}
