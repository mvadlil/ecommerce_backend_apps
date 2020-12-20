package co.g2academy.bootcamp.ecommerce.filter;

import co.g2academy.bootcamp.ecommerce.model.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

import static co.g2academy.bootcamp.ecommerce.model.SecurityConstants.EXPIRATION_TIME;
import static co.g2academy.bootcamp.ecommerce.model.SecurityConstants.KEY;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // get login object from request body
//            Login login = new ObjectMapper().readValue(request.getInputStream(), Login.class);
            Login login = new ObjectMapper().readValue(request.getInputStream(), Login.class);
            // create authentication token
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword(), Collections.emptyList());
            // authenticate using authenticationManager object
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        Key key = Keys.hmacShaKeyFor(KEY.getBytes());
        User user = (User) authResult.getPrincipal();
        Claims claims = Jwts.claims().setSubject(user.getUsername());

        // create token
        String token = Jwts.builder().setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(exp).compact();
        response.setHeader("token", token);
    }
}
