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
public class TemaDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private UsuarioDto autor;
    private LocalDateTime fechaCreacion;
    private Long cantidadMensajes;
} 