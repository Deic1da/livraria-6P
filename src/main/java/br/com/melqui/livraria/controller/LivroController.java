package br.com.melqui.livraria.controller;

import br.com.melqui.livraria.dto.request.LivroRequestDto;
import br.com.melqui.livraria.dto.response.LivroResponseDto;
import br.com.melqui.livraria.mapper.LivroMapper;
import br.com.melqui.livraria.model.Livro;
import br.com.melqui.livraria.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/livros")
public class LivroController {
    private final LivroService livroService;
    private final LivroMapper livroMapper;

    public LivroController(LivroService livroService, LivroMapper livroMapper) {
        this.livroService = livroService;
        this.livroMapper = livroMapper;
    }

    @PostMapping
    public ResponseEntity<LivroResponseDto> cadastrarLivro(@RequestBody @Valid LivroRequestDto dto) {
        Livro livro = livroMapper.toEntity(dto);
        Livro  livroCriado = livroService.criarLivro(livro);
        URI location = URI.create("/api/v1/livros/" + livroCriado.getId());
        return ResponseEntity.created(location).body(livroMapper.toResponse(livroCriado));
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDto>> listarLivros(){
        List<Livro> livros = livroService.listarLivros();
        return ResponseEntity.ok(livroMapper.toResponseList(livros));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> buscarLivro(@PathVariable UUID id){
        Livro livro = livroService.buscarLivro(id);
        return ResponseEntity.ok(livroMapper.toResponse(livro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDto> atualizarLivro(@PathVariable UUID id, @RequestBody @Valid LivroRequestDto dto){
        Livro livro = livroMapper.toEntity(dto);
        Livro atualizado = livroService.atualizarLivro(id, livro);
        return ResponseEntity.ok(livroMapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable UUID id){
        livroService.excluirLivro(id);
        return ResponseEntity.noContent().build();
    }
}
