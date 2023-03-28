package com.guavaPay.employeeservice.config.security;

import com.guavaPay.employeeservice.config.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final JwtUtil jwtUtil;

  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {
    final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (token == null) {
      return;
    }
      String cleanToken = token.substring(7);
      jwtUtil.updateToken("exit", cleanToken);
      SecurityContextHolder.clearContext();
  }
}