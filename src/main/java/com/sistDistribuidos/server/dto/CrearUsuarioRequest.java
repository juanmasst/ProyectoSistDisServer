package com.sistDistribuidos.server.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para crear un nuevo usuario")
public class CrearUsuarioRequest {

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe tener un formato válido")
    @Schema(description = "Email del usuario", example = "usuario@ejemplo.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = "URL de la foto de perfil del usuario", example = "https://ejemplo.com/foto.jpg")
    private String fotoPerfil;

    @NotBlank(message = "El auth0Id es requerido")
    @Schema(description = "Identificador único de Auth0", example = "auth0|1234567890", requiredMode = Schema.RequiredMode.REQUIRED)
    private String auth0Id;
}
