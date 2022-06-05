package br.com.casadocodigo.mapper;

import br.com.casadocodigo.domain.Categoria;
import br.com.casadocodigo.dto.CategoriaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CategoriaTransformMapper {

    public abstract CategoriaDTO toDTO(Categoria categoria);

    public abstract Categoria toEntity(CategoriaDTO dto);

}
