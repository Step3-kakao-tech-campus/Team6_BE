package com.example.tripKo._core.security.filter;

import static com.example.tripKo._core.security.data.JwtType.ACCESS_TOKEN;

import com.example.tripKo._core.security.JwtProvider;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.example.tripKo._core.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;
    private final RedisUtil redisUtil;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String accessToken = jwtProvider.resolveAccessToken(httpServletRequest);

        //validation을 먼저 수행하면 토큰이 없을때 오류가 떠서 if 문 안으로 넣었습니다!!
        if (accessToken != null && accessToken != "") {
            if (jwtProvider.validateToken(accessToken)) {
                Authentication authentication = jwtProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else if (jwtProvider.isTokenExpired(accessToken)) {
                String id = jwtProvider.getAuthId(accessToken);

                final var refreshToken = jwtProvider.resolveRefreshToken(httpServletRequest);
                boolean isRefreshTokenValid = jwtProvider.validateToken(refreshToken);

                boolean isAccessExpiredAndRefreshValid = isRefreshTokenValid && isTokenInRedis(id, refreshToken);

                if (isAccessExpiredAndRefreshValid) {
                    String newAccessToken = jwtProvider.createAccessToken(id);
                    Authentication authentication = jwtProvider.getAuthentication(newAccessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // 로그 찍는 부분이 validateToken에 포함되어 있어서 주석해놨습니당!!
//        else {
//            log.info(tokenValidation.getJwtValidationType().getMessage());
//        }
        chain.doFilter(request, response);
    }

    private boolean isTokenInRedis(String id, String refreshToken) {
        Optional<String> tokenInRedis = redisUtil.getData(id);
        if (tokenInRedis.isEmpty())
            return false;
        return tokenInRedis.equals(refreshToken);
    }
}