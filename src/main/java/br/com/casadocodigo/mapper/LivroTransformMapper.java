package br.com.casadocodigo.mapper;

import br.com.casadocodigo.domain.Livro;
import br.com.casadocodigo.dto.LivroDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LivroTransformMapper {

    public abstract LivroDTO toDTO(Livro livro);

    public abstract Livro toEntity(LivroDTO dto);

    public abstract List<LivroDTO> toListDTO(List<Livro> all);

}
