package com.example.tripKo.domain.place.api;

import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo._core.utils.ApiUtils;
import com.example.tripKo.domain.member.dao.MemberRepository;
import com.example.tripKo.domain.member.entity.Member;
import com.example.tripKo.domain.place.application.ReviewService;
import com.example.tripKo.domain.place.dto.response.search.PlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tripKo.domain.place.dto.request.ReviewRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final MemberRepository memberRepository;
    @PostMapping(path ="/restaurant/reviews", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createReview(/*@AuthenticationPrincipal MyUserDetails userDetails, */ @ModelAttribute @Valid ReviewRequest reviewRequest) {
        //Spring Security 개발되면 삭제
        //원래 userDetails에서 Member를 가져오므로 현재 userDetails에서 Member를 가져왔다고 가정합니다.
        Member member = memberRepository.findById(reviewRequest.getMemberId()).orElseThrow(() -> new Exception404("해당 id를 가진 회원을 찾을 수 없습니다: " + reviewRequest.getMemberId()));
        //Member member = userDetails.getMember()

        reviewService.createReview(reviewRequest, member);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }
}
