package com.sistDistribuidos.server.repository;

import com.sistDistribuidos.server.entity.Tema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {
    
    Page<Tema> findAllByOrderByFechaCreacionDesc(Pageable pageable);
    
    @Query("SELECT t FROM Tema t LEFT JOIN FETCH t.autor ORDER BY t.fechaCreacion DESC")
    Page<Tema> findAllWithAutor(Pageable pageable);
    
    // Nuevo método para obtener temas por autor
    @Query("SELECT t FROM Tema t LEFT JOIN FETCH t.autor WHERE t.autor.id = :autorId ORDER BY t.fechaCreacion DESC")
    Page<Tema> findByAutorIdWithAutor(@Param("autorId") Long autorId, Pageable pageable);
    
    // Método alternativo usando el auth0Id directamente
    @Query("SELECT t FROM Tema t LEFT JOIN FETCH t.autor WHERE t.autor.auth0Id = :auth0Id ORDER BY t.fechaCreacion DESC")
    Page<Tema> findByAutorAuth0IdWithAutor(@Param("auth0Id") String auth0Id, Pageable pageable);
}