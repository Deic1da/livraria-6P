package br.com.melqui.livraria.mapper;

import br.com.melqui.livraria.dto.request.AutorRequestDto;
import br.com.melqui.livraria.dto.response.AutorResponseDto;
import br.com.melqui.livraria.model.Autor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutorMapper {

    public Autor toEntity(AutorRequestDto dto){
        Autor autor = new Autor();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());

        return autor;
    }

    public AutorResponseDto toResponse(Autor autor){
        return new AutorResponseDto(
            autor.getId(),
            autor.getNome(),
            autor.getNacionalidade()
        );
    }

    public List<AutorResponseDto> toResponseList(List<Autor> autores){
        return autores.stream().map(this::toResponse).toList();
    }
}
