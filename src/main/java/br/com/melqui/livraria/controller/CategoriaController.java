package br.com.melqui.livraria.controller;

import br.com.melqui.livraria.dto.request.CategoriaRequestDto;
import br.com.melqui.livraria.dto.response.CategoriaResponseDto;
import br.com.melqui.livraria.mapper.CategoriaMapper;
import br.com.melqui.livraria.model.Categoria;
import br.com.melqui.livraria.service.CategoriaService;
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

@Tag(name = "Categorias", description = "Cadastro de categorias")
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaMapper categoriaMapper) {
        this.categoriaService = categoriaService;
        this.categoriaMapper = categoriaMapper;
    }

    @Operation(summary = "Cadastra uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido")
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDto> cadastrarCategoria(@RequestBody @Valid CategoriaRequestDto dto) {
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria categoriaCriada = categoriaService.criarCategoria(categoria);
        URI location = URI.create("/api/v1/categorias/" + categoriaCriada.getId());
        return ResponseEntity.created(location).body(categoriaMapper.toResponse(categoriaCriada));
    }

    @Operation(summary = "Lista todas as categorias")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listarCategorias(){
        List<Categoria> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categoriaMapper.toResponseList(categorias));
    }

    @Operation(summary = "Busca uma categoria pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> buscarCategoria(@PathVariable UUID id){
        Categoria categoria = categoriaService.buscarCategoria(id);
        return ResponseEntity.ok(categoriaMapper.toResponse(categoria));
    }

    @Operation(summary = "Atualiza uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> atualizarCategoria(@PathVariable UUID id, @RequestBody @Valid CategoriaRequestDto dto){
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria atualizada = categoriaService.atualizarCategoria(id, categoria);
        return ResponseEntity.ok(categoriaMapper.toResponse(atualizada));
    }

    @Operation(summary = "Exclui uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria excluída"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "409", description = "Existem livros vinculados a esta categoria")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable UUID id){
        categoriaService.excluirCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
