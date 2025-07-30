package com.sistDistribuidos.server.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del usuario")
public class UsuarioDto {

    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Email del usuario", example = "usuario@ejemplo.com")
    private String email;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "URL de la foto de perfil", example = "https://ejemplo.com/foto.jpg")
    private String fotoPerfil;

    @Schema(description = "Fecha y hora de creación del usuario", example = "2025-07-28T10:30:00")
    private LocalDateTime fechaCreacion;
}
