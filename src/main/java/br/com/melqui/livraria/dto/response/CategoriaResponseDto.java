package br.com.melqui.livraria.dto.response;

import java.util.UUID;

public record CategoriaResponseDto(
        UUID id,
        String nome,
        String descricao) {
}
