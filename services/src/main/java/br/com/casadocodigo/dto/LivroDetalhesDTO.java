package br.com.casadocodigo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LivroDetalhesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String titulo;
    private String resumo;
    private String descricao;
    private String sumario;
    private BigDecimal preco;
    private Integer paginas;
    private String isbn;
    private LocalDate lancamento;
    private AutorDetalhesDTO autor;
}
