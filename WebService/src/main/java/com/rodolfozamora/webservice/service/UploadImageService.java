package com.rodolfozamora.webservice.service;

import org.springframework.core.io.Resource;

import java.io.InputStream;

public interface UploadImageService {
    String saveImage(InputStream in);
    Resource getImage(String fileName);
}
