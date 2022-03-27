package com.rodolfozamora.webservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@PropertySource("classpath:custom.properties")
public class JwtAuthService implements JwtService {
    @Value("${jwt.hmac.secret: MY-256-BIT-SECRET-JWT}")
    private String SECRET_KEY;
    @Value("${jwt.time-expire: 604800000}")
    private long TIME_EXPIRE;

    /**
     * Creates a new token with username and roles given.
     * @param user Username
     * @param roles User roles
     * @return A new JWT token
     */
    @Override
    public String createToken(String user, String... roles) {
        return JWT.create().withSubject(user).withArrayClaim("roles", roles).
                withExpiresAt(new Date(System.currentTimeMillis() + TIME_EXPIRE)).
                    sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * Gets username from the token given
     * @param token String token
     * @return Username
     */
    @Override
    public String getSubject(String token) {
        return JWT.decode(token).getSubject();
    }

    /**
     * Gets User GrantedAuthorities from the token given
     * @param token String token
     * @return A list of GrantedAuthorities
     */
    @Override
    public List<GrantedAuthority> getGrantedAuthorities(String token) {
        var roles = JWT.decode(token).getClaim("roles").asArray(String.class);
        return Arrays.stream(roles).map(role ->
                (GrantedAuthority) new SimpleGrantedAuthority(role)
        ).toList();
    }

    /**
     * Validates if token given is valid.
     * @param token String token to validate.
     * @return True if token is valid false if is not valid.
     */
    @Override
    public boolean isValid(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
