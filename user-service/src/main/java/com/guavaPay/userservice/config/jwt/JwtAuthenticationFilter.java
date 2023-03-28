package com.guavaPay.userservice.config.jwt;

import com.guavaPay.userservice.config.security.UserDetailsServiceImpl;
import com.guavaPay.userservice.config.security.employeeDetail.EmployeeDetailsServiceImpl;
import com.guavaPay.userservice.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final UserDetailsServiceImpl userDetailsService;
  private final EmployeeDetailsServiceImpl employeeDetailsService;
  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.substring(7);
    User user = jwtUtil.getByJwtToken(token);

    if (user != null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
      authenticate(userDetails, filterChain, request, response);
    }

    if (user == null){
      UserDetails employeeDetails = employeeDetailsService.loadUserByUsername(token);
      authenticate(employeeDetails, filterChain, request, response);
    }
  }

  private void authenticate(UserDetails details,
                            FilterChain filterChain,
                            HttpServletRequest request,
                            HttpServletResponse response)
          throws ServletException, IOException {

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            details,
            null,
            details.getAuthorities()
    );
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);
    filterChain.doFilter(request, response);
  }
}