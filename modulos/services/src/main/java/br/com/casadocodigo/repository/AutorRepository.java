package br.com.casadocodigo.repository;

import br.com.casadocodigo.domain.Autor;
import br.com.casadocodigo.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {

    @Query("SELECT a FROM Autor a WHERE a.email =:email")
    Optional<Autor> findByEmail(String email);
}
