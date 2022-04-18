package br.com.casadocodigo.mapper;

import br.com.casadocodigo.domain.Livro;
import br.com.casadocodigo.dto.LivroDTO;
import br.com.casadocodigo.dto.LivroDetalhesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class LivroTransformMapper {

    public abstract LivroDTO toDTO(Livro livro);

    public abstract Livro toEntity(LivroDTO dto);

    public abstract LivroDetalhesDTO toDetalhesDTO(Livro livro);
}
