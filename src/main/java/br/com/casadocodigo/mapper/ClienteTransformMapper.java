package br.com.casadocodigo.mapper;

import br.com.casadocodigo.domain.Cliente;
import br.com.casadocodigo.dto.ClienteDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ClienteTransformMapper {

    public abstract ClienteDTO toDTO(Cliente cliente);

    public abstract Cliente toEntity(ClienteDTO dto);

    public abstract List<ClienteDTO> toListDTO(List<Cliente> all);
}
