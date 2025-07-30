package com.sistDistribuidos.server.controller;

import com.sistDistribuidos.server.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class FileController {

    private final FileService fileService;

    @Value("${file.upload.path}")
    private String uploadPath;

    @PostMapping
    public ResponseEntity<Map<String, String>> subirArchivo(@RequestParam("file") MultipartFile archivo) {
        log.info("Iniciando subida de archivo: {} ({} bytes)", archivo.getOriginalFilename(), archivo.getSize());
        Map<String, String> respuesta = new HashMap<>();
        
        try {
            // Validar que sea una imagen
            if (!fileService.esImagen(archivo)) {
                log.warn("Archivo rechazado - no es una imagen: {}", archivo.getContentType());
                respuesta.put("error", "Solo se permiten archivos de imagen");
                return ResponseEntity.badRequest().body(respuesta);
            }

            // Validar tamaño (máximo 10MB)
            if (archivo.getSize() > 10 * 1024 * 1024) {
                log.warn("Archivo rechazado - demasiado grande: {} bytes", archivo.getSize());
                respuesta.put("error", "El archivo es demasiado grande. Máximo 10MB");
                return ResponseEntity.badRequest().body(respuesta);
            }

            String urlArchivo = fileService.guardarArchivo(archivo);
            log.info("Archivo subido exitosamente: {}", urlArchivo);
            respuesta.put("url", urlArchivo);
            respuesta.put("mensaje", "Archivo subido correctamente");
            
            return ResponseEntity.ok(respuesta);
            
        } catch (IOException e) {
            log.error("Error al subir archivo: {}", e.getMessage(), e);
            respuesta.put("error", "Error al subir el archivo: " + e.getMessage());
            return ResponseEntity.internalServerError().body(respuesta);
        }
    }

    @GetMapping("/{nombreArchivo:.+}")
    public ResponseEntity<Resource> descargarArchivo(@PathVariable String nombreArchivo) {
        log.info("Solicitando archivo: {}", nombreArchivo);
        try {
            Path rutaArchivo = Paths.get(uploadPath).resolve(nombreArchivo).normalize();
            Resource recurso = new UrlResource(rutaArchivo.toUri());
            
            if (recurso.exists() && recurso.isReadable()) {
                log.info("Archivo encontrado: {}", rutaArchivo);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + nombreArchivo + "\"")
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(recurso);
            } else {
                log.warn("Archivo no encontrado: {}", rutaArchivo);
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            log.error("Error al acceder al archivo: {}", e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
} 