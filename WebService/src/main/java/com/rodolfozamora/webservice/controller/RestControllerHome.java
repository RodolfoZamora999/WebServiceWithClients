package com.rodolfozamora.webservice.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestControllerHome {

    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).
                body("{message: 'Hello World'}");
    }

}
