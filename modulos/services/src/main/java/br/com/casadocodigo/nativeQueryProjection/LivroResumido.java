package br.com.casadocodigo.nativeQueryProjection;

import java.io.Serializable;
import java.util.UUID;

public abstract class LivroResumido implements Serializable {

    private static final long serialVersionUID = 1L;

    abstract UUID getId();
    abstract String getTitulo();
}