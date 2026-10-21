package br.com.melqui.livraria.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastrar ou atualizar um autor")
public record AutorRequestDto(
        @Schema(example = "Machado de Assis") @NotBlank @Size(max = 255) String nome,
        @Schema(example = "Brasileira") @Size(max = 255) String nacionalidade) {
}
