package com.example.tripKo._core.security;

import com.example.tripKo._core.errors.exception.Exception401;
import com.example.tripKo._core.errors.exception.Exception403;
import com.example.tripKo._core.security.data.CustomOAuth2UserService;
import com.example.tripKo._core.security.data.JwtToken;
import com.example.tripKo._core.security.data.OAuth2SuccessHandler;
import com.example.tripKo._core.security.filter.JwtAuthFilter;
import com.example.tripKo._core.security.filter.RefreshTokenFilter;
import com.example.tripKo._core.utils.FilterResponseUtils;
import com.example.tripKo._core.utils.RedisUtil;
import com.example.tripKo.domain.member.MemberRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtProvider jwtProvider;
  private final RedisUtil redisUtil;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;

  // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
  @Bean
  public static BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .cors().configurationSource(configurationSource())
            .and()
            .authorizeHttpRequests()
            .antMatchers("/userinfo/**", "/wishlist/**", "/**/reviews/**") // 허용하지 않는 것들 (추가 예정)
            .hasRole(MemberRoleType.MEMBER.name())
            .anyRequest()
            .permitAll()
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
            .and()
            .successHandler(oAuth2SuccessHandler)
            .and()
            .exceptionHandling().authenticationEntryPoint(((request, response, authException) -> {
              FilterResponseUtils.unAuthorized(response, new Exception401("인증되지 않았습니다"));
            }))
            .and()
            .exceptionHandling().accessDeniedHandler(((request, response, accessDeniedException) -> {
              FilterResponseUtils.forbidden(response, new Exception403("권한이 없습니다"));
            }))
            .and()
            .addFilterBefore(new JwtAuthFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new RefreshTokenFilter(jwtProvider, redisUtil), JwtAuthFilter.class);

    //h2-console 접속을 위해 허용
   http.headers().frameOptions().disable()
           .addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "ALLOW-FROM " + "https://kfd701ba2c3a1a.user-app.krampoline.com:3000"));

    return http.build();
  }

  /*
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .antMatchers() // 허용하지 않는 것들 (추가 예정)
        .hasRole(MemberRoleType.MEMBER.name())
        .anyRequest()
        .permitAll()
        .and()
        .formLogin().disable()
        .httpBasic().disable()
        .csrf().disable()
        .logout().disable()
        .cors().configurationSource(configurationSource())
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .oauth2Login()
        .userInfoEndpoint()
        .userService(customOAuth2UserService)
        .and()
        .successHandler(oAuth2SuccessHandler)
        .and()
        .exceptionHandling().authenticationEntryPoint(((request, response, authException) -> {
          FilterResponseUtils.unAuthorized(response, new Exception401("인증되지 않았습니다"));
        }))
        .and()
        .exceptionHandling().accessDeniedHandler(((request, response, accessDeniedException) -> {
          FilterResponseUtils.forbidden(response, new Exception403("권한이 없습니다"));
        }))
        .and()
        .addFilterBefore(new JwtAuthFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new RefreshTokenFilter(jwtProvider, redisUtil), JwtAuthFilter.class);
      
    
      http.cors(cors -> cors.configurationSource(configurationSource()));


    //h2-console 접속을 위해 허용
    //http.headers().frameOptions().sameOrigin();

    return http.build();
  }
   */

  public CorsConfigurationSource configurationSource() {
    System.out.println("==========================================================================================================================");
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(List.of("https://kfd701ba2c3a1a.user-app.krampoline.com:3000", "https://kfd701ba2c3a1a.user-app.krampoline.com",
 "https://k9f03505f0e8ba.user-app.krampoline.com:3000",  "https://k9f03505f0e8ba.user-app.krampoline.com:8080"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("Origin","Accept","X-Requested-With","Content-Type","Access-Control-Request-Method","Access-Control-Request-Headers", "Authorization"));
    configuration.setExposedHeaders(List.of("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }


}
