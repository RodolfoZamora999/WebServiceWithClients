package com.rodolfozamora.webservice.service;

import com.rodolfozamora.webservice.service.interfaces.UploadImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

@Service
@PropertySource("classpath:custom.properties")
public class FileSystemUploadImageService implements UploadImageService {
    private final Logger LOG = LoggerFactory.getLogger(FileSystemUploadImageService.class);
    private final Random random = new Random();

    @Value("${images-folder.root-path: ${user.dir}/images}")
    private Path rootPath;

    @PostConstruct
    private void postConstructor() {
        LOG.info("Image folder: " + rootPath.toString());
    }

    @Override
    public String saveImage(InputStream in) {
        try {
            if (!Files.exists(this.rootPath)) {
                Files.createDirectory(this.rootPath);
            }

            var name = "IMG_%s_%s.jpg".formatted(System.currentTimeMillis(),
                    this.random.nextInt(0, 99999));
            var path = this.rootPath.resolve(name);
            Files.copy(in, path);
            return name;
        } catch (IOException ioException) {
            LOG.info(ioException.getMessage());
            return null;
        }
    }

    @Override
    public Resource getImage(String fileName) {
        var filePath = this.rootPath.resolve(fileName);
        if (Files.exists(filePath))
            return new FileSystemResource(filePath);
        else
            return null;
    }
}
