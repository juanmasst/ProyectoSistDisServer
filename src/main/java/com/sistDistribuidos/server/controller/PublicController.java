package com.sistDistribuidos.server.controller;

import com.sistDistribuidos.server.dto.TemaDto;
import com.sistDistribuidos.server.service.TemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PublicController {

    private final TemaService temaService;

    @GetMapping("/temas")
    public ResponseEntity<Page<TemaDto>> obtenerTemasPublicos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<TemaDto> temas = temaService.obtenerTemas(pageable);
        return ResponseEntity.ok(temas);
    }

    @GetMapping("/temas/{id}")
    public ResponseEntity<TemaDto> obtenerTemaPublicoPorId(@PathVariable Long id) {
        return temaService.obtenerTemaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/health")
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "OK",
            "message", "Servidor funcionando correctamente",
            "timestamp", System.currentTimeMillis()
        ));
    }
} 