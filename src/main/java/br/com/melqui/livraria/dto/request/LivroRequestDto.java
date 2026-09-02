package br.com.melqui.livraria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record LivroRequestDto(
        @NotBlank @Size(max = 255) String titulo,
        @NotBlank String autor,
        @NotBlank String isbn,
        @NotNull @Positive Integer anoPublicacao,
        @NotNull @Positive BigDecimal preco) {
}
