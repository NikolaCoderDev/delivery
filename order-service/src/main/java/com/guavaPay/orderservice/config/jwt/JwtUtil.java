package com.guavaPay.orderservice.config.jwt;

import com.guavaPay.orderservice.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public UserDto getJwtParsetUserDto(String token) {

        Claims claims = extractAllClaims(token);
        String text = claims.get("role").toString();
        String authorities = parseText(text);

        return UserDto.builder()
                .id(claims.get("userid").toString())
                .login(claims.getSubject())
                .authorities(authorities)
                .build();
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

    public String removeBearer(String token) {
        return token.substring(7);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}