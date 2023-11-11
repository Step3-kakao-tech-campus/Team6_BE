package com.example.tripKo._core.security;

import com.example.tripKo._core.security.data.JwtUserDetails;
import com.example.tripKo._core.security.data.JwtUserDetailsService;
import com.example.tripKo._core.security.data.JwtToken;

import static com.example.tripKo._core.security.data.JwtType.ACCESS_TOKEN;
import static com.example.tripKo._core.security.data.JwtType.REFRESH_TOKEN;

import com.example.tripKo._core.security.data.RefreshToken;
import com.example.tripKo._core.utils.RedisUtil;
import com.example.tripKo.domain.member.MemberRoleType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

  private final JwtUserDetailsService jwtUserDetailsService;
  private final RedisUtil redisUtil;

  private static final String ROLES = "roles";
  private static final String SEPARATOR = ",";

  @Value("${jwt.secret.key}")
  private String salt;

  //Test를 위해서 public으로 열었습니다.
  public Key secretKey;

  @PostConstruct
  protected void init() {
    secretKey = Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary(salt));
  }

  // JWT 토큰 생성
  public JwtToken generateToken(Authentication authentication) {
    String userPK = authentication.getName();
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    String accessToken = createAccessToken(userPK, authorities);
    String refreshToken = createRefreshToken();

    //redis 설치 필요
    //redisUtil.save(new RefreshToken(userPK, refreshToken));

    return JwtToken.builder()
        .grantType("Bearer ")
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  public String createAccessToken(Long userPk, MemberRoleType... roles) {
    return createAccessToken(String.valueOf(userPk), Arrays.stream(roles)
        .map(MemberRoleType::name)
        .toArray(String[]::new));
  }

  public String createAccessToken(String userPK, String... roleTypes) {
    Date now = new Date();
    Date accessTokenExpiresIn = new Date(now.getTime() + ACCESS_TOKEN.getExpiredMillis());

    Claims claims = Jwts.claims().setSubject(userPK);
    setRoles(claims, roleTypes);

    return Jwts.builder()
        .setClaims(claims)  //데이터
        .setIssuedAt(now)   //토큰 발행 시간
        .setExpiration(accessTokenExpiresIn) //토큰 만료 기한
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  // Refresh Token 생성
  // Refresh 토큰은 유효기간이 길기에 탈취될 확률이 상대적으로 높아 사용자의 정보가 노출될 수 있으므로 페이로드에 포함하지 않는다.
  public String createRefreshToken() {
    Date now = new Date();
    Date refreshTokenExpiresIn = new Date(now.getTime() + REFRESH_TOKEN.getExpiredMillis());

    return Jwts.builder()
        .setExpiration(refreshTokenExpiresIn)
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  // JWT 토큰에서 인증 정보 조회
  public Authentication getAuthentication(String token) {
    // 토큰 복호화
    Claims claims = parseClaims(token);

    if (claims.get("roles") == null) {
      throw new RuntimeException("권한 정보가 없는 토큰입니다.");
    }

    // UserDetails 객체를 만들어서 Authentication 리턴
    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(claims.getSubject());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
  public String resolveAccessToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  // Request의 Header에서 RefreshToken 값을 가져옵니다.
  public String resolveRefreshToken(HttpServletRequest request) {
    if (request.getHeader("Refresh-Token") != null) {
      return request.getHeader("Refresh-Token");
    }
    return null;
  }

  // 토큰의 유효성 확인
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT Token", e);
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT Token", e);
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT Token", e);
    } catch (IllegalArgumentException e) {
      log.info("JWT claims string is empty.", e);
    }
    return false;
  }

  private Claims parseClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  public String getAuthId(String token) {
    return parseClaims(token).getSubject();
  }

  private static void setRoles(Claims claims, String[] roles) {
    claims.put(ROLES, String.join(SEPARATOR, roles));
  }

  // 토큰이 만료되었는지
  public boolean isTokenExpired(String token) {
    return !parseClaims(token).getExpiration().after(new Date());
  }
}
