package br.com.melqui.livraria.controller;

import br.com.melqui.livraria.model.Livro;
import br.com.melqui.livraria.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/livros")
public class LivroController {
    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<Livro> cadastrarLivro(@RequestBody Livro livro){
        Livro  livroCriado = livroService.criarLivro(livro);
        URI location = URI.create("/api/v1/livros/" + livroCriado.getId());
        return ResponseEntity.created(location).body( livroCriado);
    }


}
