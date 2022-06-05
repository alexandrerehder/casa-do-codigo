package br.com.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class QueueEstudosResponseDTO implements Serializable{
	
	private static final long serialVersionUID = -92711001580885805L;

	private String mensagemRetorno;
	private boolean erro;
	private Object objeto;

}
