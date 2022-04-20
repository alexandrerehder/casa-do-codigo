package br.com.casadocodigo.repository;

import br.com.casadocodigo.domain.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaisRepository extends JpaRepository<Pais, UUID> {
}
