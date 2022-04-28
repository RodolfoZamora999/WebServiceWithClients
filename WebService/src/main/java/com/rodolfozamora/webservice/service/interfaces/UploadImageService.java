package com.rodolfozamora.webservice.service.interfaces;

import org.springframework.core.io.Resource;

import java.io.InputStream;

public interface UploadImageService {
    String saveImage(InputStream in, String type);
    Resource getImage(String fileName);
}
