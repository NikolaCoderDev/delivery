package com.guavaPay.employeeservice.config.jwt;

import com.guavaPay.employeeservice.config.security.EmployeeDetailsServiceImpl;
import com.guavaPay.employeeservice.config.security.userDetail.UserDetailsServiceImpl;
import com.guavaPay.employeeservice.model.Employee;
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

  private final EmployeeDetailsServiceImpl employeeDetailsService;
  private final UserDetailsServiceImpl userDetailsService;
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
    Employee employee = jwtUtil.getByJwtToken(token);
    String email = jwtUtil.extractUsername(token);

    if (employee != null) {
      UserDetails userDetails = employeeDetailsService.loadUserByUsername(email);
      authenticate(userDetails, filterChain, request, response);
    }

    if (employee == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(token);
      authenticate(userDetails, filterChain, request, response);
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