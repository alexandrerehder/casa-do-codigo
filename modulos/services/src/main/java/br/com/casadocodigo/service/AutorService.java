package br.com.casadocodigo.service;

import br.com.casadocodigo.domain.Autor;
import br.com.commons.dto.AutorDTO;
import br.com.casadocodigo.mapper.AutorTransformMapper;
import br.com.casadocodigo.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public AutorDTO criarAutor(AutorDTO dto) {
        Autor autor = mapper.toEntity(dto);
        Matcher mather = pattern.matcher(autor.getEmail());

        AutorDTO validado = null;
        if (mather.find()) {
            validado = mapper.toDTO(autorRepository.save(autor));
        } else {
            validado = new AutorDTO();
        }
        return validado;
    }

    @Transactional
    public AutorDTO buscarAutorPorId(UUID id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.isPresent() ? mapper.toDTO(autor.get()) : new AutorDTO();
    }

    public AutorDTO buscarAutorPorEmail(AutorDTO dto) {
        Optional<Autor> autor = autorRepository.findByEmail(dto.getEmail());
        return autor.isPresent() ? mapper.toDTO(autor.get()) : new AutorDTO();
    }
}
