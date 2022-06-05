package br.com.casadocodigo.service;

import br.com.casadocodigo.domain.Categoria;
import br.com.casadocodigo.dto.CategoriaDTO;
import br.com.casadocodigo.mapper.CategoriaTransformMapper;
import br.com.casadocodigo.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    CategoriaTransformMapper mapper;

    public CategoriaDTO criarCategoria(CategoriaDTO dto) {
        Categoria categoria = mapper.toEntity(dto);
        return mapper.toDTO(categoriaRepository.save(categoria));
    }

    public CategoriaDTO buscarCategoriaPorId(UUID id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.isPresent() ? mapper.toDTO(categoria.get()) : new CategoriaDTO();
    }
}
