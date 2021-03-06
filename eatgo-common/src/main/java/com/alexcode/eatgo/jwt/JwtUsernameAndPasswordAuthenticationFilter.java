package com.alexcode.eatgo.jwt;

import com.alexcode.eatgo.security.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
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
import java.time.LocalDate;
import java.util.Date;


/**
 * 사용자 로그인시 username 및 password가 유효하면
 * 응답 헤더에 jwt token을 넣고 클라이언트로 응답
 */
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final JwtSecretKey jwtSecretKey;

    public JwtUsernameAndPasswordAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtConfig jwtConfig,
            JwtSecretKey jwtSecretKey) {

        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.jwtSecretKey = jwtSecretKey;
    }

    /**
     * 사용자 로그인시 username과 password가 유효한지 검사
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {

        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * attemptAuthentication 실행 후에 성공시 successfulAuthentication 호출
     * JWT 토큰 생성후 응답(Response) 헤더(Header)에 추가하여 클라이언트로 전송
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {

        ApplicationUser applicationUser = (ApplicationUser) authResult.getPrincipal();
        Long userId = applicationUser.getUserId();
        Long restaurantId = applicationUser.getRestaurantId();
        String userName = applicationUser.getName();

        JwtBuilder builder = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("userId", userId)
                .claim("userName", userName)
                .claim("authorities", authResult.getAuthorities());

        if(restaurantId != null) {
            builder = builder.claim("restaurantId", restaurantId);
        }

        String token = builder
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(jwtSecretKey.getSecretKey())
                .compact();

        response.addHeader("token", jwtConfig.getTokenPrefix() + " " + token);
    }

}
