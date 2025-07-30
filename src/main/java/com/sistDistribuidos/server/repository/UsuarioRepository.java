package com.sistDistribuidos.server.repository;

import com.sistDistribuidos.server.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);
    
    Optional<Usuario> findByAuth0Id(String auth0Id);
    
    boolean existsByEmail(String email);
    
    boolean existsByAuth0Id(String auth0Id);
} 