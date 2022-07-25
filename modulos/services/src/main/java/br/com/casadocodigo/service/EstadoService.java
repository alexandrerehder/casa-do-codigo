package br.com.casadocodigo.service;

import br.com.casadocodigo.domain.Estado;
import br.com.commons.dto.EstadoDTO;
import br.com.casadocodigo.mapper.EstadoTransformMapper;
import br.com.casadocodigo.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    EstadoTransformMapper mapper;

    public List<EstadoDTO> listarTodos() {
        return mapper.toListDTO(estadoRepository.findAll());
    }

    public EstadoDTO buscarEstadoPorId(UUID id) {
        Optional<Estado> estado = estadoRepository.findById(id);
        return estado.isPresent() ? mapper.toDTO(estado.get()) : new EstadoDTO();
    }

    public EstadoDTO criarEstado(EstadoDTO dto) {
        Estado estado = mapper.toEntity(dto);
        Optional<Estado> nomeEstado = estadoRepository.findByName(estado.getEstado(), estado.getPais().getId());
        return nomeEstado.isPresent() ? new EstadoDTO() : mapper.toDTO(estadoRepository.save(estado));
    }
}
