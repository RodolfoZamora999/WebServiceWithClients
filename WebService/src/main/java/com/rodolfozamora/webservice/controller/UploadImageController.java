package com.rodolfozamora.webservice.controller;

import com.rodolfozamora.webservice.service.interfaces.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/image")
public class UploadImageController {
    private final UploadImageService uploadImageService;

    @Autowired
    public UploadImageController(UploadImageService uploadImageService) {
        this.uploadImageService = uploadImageService;
    }

    @PostMapping(consumes = {"image/jpeg", "image/png"}, produces = "application/json")
    public ResponseEntity<Object> postImage(InputStream in) {
        var id = uploadImageService.saveImage(in);
        if (id == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Error to create the image"));
        else
            return ResponseEntity.ok(Map.of("path", "api/image/%s".formatted(id)));
    }

    @GetMapping(value = "/{id}", produces = {"image/jpg", "image/png", "application/json"})
    public ResponseEntity<Resource> getImage(@PathVariable String id) {
        var resource = this.uploadImageService.getImage(id);
        if (resource != null)
            return ResponseEntity.ok(resource);
        else
            return ResponseEntity.badRequest().build();
    }
}
