package br.com.casadocodigo.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull @NotBlank(message = "Please enter estado name")
    private String estado;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Pais pais;
}