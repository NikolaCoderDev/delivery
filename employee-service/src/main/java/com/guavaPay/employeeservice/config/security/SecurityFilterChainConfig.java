package com.guavaPay.employeeservice.config.security;

import com.guavaPay.employeeservice.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

  private static final String[] UN_SECURED_URLs = {
          "/login",
          "/register"
  };

  private final LogoutHandler logoutHandler;
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
           .csrf()
           .disable()
           .authorizeHttpRequests()
           .requestMatchers(UN_SECURED_URLs)
           .permitAll()
           .anyRequest()
           .authenticated()
           .and()
           .sessionManagement()
           .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           .and()
           .authenticationProvider(authenticationProvider)
           .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
           .logout()
           .logoutUrl("/logout")
           .addLogoutHandler(logoutHandler)
           .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()).and().build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> {
      web.ignoring().requestMatchers("/v3/api-docs");
    };
  }
}