package br.com.melqui.livraria.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Dados para cadastrar ou atualizar um livro; autor, categoria e editora são os IDs já cadastrados")
public record LivroRequestDto(
        @Schema(example = "Dom Casmurro") @NotBlank @Size(max = 255) String titulo,
        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") @NotNull UUID autor,
        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") @NotNull UUID categoria,
        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") @NotNull UUID editora,
        @Schema(example = "978-85-359-0277-8") @NotBlank @Size(max = 255) String isbn,
        @Schema(example = "1899") @NotNull @Positive Integer anoPublicacao,
        @Schema(example = "39.90") @NotNull @Positive BigDecimal preco) {
}
