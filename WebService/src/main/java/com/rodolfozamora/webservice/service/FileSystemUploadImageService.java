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
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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

    //@Value("${images.folder.root-path: ${user.dir}/images}")
    @Value("${images.folder.root-path: user.dir/images}")
    private Path rootPath;

    @Value("${images.size.height: 500}")
    private int imageWidth;
    @Value("${images.size.width: 500}")
    private int imageHeight;

    @PostConstruct
    private void postConstructor() {
        LOG.info("Image folder created on: " + rootPath.toString());
    }

    @Override
    public String saveImage(InputStream in, String type) {
        try {
            if (!Files.exists(this.rootPath)) {
                Files.createDirectory(this.rootPath);
            }
            var name = "IMG_%s_%s.%s".formatted(System.currentTimeMillis(),
                    this.random.nextInt(0, 99999), type);
            var path = this.rootPath.resolve(name);

            //Resize image to 600x600 px
            var buffOriginal = ImageIO.read(in);
            var buffResize= resizeImage(buffOriginal, imageWidth, imageHeight);
            ImageIO.write(buffResize, type, path.toFile());

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

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        var resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        var outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}
