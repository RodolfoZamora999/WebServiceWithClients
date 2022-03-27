package com.rodolfozamora.webservice.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface JwtService {

    String createToken(String user, String... roles);

    boolean isValid(String token);

    String getSubject(String token);

    List<GrantedAuthority> getGrantedAuthorities(String token);
}
