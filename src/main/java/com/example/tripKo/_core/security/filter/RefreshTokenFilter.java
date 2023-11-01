package com.example.tripKo._core.security.filter;

import static com.example.tripKo._core.security.data.JwtValidationType.EXPIRED;

import com.example.tripKo._core.security.JwtProvider;
import com.example.tripKo._core.security.data.JwtValidationType;
import com.example.tripKo._core.utils.RedisUtil;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class RefreshTokenFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;
    private final RedisUtil redisUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String accessToken = jwtProvider.resolveAccessToken(httpServletRequest);

        if (accessToken != null) {
        String id = jwtProvider.getAuthId(accessToken);
        JwtValidationType jwtValidationType = JwtValidationType.valueOf(accessToken);

        final var refreshToken = jwtProvider.resolveRefreshToken(httpServletRequest);
        boolean isRefreshTokenValid = jwtProvider.validateToken(refreshToken);
        boolean isAccessExpiredAndRefreshValid = isAccessTokenExpired(jwtValidationType) && isRefreshTokenValid
            && isTokenInRedis(id, refreshToken);

        if (isAccessExpiredAndRefreshValid) {
            String newAccessToken = jwtProvider.createAccessToken(id);
            Authentication authentication = jwtProvider.getAuthentication(newAccessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    private static boolean isAccessTokenExpired(JwtValidationType jwtValidationType) {
        return EXPIRED.equals(jwtValidationType);
    }

    private boolean isTokenInRedis(String id, String refreshToken) {
        Optional<String> tokenInRedis = redisUtil.getData(id);
        return tokenInRedis.orElseThrow(() -> new RuntimeException("Token not found in Redis")).equals(refreshToken);
    }

}
