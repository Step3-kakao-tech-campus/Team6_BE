package com.example.tripKo.domain.member.dto.request;

import com.example.tripKo.domain.member.MemberRoleType;
import com.example.tripKo.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class MemberRequest {
    @Getter
    @Setter
    public static class JoinDTO {
        private String email;
        private String password;
        private String memberId;

        public Member toEntity() {
            return Member.builder()
                    .realName("김철수")
                    .nickName("김철수")
                    .birthday("970905")
                    .emailAddress(email)
                    .password(password)
                    .memberId(memberId)
                    .role(MemberRoleType.MEMBER)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private String memberId;
        private String password;
    }

}
