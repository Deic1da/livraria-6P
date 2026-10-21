package br.com.melqui.livraria.exception;

import java.util.UUID;

public class AutorNaoEncontradoException extends RuntimeException {
    public AutorNaoEncontradoException(UUID id) {
        super("Autor não encontrado: "+id);
    }
}
