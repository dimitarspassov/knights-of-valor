package com.dspassov.kovapi.security;


import com.dspassov.kovapi.areas.users.entities.Role;
import com.dspassov.kovapi.areas.users.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            return this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            user.getAuthorities()
                    )
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(((User) authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + JwtSecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, JwtSecurityConstants.SECRET.getBytes())
                .compact();

        User current = (User) authResult.getPrincipal();

        List<String> roles = current.getRoles().stream()
                .map(Role::getAuthority)
                .collect(Collectors.toList());

        StringBuilder authJson = new StringBuilder();

        authJson.append(String.format("{\"authToken\":\"%s\",",
                token));
        authJson.append(String.format("\"user\":\"%s\",",
                current.getUsername()));
        authJson.append(String.format("\"roles\":\"%s\"}",
                roles.toString()));

        response.getWriter().append(authJson.toString());
    }
}
