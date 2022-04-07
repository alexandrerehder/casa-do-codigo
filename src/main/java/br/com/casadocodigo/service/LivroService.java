package br.com.casadocodigo.service;

import br.com.casadocodigo.domain.Livro;
import br.com.casadocodigo.dto.AutorDTO;
import br.com.casadocodigo.dto.LivroDTO;
import br.com.casadocodigo.mapper.LivroTransformMapper;
import br.com.casadocodigo.repository.AutorRepository;
import br.com.casadocodigo.repository.CategoriaRepository;
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
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    LivroTransformMapper mapper;

    public List<LivroDTO> listarTodos() {
        return mapper.toListDTO(livroRepository.findAll());
    }

    public LivroDTO criarLivro(LivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        LocalDate today = LocalDate.now();

        Livro livroValidado = null;
        if(today.isBefore(livro.getDataLancamento())) {
            livroValidado = livroRepository.save(livro);
        } else {
        }
        return mapper.toDTO(livroValidado);
    }

    public LivroDTO buscarLivroPorId(UUID id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.isPresent() ? mapper.toDTO(livro.get()) : new LivroDTO();
    }
}
