package br.com.casadocodigo.nativeQueryProjection;

import br.com.casadocodigo.domain.Autor;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface LivroDetalhes {

    String getTitulo();
    String getResumo();
    String  getDescricao();
    String getSumario();
    BigDecimal getPreco();
    Integer getPaginas();
    String getIsbn();
    LocalDate getLancamento();
    Autor getAutor();
}
