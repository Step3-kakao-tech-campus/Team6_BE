package com.example.tripKo.domain.place.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
