package br.com.casadocodigo.mapper;

import br.com.casadocodigo.domain.Cliente;
import br.com.commons.dto.ClienteDTO;
import br.com.commons.dto.ClienteIdDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ClienteTransformMapper {

    public abstract ClienteDTO toDTO(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    public abstract Cliente toEntity(ClienteDTO dto);

    public abstract List<ClienteDTO> toListDTO(List<Cliente> all);

    public abstract ClienteIdDTO toIdDTO(UUID id);
}
