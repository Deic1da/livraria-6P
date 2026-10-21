package br.com.melqui.livraria.dto.response;

import java.util.UUID;

public record EditoraResponseDto(
        UUID id,
        String nome,
        String cidade) {
}
