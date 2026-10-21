package br.com.melqui.livraria.exception;

import java.util.UUID;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(UUID id) {
        super("Categoria não encontrada: "+id);
    }
}
