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

    @Query(nativeQuery = true, value = "SELECT livro.titulo, livro.resumo, livro.preco, livro.descricao, livro.sumario, livro.paginas, livro.isbn, livro.lancamento FROM Livro WHERE id = :id")
    Optional<LivroDetalhes> findDetalhesLivro(UUID id);
}
