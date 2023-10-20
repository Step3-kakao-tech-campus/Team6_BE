package com.example.tripKo._core.security;

import com.example.tripKo._core.security.dto.JwtToken;
import com.example.tripKo._core.security.refreshToken.applicaiton.RefreshTokenService;
import com.example.tripKo._core.security.refreshToken.dao.RefreshTokenRepository;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtUserDetailsService jwtUserDetailsService;

    @Value("${jwt.secret.key}")
    private String salt;

    private Key secretKey;

    // 토큰 유효시간 30분
    private final long accessTokenValidTime = 30 * 60 * 1000L;
    private final long refreshTokenValidTime = 60 * 24 * 3 * 60 * 1000L;
    private final RefreshTokenService refreshTokenService;
    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(salt));
    }

    // JWT 토큰 생성
    public JwtToken generateToken(Authentication authentication) {
        Date now = new Date();

        String accessToken = createAccessToken(authentication, now);

        String refreshToken = createRefreshToken(now);

        refreshTokenService.saveRefreshToken(authentication.getName(), refreshToken, accessToken);

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String createAccessToken(Authentication authentication, Date now) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now.getTime() + accessTokenValidTime);

        return Jwts.builder()
                .setSubject(authentication.getName()) //토큰 제목
                .setIssuedAt(now)   //토큰 발행 시간
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn) //토큰 만료 기한
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(Date now) {
        // Refresh Token 생성
        // Refresh 토큰은 유효기간이 길기에 탈취될 확률이 상대적으로 높아 사용자의 정보가 노출될 수 있으므로 페이로드에 포함하지 않는다.
        Date refreshTokenExpiresIn = new Date(now.getTime() + refreshTokenValidTime);

        return Jwts.builder()
                .setExpiration(refreshTokenExpiresIn)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        // 토큰 복호화
        Claims claims = parseClaims(token);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰의 유효성 + 만료일자 확인
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

    // 토큰이 만료되었는지
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().after(new Date());
    }

    // Claim 에서 username 가져오기
    public String getUsernameFromToken(String token) {
        String username = String.valueOf(parseClaims(token).get("username"));
        log.info("getUsernameFormToken subject = {}", username);
        return username;
    }
}
