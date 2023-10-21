//package com.example.tripKo._core.security;
//
//import com.example.tripKo._core.errors.exception.Exception401;
//import com.example.tripKo._core.errors.exception.Exception403;
//import com.example.tripKo._core.security.filter.JwtAuthFilter;
//import com.example.tripKo._core.security.filter.RefreshTokenFilter;
//import com.example.tripKo._core.utils.FilterResponseUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true)
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//  private final JwtAuthFilter jwtAuthFilter;
//  private final RefreshTokenFilter refreshTokenFilter;
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.authorizeHttpRequests()
//        .antMatchers("/sign-up/**", "/sign-in/**") // 허용하는 것들 (추가 예정)
//        .permitAll()
//        .anyRequest().hasRole("정회원")
//        .and()
//        .httpBasic().disable()
//        .csrf().disable()
//        .logout().disable()
//        .cors().configurationSource(configurationSource())
//        .and()
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
//        .exceptionHandling().authenticationEntryPoint(((request, response, authException) -> {
//          FilterResponseUtils.unAuthorized(response, new Exception401("인증되지 않았습니다"));
//        }))
//        .and()
//        .exceptionHandling().accessDeniedHandler(((request, response, accessDeniedException) -> {
//          FilterResponseUtils.forbidden(response, new Exception403("권한이 없습니다"));
//        }))
////        .and()
////        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
////        .addFilterBefore(refreshTokenFilter, JwtAuthFilter.class)
//        ;
//
//    return http.build();
//  }
//
//  public CorsConfigurationSource configurationSource() {
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.addAllowedHeader("*");
//    configuration.addAllowedMethod("*");
//    configuration.addAllowedOriginPattern("*");
//    configuration.setAllowCredentials(true);
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration);
//    return source;
//  }
//
//}
