package com.sistDistribuidos.server.controller;

import com.sistDistribuidos.server.dto.CrearTemaRequest;
import com.sistDistribuidos.server.dto.TemaDto;
import com.sistDistribuidos.server.entity.Usuario;
import com.sistDistribuidos.server.service.TemaService;
import com.sistDistribuidos.server.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/temas")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class TemaController {

    private final TemaService temaService;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<TemaDto>> obtenerTemas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<TemaDto> temas = temaService.obtenerTemas(pageable);
        return ResponseEntity.ok(temas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemaDto> obtenerTemaPorId(@PathVariable Long id) {
        return temaService.obtenerTemaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TemaDto> crearTema(
            @Valid @RequestBody CrearTemaRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        
        String auth0Id = jwt.getSubject();
        Usuario usuario = usuarioService.findByAuth0Id(auth0Id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        TemaDto temaCreado = temaService.crearTema(request.getTitulo(), request.getDescripcion(), usuario);
        return ResponseEntity.ok(temaCreado);
    }
} 