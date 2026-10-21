package br.com.melqui.livraria.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastrar ou atualizar uma categoria")
public record CategoriaRequestDto(
        @Schema(example = "Romance") @NotBlank @Size(max = 255) String nome,
        @Schema(example = "Ficção em prosa") @Size(max = 255) String descricao) {
}
