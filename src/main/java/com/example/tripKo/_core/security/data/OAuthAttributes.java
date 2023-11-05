package com.example.tripKo._core.security.data;

import com.example.tripKo.domain.member.MemberRoleType;
import com.example.tripKo.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickName;
    private String emailAddress;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String nickName, String emailAddress) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickName = nickName;
        this.emailAddress = emailAddress;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickName((String) attributes.get("name"))
                .emailAddress((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .nickName((String) response.get("nickname"))
                .emailAddress((String) response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .nickName(nickName)
                .emailAddress(emailAddress)
                .role(MemberRoleType.MEMBER)
                .build();

        //임의로 부족한 데이터 채우기(사실 프론트에서 이 정보를 채워주는 페이지를 만들어주시면 제일 좋은데 일단 이렇게 해놨어용,,)
        member.addInfoForGoogleLogin(passwordEncoder.encode("qwer1234"), nickName, emailAddress.split("@")[0], "000000", "Korea");
        return member;
    }
}
