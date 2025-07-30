package com.sistDistribuidos.server.service;

import com.sistDistribuidos.server.dto.TemaDto;
import com.sistDistribuidos.server.entity.Tema;
import com.sistDistribuidos.server.entity.Usuario;
import com.sistDistribuidos.server.repository.TemaRepository;
import com.sistDistribuidos.server.repository.MensajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemaService {

    private final TemaRepository temaRepository;
    private final MensajeRepository mensajeRepository;
    private final UsuarioService usuarioService;

    public TemaDto crearTema(String titulo, String descripcion, Usuario autor) {
        Tema tema = Tema.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .autor(autor)
                .build();
        
        Tema savedTema = temaRepository.save(tema);
        return mapToDto(savedTema);
    }

    public Page<TemaDto> obtenerTemas(Pageable pageable) {
        Page<Tema> temas = temaRepository.findAllWithAutor(pageable);
        return temas.map(this::mapToDto);
    }

    public Optional<TemaDto> obtenerTemaPorId(Long id) {
        return temaRepository.findById(id).map(this::mapToDto);
    }
    
    // Nuevo método para obtener temas por usuario
    public Page<TemaDto> obtenerTemasPorUsuario(Long usuarioId, Pageable pageable) {
        Page<Tema> temas = temaRepository.findByAutorIdWithAutor(usuarioId, pageable);
        return temas.map(this::mapToDto);
    }
    
    // Método alternativo usando auth0Id
    public Page<TemaDto> obtenerTemasPorAuth0Id(String auth0Id, Pageable pageable) {
        Page<Tema> temas = temaRepository.findByAutorAuth0IdWithAutor(auth0Id, pageable);
        return temas.map(this::mapToDto);
    }

    public TemaDto mapToDto(Tema tema) {
        Long cantidadMensajes = mensajeRepository.countByTemaId(tema.getId());
        
        return TemaDto.builder()
                .id(tema.getId())
                .titulo(tema.getTitulo())
                .descripcion(tema.getDescripcion())
                .autor(usuarioService.mapToDto(tema.getAutor()))
                .fechaCreacion(tema.getFechaCreacion())
                .cantidadMensajes(cantidadMensajes)
                .build();
    }
}