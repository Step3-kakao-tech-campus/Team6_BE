package com.example.tripKo.domain.member.dto.request.userInfo;

import com.example.tripKo.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.io.File;

import static com.example.tripKo.domain.member.entity.Member.*;
import static com.example.tripKo.domain.member.entity.Member.REAL_NAME_INVALID;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PACKAGE)
@NoArgsConstructor(access = PRIVATE)
public class UserInfoRequest {
    @Pattern(regexp = REAL_NAME_REGEX, message = REAL_NAME_INVALID)
    private String name;
    @Pattern(regexp = NICK_NAME_REGEX, message = NICK_NAME_INVALID)
    private String nickName;

    private String email;

}
