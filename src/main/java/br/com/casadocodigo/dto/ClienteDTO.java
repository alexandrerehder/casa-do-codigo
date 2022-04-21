package br.com.casadocodigo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ClienteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String email;
    private String nome;
    private String sobrenome;
    private String documento;
    private String endereco;
    private String cidade;
    private String complemento;
    private PaisDTO pais;
    private EstadoDTO estado;
    private String telefone;
    private String cep;
}
