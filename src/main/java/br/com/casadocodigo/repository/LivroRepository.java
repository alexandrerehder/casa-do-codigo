package br.com.casadocodigo.repository;

import br.com.casadocodigo.domain.Livro;
import br.com.casadocodigo.nativeQueryProjection.LivroDetalhes;
import br.com.casadocodigo.nativeQueryProjection.LivroResumido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    @Query(nativeQuery = true, value = "SELECT Cast(id as varchar) id, livro.titulo FROM Livro")
    List<LivroResumido> findAllLivros();

    @Query(nativeQuery = true, value =
            "SELECT l.titulo, l.resumo, l.descricao, l.preco, l.sumario, l.paginas, l.isbn, l.lancamento, a.nome " +
                    "FROM Livro l " +
                    "INNER JOIN autor a ON l.autor_id = a.id ")
    Optional<LivroDetalhes> findDetalhesLivro(UUID id);
}

