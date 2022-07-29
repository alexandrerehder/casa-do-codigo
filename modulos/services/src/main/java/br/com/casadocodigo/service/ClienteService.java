package br.com.casadocodigo.service;

import br.com.casadocodigo.domain.Cliente;
import br.com.casadocodigo.mapper.ClienteTransformMapper;
import br.com.casadocodigo.repository.ClienteRepository;
import br.com.commons.dto.ClienteDTO;
import br.com.commons.dto.ClienteIdDTO;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;

import static br.com.casadocodigo.shared.EmailValidation.pattern;

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
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.isPresent() ? mapper.toDTO(cliente.get()) : new ClienteDTO();
    }

    public ClienteDTO criarCliente(ClienteDTO dto) {
        Cliente cliente = mapper.toEntity(dto);

        Matcher matcher = pattern.matcher(cliente.getEmail());

        if (matcher.find()) {
                return mapper.toDTO(clienteRepository.save(cliente));
        } else {
            return new ClienteDTO();
        }
    }
}
