package br.com.casadocodigo.service;

import br.com.casadocodigo.domain.Pais;
import br.com.casadocodigo.dto.PaisDTO;
import br.com.casadocodigo.mapper.PaisTransformMapper;
import br.com.casadocodigo.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    PaisTransformMapper mapper;

    public List<PaisDTO> listarTodos() {
        return mapper.toListDTO(paisRepository.findAll());
    }

    public PaisDTO buscarPaisPorId(UUID id) {
        Optional<Pais> pais = paisRepository.findById(id);
        return pais.isPresent() ? mapper.toDTO(pais.get()) : null;
    }

    public PaisDTO criarPais(PaisDTO dto) {
        Pais pais = mapper.toEntity(dto);
        return mapper.toDTO(paisRepository.save(pais));
    }
}
