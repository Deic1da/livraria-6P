package br.com.melqui.livraria.exception;

import java.util.UUID;

public class EditoraNaoEncontradaException extends RuntimeException {
    public EditoraNaoEncontradaException(UUID id) {
        super("Editora não encontrada: "+id);
    }
}
