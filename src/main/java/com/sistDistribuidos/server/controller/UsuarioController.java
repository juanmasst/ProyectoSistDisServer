package com.sistDistribuidos.server.controller;

import com.sistDistribuidos.server.dto.CrearUsuarioRequest;
import com.sistDistribuidos.server.dto.UsuarioDto;
import com.sistDistribuidos.server.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(
        summary = "Crear un nuevo usuario",
        description = "Crea un nuevo usuario en el sistema con los datos proporcionados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuario creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Usuario ya existe (email o auth0Id duplicado)",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    public ResponseEntity<UsuarioDto> crearUsuario(
        @Parameter(description = "Datos del usuario a crear", required = true)
        @Valid @RequestBody CrearUsuarioRequest request) {
        try {
            // Verificar si el usuario ya existe por email o auth0Id
            if (usuarioService.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 - Usuario ya existe
            }

            if (usuarioService.findByAuth0Id(request.getAuth0Id()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 - Usuario ya existe
            }

            UsuarioDto nuevoUsuario = usuarioService.crearUsuario(
                request.getEmail(),
                request.getNombre(),
                request.getAuth0Id(),
                request.getFotoPerfil()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener usuario por ID",
        description = "Recupera la información de un usuario específico por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuario encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado",
            content = @Content
        )
    })
    public ResponseEntity<UsuarioDto> obtenerUsuario(
        @Parameter(description = "ID del usuario", required = true, example = "1")
        @PathVariable Long id) {
        return usuarioService.findById(id)
                .map(usuario -> ResponseEntity.ok(usuarioService.mapToDto(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }
}
