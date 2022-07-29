package br.com.casadocodigo.repository;

import br.com.casadocodigo.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    @Query(nativeQuery = true, value = "SELECT Cast(id as varchar) id FROM Cliente")
    UUID findJustId();
}
