package com.example.tripKo._core.security.filter;

import static com.example.tripKo._core.security.data.JwtType.ACCESS_TOKEN;

import com.example.tripKo._core.security.JwtProvider;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

  private final JwtProvider jwtProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    var tokenValidation = jwtProvider.validateToken(httpServletRequest, ACCESS_TOKEN);

    if (tokenValidation.isValid()) {
      Authentication authentication = jwtProvider.getAuthentication(tokenValidation.getToken());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      log.info(tokenValidation.getJwtValidationType().getMessage());
    }
    chain.doFilter(request, response);
  }

}


