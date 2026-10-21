package br.com.melqui.livraria.mapper;

import br.com.melqui.livraria.dto.request.EditoraRequestDto;
import br.com.melqui.livraria.dto.response.EditoraResponseDto;
import br.com.melqui.livraria.model.Editora;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EditoraMapper {

    public Editora toEntity(EditoraRequestDto dto){
        Editora editora = new Editora();
        editora.setNome(dto.nome());
        editora.setCidade(dto.cidade());

        return editora;
    }

    public EditoraResponseDto toResponse(Editora editora){
        return new EditoraResponseDto(
            editora.getId(),
            editora.getNome(),
            editora.getCidade()
        );
    }

    public List<EditoraResponseDto> toResponseList(List<Editora> editoras){
        return editoras.stream().map(this::toResponse).toList();
    }
}
