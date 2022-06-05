package br.com.casadocodigo.domain;

import br.com.casadocodigo.shared.LocalDateAttributeConverter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique=true) @NotNull @NotBlank
    private String titulo;
    @NotNull @NotBlank @Size(max=500)
    private String resumo;
    @NotNull @NotBlank
    private String descricao;
    @NotNull @NotBlank
    private String sumario;
    @NotNull @Min(value = 20)
    private BigDecimal preco;
    @NotNull @Min(value = 100)
    private Integer paginas;
    @Column(unique=true) @NotNull @NotBlank
    private String isbn;
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate lancamento;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Categoria categoria;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Autor autor;
}
