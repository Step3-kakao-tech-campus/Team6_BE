package com.example.tripKo._core.security.filter;

import static com.example.tripKo._core.security.data.JwtType.ACCESS_TOKEN;
import static com.example.tripKo._core.security.data.JwtType.REFRESH_TOKEN;
import static com.example.tripKo._core.security.data.JwtValidationType.EXPIRED;

import com.example.tripKo._core.security.JwtProvider;
import com.example.tripKo._core.security.data.JwtValidationType;
import com.example.tripKo._core.security.data.TokenValidationDTO;
import com.example.tripKo._core.utils.RedisUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class RefreshTokenFilter extends GenericFilterBean {

//    private final JwtProvider jwtProvider;
//    private final RedisUtil redisUtil;
//
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        final var accessToken = jwtProvider.validateToken(httpServletRequest, ACCESS_TOKEN);
//        final var refreshToken = jwtProvider.validateToken(httpServletRequest, REFRESH_TOKEN);
//        boolean isAccessExpiredAndRefreshValid =
//                isAccessTokenExpired(accessToken.getJwtValidationType()) && isRefreshTokenValid(refreshToken)
//                        && isTokenInRedis(refreshToken);
//
//        if (isAccessExpiredAndRefreshValid) {
//            Authentication authentication = jwtProvider.getAuthentication(refreshToken.getToken());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
        chain.doFilter(request, response);
    }
//
//    private static boolean isAccessTokenExpired(JwtValidationType jwtValidationType) {
//        return EXPIRED.equals(jwtValidationType);
//    }
//
//    private static boolean isRefreshTokenValid(TokenValidationDTO tokenValidationDTO) {
//        return tokenValidationDTO.isValid();
//    }
//
//    private boolean isTokenInRedis(TokenValidationDTO tokenValidationDTO) {
//        long id = jwtProvider.getAuthId(tokenValidationDTO.getToken());
//        String tokenInRedis = redisUtil.getData(String.valueOf(id));
//        return tokenInRedis.equals(tokenValidationDTO.getToken());
//    }

}