package br.com.melqui.livraria.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para fazer login")
public record LoginRequestDto(
        @Schema(example = "usuario@email.com") @NotBlank String email,
        @Schema(example = "Senha@123") @NotBlank String senha) {
}
