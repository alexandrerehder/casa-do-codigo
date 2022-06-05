package br.com.casadocodigo.mapper;

import br.com.casadocodigo.domain.Livro;
import br.com.commons.dto.LivroDTO;
import br.com.commons.dto.LivroDetalhesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class LivroTransformMapper {

    public abstract LivroDTO toDTO(Livro livro);

    public abstract Livro toEntity(LivroDTO dto);

    public abstract LivroDetalhesDTO toDetalhesDTO(Livro livro);
}
