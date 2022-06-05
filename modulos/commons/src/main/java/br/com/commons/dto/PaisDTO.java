package br.com.casadocodigo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class PaisDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String pais;
}


