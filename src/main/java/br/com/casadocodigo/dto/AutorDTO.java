package br.com.casadocodigo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AutorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    @NotNull @NotBlank
    private String nome;
    private String email;
    @NotNull @NotBlank @Size(max=400)
    private String descricao;
    private LocalDateTime dataCriacao;
}
