package br.com.melqui.livraria.mapper;

import br.com.melqui.livraria.dto.request.CategoriaRequestDto;
import br.com.melqui.livraria.dto.response.CategoriaResponseDto;
import br.com.melqui.livraria.model.Categoria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequestDto dto){
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());

        return categoria;
    }

    public CategoriaResponseDto toResponse(Categoria categoria){
        return new CategoriaResponseDto(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao()
        );
    }

    public List<CategoriaResponseDto> toResponseList(List<Categoria> categorias){
        return categorias.stream().map(this::toResponse).toList();
    }
}
