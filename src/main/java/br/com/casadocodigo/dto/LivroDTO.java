package br.com.casadocodigo.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class LivroDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    @NotNull @NotBlank
    private String titulo;
    @NotNull @NotBlank @Size(max=500)
    private String resumo;
    @NotNull @NotBlank
    private String sumario;
    @NotNull @Min(value = 20)
    private BigDecimal preco;
    @NotNull @Min(value = 100)
    private Integer paginas;
    @NotNull
    private Integer isbn;
    @NotNull
    private LocalDate dataLancamento;
    @NotNull @NotBlank
    private AutorDTO autor;
    @NotNull @NotBlank
    private CategoriaDTO categoria;
}
