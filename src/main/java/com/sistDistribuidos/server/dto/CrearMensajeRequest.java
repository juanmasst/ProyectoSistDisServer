package com.sistDistribuidos.server.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearMensajeRequest {
    
    @NotBlank(message = "El texto del mensaje es obligatorio")
    @Size(max = 2000, message = "El mensaje no puede exceder 2000 caracteres")
    private String texto;
    
    private String urlImagen;
    
    @NotNull(message = "El ID del tema es obligatorio")
    private Long temaId;
} 