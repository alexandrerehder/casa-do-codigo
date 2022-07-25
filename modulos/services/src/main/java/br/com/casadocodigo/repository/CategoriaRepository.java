package br.com.casadocodigo.repository;

import br.com.casadocodigo.domain.Categoria;
import br.com.casadocodigo.domain.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

    @Query("SELECT c FROM Categoria c WHERE c.nome =:nome")
    Optional<Categoria> findByName(String nome);
}
