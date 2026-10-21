package br.com.melqui.livraria.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastrar ou atualizar uma editora")
public record EditoraRequestDto(
        @Schema(example = "Companhia das Letras") @NotBlank @Size(max = 255) String nome,
        @Schema(example = "São Paulo") @Size(max = 255) String cidade) {
}
