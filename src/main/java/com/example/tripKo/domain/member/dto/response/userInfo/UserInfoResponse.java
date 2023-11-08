package com.example.tripKo.domain.member.dto.response.userInfo;

import com.example.tripKo.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.io.File;

@Getter
public class UserInfoResponse {
    private Long id;
    private String name;
    private String nickName;
    private String email;
    private String image;
    private String nationality;
    private String birthday;

    @Builder
    public UserInfoResponse(Member member) {
        this.id = member.getId();
        this.name = member.getRealName();
        this.nickName = member.getNickName();
        this.email = member.getEmailAddress();
        try {
            this.image = createFileNameWithPaths(member.getFile().getName());
        }
        catch (Exception e) {
        }
        this.nationality =member.getNationality();
        this.birthday = member.getBirthday();
    }

    private String createFileNameWithPaths(String filename) {
        return "src" + File.separator
                + "main" + File.separator
                + "resources" + File.separator
                + "reviews" + File.separator
                + "images" + File.separator
                + filename;
    }

}
