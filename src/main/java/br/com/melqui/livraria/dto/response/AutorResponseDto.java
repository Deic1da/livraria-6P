package br.com.melqui.livraria.dto.response;

import java.util.UUID;

public record AutorResponseDto(
        UUID id,
        String nome,
        String nacionalidade) {
}
