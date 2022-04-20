package br.com.casadocodigo.repository;

import br.com.casadocodigo.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, UUID> {

    @Query("SELECT estado FROM Estado WHERE estado = :nomeEstado and pais_id = :id")
    Optional<Estado> findByName(String nomeEstado, UUID id);
}
