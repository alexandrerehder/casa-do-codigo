package br.com.casadocodigo.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull @NotBlank
    private String nome;
    @Column(unique=true) @NotNull @NotBlank
    private String email;
    @NotNull @NotBlank @Size(max=400)
    private String descricao;
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
