package com.rodolfozamora.webservice.filter;

import com.rodolfozamora.webservice.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private List<String> ignorePath = new ArrayList<>();

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        LOG.debug("JwtAuthenticationFilter init");

        if (this.ignorePath.contains(request.getServletPath())){
            LOG.debug("Ignoring path: " + request.getServletPath());
            filterChain.doFilter(request, response);
            LOG.debug("JwtAuthenticationFilter end");
            return;
        }

        //Get header
        final String headerAuth = Objects.requireNonNullElse(request.getHeader(HttpHeaders.AUTHORIZATION), "null");
        LOG.debug("Header request: " + headerAuth);

        if (!headerAuth.startsWith("Bearer ")) {
            LOG.debug("Header is different to 'Bearer ': " + headerAuth);
            filterChain.doFilter(request, response);
            LOG.debug("JwtAuthenticationFilter end");
            return;
        }

        //Valid HeaderToken
        final String jwtTokenHeader = headerAuth.split(" ")[1].trim();
        if (!jwtService.isValid(jwtTokenHeader)) {
            LOG.debug("HeaderToken is not valid: " + jwtTokenHeader);
            filterChain.doFilter(request, response);
            LOG.debug("JwtAuthenticationFilter end");
            return;
        }

        LOG.debug("HeaderToken is valid: " + jwtTokenHeader);

        //Get user values
        var userName = jwtService.getSubject(jwtTokenHeader);
        var grants = jwtService.getGrantedAuthorities(jwtTokenHeader);
        var authentication = new UsernamePasswordAuthenticationToken(userName, null, grants);

        LOG.debug("User: %s".formatted(authentication));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
        LOG.debug("JwtAuthenticationFilter end");
    }

    public void setIgnorePath(String... path) {
        this.ignorePath = Arrays.asList(path);
    }

}
