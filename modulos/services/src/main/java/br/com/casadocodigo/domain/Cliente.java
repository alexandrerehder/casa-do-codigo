package br.com.casadocodigo.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique=true)
    @NotNull @NotBlank
    private String email;
    @NotNull @NotBlank
    private String nome;
    @NotNull @NotBlank
    private String sobrenome;
    @Column(unique=true)
    @NotNull @NotBlank
    private String documento;
    @NotNull @NotBlank
    private String endereco;
    @NotNull @NotBlank
    private String cidade;
    private String complemento;
    @NotNull @NotBlank
    private String telefone;
    @NotNull @NotBlank
    private String cep;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Pais pais;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Estado estado;
}
