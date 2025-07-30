package com.sistDistribuidos.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.upload.path}")
    private String uploadPath;

    public String guardarArchivo(MultipartFile archivo) throws IOException {
        // Crear directorio si no existe
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Generar nombre único para el archivo
        String nombreOriginal = archivo.getOriginalFilename();
        String extension = nombreOriginal != null ? nombreOriginal.substring(nombreOriginal.lastIndexOf(".")) : "";
        String nombreArchivo = UUID.randomUUID().toString() + extension;

        // Guardar archivo
        Path rutaArchivo = uploadDir.resolve(nombreArchivo);
        Files.copy(archivo.getInputStream(), rutaArchivo);

        // Retornar URL relativa
        return "/api/upload/" + nombreArchivo;
    }

    public boolean esImagen(MultipartFile archivo) {
        String contentType = archivo.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    public boolean esGif(MultipartFile archivo) {
        String contentType = archivo.getContentType();
        return "image/gif".equals(contentType);
    }
} 