package com.example.tripKo.domain.contents.api;

import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.contents.application.ContentsService;
import com.example.tripKo.domain.contents.dto.response.FestivalResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class ContentsController {

  private final ContentsService contentsService;

  @GetMapping("/festival/{id}")
  public ResponseEntity<?> getFestivalInfo(
      @PathVariable long id
  ) {
    FestivalResponse response = contentsService.getFestivalInfo(id);
    ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
    return ResponseEntity.ok(apiResult);
  }

}
