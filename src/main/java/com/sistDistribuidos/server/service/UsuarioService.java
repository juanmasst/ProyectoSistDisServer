package com.sistDistribuidos.server.service;

import com.sistDistribuidos.server.dto.UsuarioDto;
import com.sistDistribuidos.server.entity.Usuario;
import com.sistDistribuidos.server.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDto crearUsuario(String email, String nombre, String auth0Id, String fotoPerfil) {
        Usuario usuario = Usuario.builder()
                .email(email)
                .nombre(nombre)
                .auth0Id(auth0Id)
                .fotoPerfil(fotoPerfil)
                .build();
        
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return mapToDto(savedUsuario);
    }

    public Optional<Usuario> findByAuth0Id(String auth0Id) {
        return usuarioRepository.findByAuth0Id(auth0Id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioDto mapToDto(Usuario usuario) {
        return UsuarioDto.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .fotoPerfil(usuario.getFotoPerfil())
                .fechaCreacion(usuario.getFechaCreacion())
                .build();
    }
}
