package com.sistDistribuidos.server.controller;

import com.sistDistribuidos.server.dto.CrearMensajeRequest;
import com.sistDistribuidos.server.dto.MensajeDto;
import com.sistDistribuidos.server.entity.Usuario;
import com.sistDistribuidos.server.service.MensajeService;
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
@RequestMapping("/api/mensajes")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class MensajeController {

    private final MensajeService mensajeService;
    private final UsuarioService usuarioService;

    @GetMapping("/tema/{temaId}")
    public ResponseEntity<Page<MensajeDto>> obtenerMensajesPorTema(
            @PathVariable Long temaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<MensajeDto> mensajes = mensajeService.obtenerMensajesPorTema(temaId, pageable);
        return ResponseEntity.ok(mensajes);
    }

    @PostMapping
    public ResponseEntity<MensajeDto> crearMensaje(
            @Valid @RequestBody CrearMensajeRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        
        String auth0Id = jwt.getSubject();
        Usuario usuario = usuarioService.findByAuth0Id(auth0Id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        MensajeDto mensajeCreado = mensajeService.crearMensaje(
                request.getTexto(), 
                request.getUrlImagen(), 
                usuario, 
                request.getTemaId()
        );
        return ResponseEntity.ok(mensajeCreado);
    }
} 