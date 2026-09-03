package br.com.melqui.livraria.exception;

import java.util.UUID;

public class LivroNaoEncontradoException extends RuntimeException {
    public LivroNaoEncontradoException(UUID id) {
        super("Livro não encontrado: "+id);
    }
}
