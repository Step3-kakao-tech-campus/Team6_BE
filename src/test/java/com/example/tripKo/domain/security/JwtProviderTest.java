package com.example.tripKo.domain.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.tripKo.IntegrationTest;
import com.example.tripKo._core.security.JwtProvider;
import com.example.tripKo.domain.member.MemberRoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class JwtProviderTest extends IntegrationTest {

  @Autowired
  private JwtProvider jwtProvider;

  @Test
  @DisplayName("accessToken이 정상적으로 생성된다.")
  public void success_create_accessToken() throws Exception {
    long userPk = 1;
    MemberRoleType[] roleType = new MemberRoleType[]{MemberRoleType.MEMBER, MemberRoleType.DORMANT};

    String accessToken = jwtProvider.createAccessToken(userPk, roleType);
    System.out.println(accessToken);

    Claims claims = Jwts
        .parserBuilder()
        .setSigningKey(jwtProvider.secretKey)
        .build()
        .parseClaimsJws(accessToken)
        .getBody();
    long savedUserPK = Long.parseLong(claims.getSubject());

    String roles = claims.get("roles").toString();
    assertThat(savedUserPK).isEqualTo(userPk);
    assertThat(roles).isEqualTo("MEMBER,DORMANT");
  }

  @Test
  @DisplayName("인증 정보가 올바르면 조회가 성공해야 한다.")
  public void success_getAuth_test() throws Exception {
    String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJNRU1CRVIsRE9STUFOVCIsImlhdCI6MTY5ODgyNzI2NiwiZXhwIjoxNjk4ODI5MDY2fQ.suGpVAewfi0acAsONQOBpitiOJVzGmPbYBrvQgP88pk";
    Authentication authentication = jwtProvider.getAuthentication(validToken);

    assertThat(authentication.isAuthenticated()).isTrue();
    assertThat(authentication.getPrincipal()).isInstanceOf(UserDetails.class);
    List<String> authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    assertThat(authorities).containsExactly("ROLE_MEMBER");
  }

  @Test
  @DisplayName("인증 정보가 틀리다면 조회에 실패해야 한다.")
  public void fail_getAuth_test() throws Exception {
    String tokenWithoutRoles = "without.role";

    assertThrows(RuntimeException.class, ()-> jwtProvider.getAuthentication(tokenWithoutRoles));
  }

}
