package com.sistDistribuidos.server.service;

import com.sistDistribuidos.server.dto.MensajeDto;
import com.sistDistribuidos.server.entity.Mensaje;
import com.sistDistribuidos.server.entity.Tema;
import com.sistDistribuidos.server.entity.Usuario;
import com.sistDistribuidos.server.repository.MensajeRepository;
import com.sistDistribuidos.server.repository.TemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MensajeService {

    private final MensajeRepository mensajeRepository;
    private final TemaRepository temaRepository;
    private final UsuarioService usuarioService;

    public MensajeDto crearMensaje(String texto, String urlImagen, Usuario autor, Long temaId) {
        Tema tema = temaRepository.findById(temaId)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado"));
        
        Mensaje mensaje = Mensaje.builder()
                .texto(texto)
                .urlImagen(urlImagen)
                .autor(autor)
                .tema(tema)
                .build();
        
        Mensaje savedMensaje = mensajeRepository.save(mensaje);
        return mapToDto(savedMensaje);
    }

    public Page<MensajeDto> obtenerMensajesPorTema(Long temaId, Pageable pageable) {
        Page<Mensaje> mensajes = mensajeRepository.findByTemaIdWithAutor(temaId, pageable);
        return mensajes.map(this::mapToDto);
    }

    public MensajeDto mapToDto(Mensaje mensaje) {
        return MensajeDto.builder()
                .id(mensaje.getId())
                .texto(mensaje.getTexto())
                .urlImagen(mensaje.getUrlImagen())
                .autor(usuarioService.mapToDto(mensaje.getAutor()))
                .temaId(mensaje.getTema().getId())
                .fechaCreacion(mensaje.getFechaCreacion())
                .build();
    }
} 