package br.com.melqui.livraria.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criar uma conta")
public record RegisterRequestDto(
        @Schema(example = "usuario@email.com") @NotBlank @Email @Size(max = 255) String email,
        @Schema(example = "Senha@123") @NotBlank @Size(min = 6, max = 255) String senha) {
}
