package br.com.casadocodigo.service;

import br.com.casadocodigo.domain.Livro;
import br.com.casadocodigo.mapper.LivroTransformMapper;
import br.com.casadocodigo.nativeQueryProjection.LivroResumido;
import br.com.casadocodigo.repository.LivroRepository;
import br.com.commons.dto.LivroDTO;
import br.com.commons.dto.LivroDetalhesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    LivroTransformMapper mapper;

    public List<LivroDTO> listarTodos() {
        List<Livro> livros = livroRepository.findAll();
        return mapper.toDTO(livros);
    }

    @Transactional
    public LivroDTO criarLivro(LivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        LocalDate today = LocalDate.now();

        Livro livroValidado = null;
        if(today.isBefore(livro.getLancamento())) {
            livroValidado = livroRepository.save(livro);
        }
        return mapper.toDTO(livroValidado);
    }

    public LivroDTO buscarLivroPorId(UUID id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.isPresent() ? mapper.toDTO(livro.get()) : null;
    }

    public LivroDetalhesDTO buscarDetalhesLivroPorId(UUID id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.isPresent() ? mapper.toDetalhesDTO(livro.get()) : null;
    }
}

