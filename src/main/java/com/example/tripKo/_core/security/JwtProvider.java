package com.example.tripKo._core.security;

import static com.example.tripKo._core.security.data.JwtType.ACCESS_TOKEN;
import static com.example.tripKo._core.security.data.JwtType.REFRESH_TOKEN;
import static com.example.tripKo._core.security.data.JwtValidationType.EMPTY;
import static com.example.tripKo._core.security.data.JwtValidationType.EXPIRED;
import static com.example.tripKo._core.security.data.JwtValidationType.MALFORMED;
import static com.example.tripKo._core.security.data.JwtValidationType.UNSUPPORTED;
import static com.example.tripKo._core.security.data.JwtValidationType.VALID;

import com.example.tripKo._core.security.data.JwtType;
import com.example.tripKo._core.security.data.JwtUserDetails;
import com.example.tripKo._core.security.data.JwtUserDetailsService;
import com.example.tripKo._core.security.data.TokenValidationDTO;
import com.example.tripKo._core.security.refreshToken.applicaiton.RefreshTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final RefreshTokenService refreshTokenService;

    private static final String ROLES = "roles";
    private static final String SEPARATOR = ",";

    @Value("${jwt.secret.key}")
    private String salt;

//    @Value("spring.jwt.secret") -> 이거 있어야 하지 않나요??
    private Key secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(salt));
        // 이 부분 잘 모르겠어요.. 왜 Encode가 아니라 Decode를 하죠...?
    }

//    // JWT 토큰 생성 -> 이 친구는 무슨 역할인가요?? 안쓰이는 것 같아서요!
//    public JwtToken generateToken(Authentication authentication) {
//
//        Date now = new Date();
//
//        String accessToken = createAccessToken(authentication);
//
//        String refreshToken = createRefreshToken(now);
//
//        refreshTokenService.saveRefreshToken(authentication.getName(), refreshToken, accessToken);
//
//        return JwtToken.builder()
//                .grantType("Bearer")
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
//    }

    public String createAccessToken(String userPK, String... roleTypes) {
        // 권한 가져오기
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
        Claims claims = Jwts.claims().setSubject(userPK);
        setRoles(claims, roleTypes);

        // Access Token 생성
        Date now = new Date();
        Date accessTokenExpiresIn = new Date(now.getTime() + ACCESS_TOKEN.getExpiredMillis();

        return Jwts.builder()
                .setClaims(claims)  //데이터
                .setIssuedAt(now)   //토큰 발행 시간
                .setExpiration(accessTokenExpiresIn) //토큰 만료 기한
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(Date now) {
        // Refresh Token 생성
        // Refresh 토큰은 유효기간이 길기에 탈취될 확률이 상대적으로 높아 사용자의 정보가 노출될 수 있으므로 페이로드에 포함하지 않는다.
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
        List<String> roles = getRolesBy(claims);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // UserDetails 객체를 만들어서 Authentication 리턴
//        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(claims.getSubject());
        UserDetails userDetails = new JwtUserDetails(claims.getSubject(), roles);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    // Cookie 에서 토큰을 추출하려는 이유 -> accessToken & refreshToken 구별 때문에
    public String resolveToken(HttpServletRequest request, JwtType jwtType) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
        Optional<Cookie> accessToken = Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equals(jwtType.getTokenName()))
            .findFirst();
        if (accessToken.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return accessToken.get().getValue();
    }

    // 토큰의 유효성 + 만료일자 확인
    public TokenValidationDTO validateToken(HttpServletRequest request, JwtType jwtType) {
        try {
            String token = resolveToken(request, jwtType);
            getAuthId(token);
            return TokenValidationDTO.of(true, VALID, token);
        } catch (SecurityException | MalformedJwtException e) {
            return TokenValidationDTO.of(false, MALFORMED);
        } catch (ExpiredJwtException e) {
            return TokenValidationDTO.of(false, EXPIRED);
        } catch (UnsupportedJwtException e) {
            return TokenValidationDTO.of(false, UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            return TokenValidationDTO.of(false, EMPTY);
        }
//            Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(String.valueOf(jwtType));
//            return true;
//        } catch (SecurityException | MalformedJwtException e) {
//            log.info("Invalid JWT Token", e);
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT Token", e);
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT Token", e);
//        } catch (IllegalArgumentException e) {
//            log.info("JWT claims string is empty.", e);
//        }
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

    public long getAuthId(String token) {
        return Long.parseLong(parseClaims(token).getSubject());
    }

    private static List<String> getRolesBy(Claims claims) {
        return List.of(claims.get(ROLES)
            .toString()
            .split(SEPARATOR));
    }

    private static void setRoles(Claims claims, String[] roles) {
        claims.put(ROLES, String.join(SEPARATOR, roles));
    }

}
