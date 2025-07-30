package com.sistDistribuidos.server.controller;

import com.sistDistribuidos.server.dto.UsuarioDto;
import com.sistDistribuidos.server.dto.TemaDto;
import com.sistDistribuidos.server.entity.Usuario;
import com.sistDistribuidos.server.service.UsuarioService;
import com.sistDistribuidos.server.service.TemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Autenticación", description = "Operaciones relacionadas con la autenticación y autorización")
public class AuthController {

    private final UsuarioService usuarioService;
    private final TemaService temaService;

    @GetMapping("/me")
    @Operation(
        summary = "Obtener usuario autenticado",
        description = "Obtiene la información del usuario actualmente autenticado. Si el usuario no existe en la base de datos, devuelve 404.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuario encontrado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado en la base de datos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Token de autenticación inválido o faltante",
            content = @Content
        )
    })
    public ResponseEntity<UsuarioDto> obtenerUsuarioActual(@AuthenticationPrincipal Jwt jwt) {
        String auth0Id = jwt.getSubject();
        
        // Buscar usuario existente
        Optional<Usuario> usuarioOpt = usuarioService.findByAuth0Id(auth0Id);
        
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioService.mapToDto(usuarioOpt.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/public/health")
    @Operation(
        summary = "Verificar estado del servidor",
        description = "Endpoint público para verificar que el servidor está funcionando correctamente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Servidor funcionando correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "object", example = "{\"status\": \"OK\", \"message\": \"Servidor funcionando correctamente\"}")
            )
        )
    })
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "OK", "message", "Servidor funcionando correctamente"));
    }

    @GetMapping("/public/user-exists/{auth0Id}")
    @Operation(
        summary = "Verificar si un usuario existe",
        description = "Endpoint público para verificar si un usuario existe en la base de datos por su auth0Id"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Resultado de la verificación",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "object", example = "{\"exists\": true}")
            )
        )
    })
    public ResponseEntity<Map<String, Boolean>> checkUserExists(@PathVariable String auth0Id) {
        boolean exists = usuarioService.findByAuth0Id(auth0Id).isPresent();
        return ResponseEntity.ok(Map.of("exists", exists));
    }
    
    @GetMapping("/me/temas")
    @Operation(
        summary = "Obtener temas del usuario autenticado",
        description = "Obtiene todos los temas creados por el usuario actualmente autenticado",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Temas obtenidos exitosamente",
            content = @Content(
                mediaType = "application/json"
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Token de autenticación inválido o faltante",
            content = @Content
        )
    })
    public ResponseEntity<Page<TemaDto>> obtenerTemasDelUsuario(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String auth0Id = jwt.getSubject();
        
        Pageable pageable = PageRequest.of(page, size);
        Page<TemaDto> temas = temaService.obtenerTemasPorAuth0Id(auth0Id, pageable);
        
        return ResponseEntity.ok(temas);
    }
}
