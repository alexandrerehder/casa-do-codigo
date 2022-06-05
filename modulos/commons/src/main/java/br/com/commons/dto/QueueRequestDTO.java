package br.com.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class QueueRequestDTO implements Serializable {

	private static final long serialVersionUID = 5277553801366314342L;
	private Object objeto;
	private CrudMethod crudMethod;
}
