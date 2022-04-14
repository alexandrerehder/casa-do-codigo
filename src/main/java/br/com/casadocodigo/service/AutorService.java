package br.com.casadocodigo.service;

import br.com.casadocodigo.domain.Autor;
import br.com.casadocodigo.dto.AutorDTO;
import br.com.casadocodigo.mapper.AutorTransformMapper;
import br.com.casadocodigo.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;

import static br.com.casadocodigo.shared.EmailValidation.pattern;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    AutorTransformMapper mapper;

    public AutorDTO criarAutor(AutorDTO dto) {
        Autor autor = mapper.toEntity(dto);
        Matcher mather = pattern.matcher(autor.getEmail());

        AutorDTO validado = null;
        if (!mather.find()) {
            validado = null;
        } else {
            validado = mapper.toDTO(autorRepository.save(autor));
        }
        return validado;
    }

    public AutorDTO buscarAutorPorId(UUID id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.isPresent() ? mapper.toDTO(autor.get()) : new AutorDTO();
    }
}
