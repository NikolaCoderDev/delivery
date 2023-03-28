package com.guavaPay.employeeservice.config.jwt;

import com.guavaPay.employeeservice.dto.LoginEmployeeDtoRes;
import com.guavaPay.employeeservice.model.Employee;
import com.guavaPay.employeeservice.repository.EmployeeRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JwtUtil {

    private final EmployeeRepository employeeRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    private Key key;

    public JwtUtil(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * create jwt
     */

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String generate(UserDetails userDetails, Employee dto, Employee userId, String type) {
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

    /**
     * parse jwt
     */
    public String removeBearer(String token) {



    return token.substring(7);
}

    public LoginEmployeeDtoRes getJwtParsetUserDto(String token) {

        Claims claims = extractAllClaims(token);
        String text = claims.get("role").toString();
        String authorities = parseText(text);

        return LoginEmployeeDtoRes.builder()
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

        return str.substring(0, str.toString().length()-1);
    }

    /**
     * get user by jwt
     */
    public Employee getByJwtToken(String token) {
        return employeeRepository.getByAccessToken(token).orElse(null);
    }

    public void updateToken(String exit, String token) {
        employeeRepository.updateToken(exit, token);
    }
}