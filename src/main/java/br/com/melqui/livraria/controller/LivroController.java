package br.com.melqui.livraria.controller;

import br.com.melqui.livraria.dto.request.LivroRequestDto;
import br.com.melqui.livraria.dto.response.LivroResponseDto;
import br.com.melqui.livraria.mapper.LivroMapper;
import br.com.melqui.livraria.model.Livro;
import br.com.melqui.livraria.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Livros", description = "Cadastro de livros, ligados a autor, categoria e editora")
@RestController
@RequestMapping("/api/v1/livros")
public class LivroController {
    private final LivroService livroService;
    private final LivroMapper livroMapper;

    public LivroController(LivroService livroService, LivroMapper livroMapper) {
        this.livroService = livroService;
        this.livroMapper = livroMapper;
    }

    @Operation(summary = "Cadastra um livro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Autor, categoria ou editora não encontrado")
    })
    @PostMapping
    public ResponseEntity<LivroResponseDto> cadastrarLivro(@RequestBody @Valid LivroRequestDto dto) {
        Livro livro = livroMapper.toEntity(dto);
        Livro  livroCriado = livroService.criarLivro(livro, dto.autor(), dto.categoria(), dto.editora());
        URI location = URI.create("/api/v1/livros/" + livroCriado.getId());
        return ResponseEntity.created(location).body(livroMapper.toResponse(livroCriado));
    }

    @Operation(summary = "Lista todos os livros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido")
    })
    @GetMapping
    public ResponseEntity<List<LivroResponseDto>> listarLivros(){
        List<Livro> livros = livroService.listarLivros();
        return ResponseEntity.ok(livroMapper.toResponseList(livros));
    }

    @Operation(summary = "Busca um livro pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> buscarLivro(@PathVariable UUID id){
        Livro livro = livroService.buscarLivro(id);
        return ResponseEntity.ok(livroMapper.toResponse(livro));
    }

    @Operation(summary = "Atualiza um livro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado (ou autor, categoria ou editora não encontrado)")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDto> atualizarLivro(@PathVariable UUID id, @RequestBody @Valid LivroRequestDto dto){
        Livro livro = livroMapper.toEntity(dto);
        Livro atualizado = livroService.atualizarLivro(id, livro, dto.autor(), dto.categoria(), dto.editora());
        return ResponseEntity.ok(livroMapper.toResponse(atualizado));
    }

    @Operation(summary = "Exclui um livro")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro excluído"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable UUID id){
        livroService.excluirLivro(id);
        return ResponseEntity.noContent().build();
    }
}
