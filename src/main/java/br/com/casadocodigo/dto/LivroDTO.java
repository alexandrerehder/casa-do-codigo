package br.com.casadocodigo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class LivroDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String titulo;
    private String resumo;
    private String sumario;
    private BigDecimal preco;
    private Integer paginas;
    private Integer isbn;
    private LocalDate dataLancamento;

    private AutorDTO get;
    private CategoriaDTO categoria;
}
