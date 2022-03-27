package com.rodolfozamora.webservice.controller;

import com.rodolfozamora.webservice.model.AuthUser;
import com.rodolfozamora.webservice.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class JwtAuthController {
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    @Autowired
    public JwtAuthController(AuthenticationManager manager, JwtService jwtService) {
        this.jwtService = jwtService;
        this.manager = manager;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthUser user) {
        try {
            var userPassword = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
            var authentication = manager.authenticate(userPassword);

            var userName = authentication.getName();
            var roles = authentication.getAuthorities().stream().
                    map(GrantedAuthority::getAuthority).toList();

            var tokenResponse = Map.of("token",
                    jwtService.createToken(userName, roles.toArray(new String[0])));

            return ResponseEntity.ok(tokenResponse);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage()));
        }
    }

}