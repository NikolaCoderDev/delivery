package com.guavaPay.userservice.service;

import com.guavaPay.userservice.config.jwt.JwtUtil;
import com.guavaPay.userservice.config.security.UserDetailsServiceImpl;
import com.guavaPay.userservice.dto.UserDto;
import com.guavaPay.userservice.dto.mapper.Mapper;
import com.guavaPay.userservice.model.User;
import com.guavaPay.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final JwtUtil jwtUtil;

    public User register(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setRole("user");
        return userRepository.save(user);
    }

    public UserDto login(User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
            User userId = userRepository.getByLogin(user.getLogin());
            String token = jwtUtil.generate(userDetails, user, userId, "ACCESS");
            user.setAccessToken(token);
            userRepository.updateAccessTokenByLogin(user.getAccessToken(), user.getLogin());
            user.setAccessToken(token);
            return mapper.mapToUserDto(user);
        }
        return null;
    }

    @PreAuthorize("hasAuthority('admin') || hasAuthority('courier')")
    public UserDto getById(String id) {
        User user = userRepository.getById(new Long(id));
        return mapper.mapToUserDto(user);
    }
}