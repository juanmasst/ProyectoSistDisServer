package com.sistDistribuidos.server.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MensajeDto {
    private Long id;
    private String texto;
    private String urlImagen;
    private UsuarioDto autor;
    private Long temaId;
    private LocalDateTime fechaCreacion;
} 