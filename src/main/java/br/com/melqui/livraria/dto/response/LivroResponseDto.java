package br.com.melqui.livraria.dto.response;

import java.util.UUID;
import java.math.BigDecimal;

public record LivroResponseDto(
        UUID id,
        String titulo,
        String autor,
        String categoria,
        String editora,
        String isbn,
        Integer anoPublicacao,
        BigDecimal preco) {
}
