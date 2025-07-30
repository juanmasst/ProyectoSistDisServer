package com.sistDistribuidos.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class FileUploadConfig implements CommandLineRunner {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public void run(String... args) throws Exception {
        // Crear directorio de uploads si no existe
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
            log.info("Directorio de uploads creado: {}", uploadDir.toAbsolutePath());
        } else {
            log.info("Directorio de uploads ya existe: {}", uploadDir.toAbsolutePath());
        }
    }
} 