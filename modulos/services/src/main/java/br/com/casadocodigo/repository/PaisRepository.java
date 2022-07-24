package br.com.casadocodigo.repository;

import br.com.casadocodigo.domain.Autor;
import br.com.casadocodigo.domain.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaisRepository extends JpaRepository<Pais, UUID> {

    @Query("SELECT p FROM Pais p WHERE p.pais =:paisNome")
    Optional<Pais> findByName(String paisNome);
}
