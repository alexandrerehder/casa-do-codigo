package br.com.casadocodigo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class CategoriaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    @NotNull @NotBlank
    private String nome;
}
