package br.com.casadocodigo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AutorDetalhesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String descricao;

}
