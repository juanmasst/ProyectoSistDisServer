package com.sistDistribuidos.server.repository;

import com.sistDistribuidos.server.entity.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    
    Page<Mensaje> findByTemaIdOrderByFechaCreacionAsc(Long temaId, Pageable pageable);
    
    @Query("SELECT m FROM Mensaje m LEFT JOIN FETCH m.autor WHERE m.tema.id = :temaId ORDER BY m.fechaCreacion ASC")
    Page<Mensaje> findByTemaIdWithAutor(@Param("temaId") Long temaId, Pageable pageable);
    
    long countByTemaId(Long temaId);
} 