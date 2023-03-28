package com.guavaPay.userservice.config.jwt;

import com.guavaPay.userservice.dto.UserDto;
import com.guavaPay.userservice.model.User;
import com.guavaPay.userservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.Principal;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JwtUtil {

    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    private Key key;

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

//create jwt
    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String generate(UserDetails userDetails, User dto, User userId, String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userid", userId.getId().toString());
        claims.put("userName", userDetails.getUsername());
        claims.put("role", userDetails.getAuthorities());
        return doGenerateToken(claims, dto.getLogin(), type);
    }

    private String doGenerateToken(Map<String, Object> claims, String username, String type) {
        long expirationTimeLong;
        if ("ACCESS".equals(type)) {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000;
        } else {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000 * 5;
        }
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

//parse jwt
    public String removeBearer(String token) {
    return token.substring(7);
}

    public UserDto getJwtParsetUserDto(String token) {

        Claims claims = extractAllClaims(token);
        String text = claims.get("role").toString();
        String authorities = parseText(text);

        return UserDto.builder()
                .id(claims.get("userid").toString())
                .login(claims.getSubject())
                .role(authorities)
                .build();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String parseText(String text) {
        StringBuilder str = new StringBuilder();
        List<String> words = new ArrayList<>();
        words.add("admin");
        words.add("user");
        words.add("courier");

        String patternString = "\\b(" + StringUtils.join(words, "|") + ")\\b";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            str.append(matcher.group(1));
            str.append(",");
        }

        return str.toString().substring(0, str.toString().length()-1);
    }

//get user by jwt
    public User getByJwtToken(String token) {
        return userRepository.getByAccessToken(token).orElse(null);
    }

    public void updateToken(String exit, String token) {
        userRepository.updateToken(exit, token);
    }
}