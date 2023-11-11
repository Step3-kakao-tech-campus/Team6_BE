package com.example.tripKo.domain.place.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ReviewUpdateRequest {

  @NotNull
  private Long placeId;

  @NotNull
  private int rating;

  private String description;

  @Size(max = 10)
  List<MultipartFile> image = new ArrayList<>();

  List<String> deleteImage = new ArrayList<>();
}
