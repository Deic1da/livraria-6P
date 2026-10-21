package br.com.melqui.livraria.controller;

import br.com.melqui.livraria.dto.request.AutorRequestDto;
import br.com.melqui.livraria.dto.response.AutorResponseDto;
import br.com.melqui.livraria.mapper.AutorMapper;
import br.com.melqui.livraria.model.Autor;
import br.com.melqui.livraria.service.AutorService;
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

@Tag(name = "Autores", description = "Cadastro de autores")
@RestController
@RequestMapping("/api/v1/autores")
public class AutorController {
    private final AutorService autorService;
    private final AutorMapper autorMapper;

    public AutorController(AutorService autorService, AutorMapper autorMapper) {
        this.autorService = autorService;
        this.autorMapper = autorMapper;
    }

    @Operation(summary = "Cadastra um autor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido")
    })
    @PostMapping
    public ResponseEntity<AutorResponseDto> cadastrarAutor(@RequestBody @Valid AutorRequestDto dto) {
        Autor autor = autorMapper.toEntity(dto);
        Autor autorCriado = autorService.criarAutor(autor);
        URI location = URI.create("/api/v1/autores/" + autorCriado.getId());
        return ResponseEntity.created(location).body(autorMapper.toResponse(autorCriado));
    }

    @Operation(summary = "Lista todos os autores")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido")
    })
    @GetMapping
    public ResponseEntity<List<AutorResponseDto>> listarAutores(){
        List<Autor> autores = autorService.listarAutores();
        return ResponseEntity.ok(autorMapper.toResponseList(autores));
    }

    @Operation(summary = "Busca um autor pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDto> buscarAutor(@PathVariable UUID id){
        Autor autor = autorService.buscarAutor(id);
        return ResponseEntity.ok(autorMapper.toResponse(autor));
    }

    @Operation(summary = "Atualiza um autor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDto> atualizarAutor(@PathVariable UUID id, @RequestBody @Valid AutorRequestDto dto){
        Autor autor = autorMapper.toEntity(dto);
        Autor atualizado = autorService.atualizarAutor(id, autor);
        return ResponseEntity.ok(autorMapper.toResponse(atualizado));
    }

    @Operation(summary = "Exclui um autor")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor excluído"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "409", description = "Existem livros vinculados a este autor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable UUID id){
        autorService.excluirAutor(id);
        return ResponseEntity.noContent().build();
    }
}
