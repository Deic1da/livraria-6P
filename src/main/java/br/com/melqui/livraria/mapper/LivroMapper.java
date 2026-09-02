package br.com.melqui.livraria.mapper;

import br.com.melqui.livraria.dto.request.LivroRequestDto;
import br.com.melqui.livraria.dto.response.LivroResponseDto;
import br.com.melqui.livraria.model.Livro;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LivroMapper {

    public Livro toEntity(LivroRequestDto dto){
        Livro livro = new Livro();
        livro.setTitulo(dto.titulo());
        livro.setAutor(dto.autor());
        livro.setIsbn(dto.isbn());
        livro.setAnoPublicacao(dto.anoPublicacao());
        livro.setPreco(dto.preco());

        return  livro;
    }

    public LivroResponseDto toResponse(Livro livro){
        return new LivroResponseDto(
            livro.getId(),
            livro.getTitulo(),
            livro.getAutor(),
            livro.getIsbn(),
            livro.getAnoPublicacao(),
            livro.getPreco()
        );
    }

    public List<LivroResponseDto> toResponseList(List<Livro> livros){
        return livros.stream().map(this::toResponse).toList();
    }
}
