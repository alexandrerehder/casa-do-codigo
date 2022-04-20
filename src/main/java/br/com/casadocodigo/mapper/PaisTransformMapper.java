package br.com.casadocodigo.mapper;

import br.com.casadocodigo.domain.Pais;
import br.com.casadocodigo.dto.PaisDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PaisTransformMapper {

    public abstract PaisDTO toDTO(Pais pais);

    public abstract Pais toEntity(PaisDTO dto);

    public abstract List<PaisDTO> toListDTO(List<Pais> all);
}