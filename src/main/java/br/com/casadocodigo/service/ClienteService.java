package br.com.casadocodigo.service;

import br.com.casadocodigo.domain.Cliente;
import br.com.casadocodigo.dto.ClienteDTO;
import br.com.casadocodigo.mapper.ClienteTransformMapper;
import br.com.casadocodigo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    ClienteTransformMapper mapper;

    public List<ClienteDTO> listarTodos() {
        return mapper.toListDTO(clienteRepository.findAll());
    }

    public ClienteDTO buscarClientePorId(UUID id) {
        Optional<Cliente> estado = clienteRepository.findById(id);
        return estado.isPresent() ? mapper.toDTO(estado.get()) : null;
    }

    public ClienteDTO criarCliente(ClienteDTO dto) {
        Cliente cliente = mapper.toEntity(dto);
        return mapper.toDTO(clienteRepository.save(cliente));
    }

}
