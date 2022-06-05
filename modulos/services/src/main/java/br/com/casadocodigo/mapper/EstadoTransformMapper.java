package br.com.casadocodigo.mapper;

import br.com.casadocodigo.domain.Estado;
import br.com.casadocodigo.dto.EstadoDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EstadoTransformMapper {

    public abstract EstadoDTO toDTO(Estado estado);

    public abstract Estado toEntity(EstadoDTO dto);

    public abstract List<EstadoDTO> toListDTO(List<Estado> all);
}
