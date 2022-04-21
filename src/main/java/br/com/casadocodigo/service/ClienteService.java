package br.com.casadocodigo.service;

import br.com.casadocodigo.domain.Cliente;
import br.com.casadocodigo.dto.ClienteDTO;
import br.com.casadocodigo.dto.ClienteIdDTO;
import br.com.casadocodigo.mapper.ClienteTransformMapper;
import br.com.casadocodigo.repository.ClienteRepository;
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
        Optional<Cliente> estado = clienteRepository.findById(id);
        return estado.isPresent() ? mapper.toDTO(estado.get()) : null;
    }

    public ClienteIdDTO criarCliente(ClienteDTO dto) {
        Cliente cliente = mapper.toEntity(dto);
        Matcher matcher = pattern.matcher(cliente.getEmail());

        ClienteIdDTO retorno = null;
        if (!matcher.find()) {
            retorno = null;
        } else {
            clienteRepository.save(cliente);
            retorno = mapper.toIdDTO(clienteRepository.findJustId());
        }
        return retorno;
    }
}
