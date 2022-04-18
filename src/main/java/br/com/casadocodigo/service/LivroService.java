package br.com.casadocodigo.service;

import br.com.casadocodigo.domain.Livro;
import br.com.casadocodigo.dto.LivroDTO;
import br.com.casadocodigo.dto.LivroDetalhesDTO;
import br.com.casadocodigo.mapper.LivroTransformMapper;
import br.com.casadocodigo.nativeQueryProjection.LivroDetalhes;
import br.com.casadocodigo.nativeQueryProjection.LivroResumido;
import br.com.casadocodigo.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<LivroResumido> listarTodos() {
        return livroRepository.findAllLivros();
    }

    public LivroDTO criarLivro(LivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        LocalDate today = LocalDate.now();

        Livro livroValidado = null;
        if(today.isBefore(livro.getLancamento())) {
            livroValidado = livroRepository.save(livro);
        } else {
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

