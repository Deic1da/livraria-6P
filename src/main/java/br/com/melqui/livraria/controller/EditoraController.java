package br.com.melqui.livraria.controller;

import br.com.melqui.livraria.dto.request.EditoraRequestDto;
import br.com.melqui.livraria.dto.response.EditoraResponseDto;
import br.com.melqui.livraria.mapper.EditoraMapper;
import br.com.melqui.livraria.model.Editora;
import br.com.melqui.livraria.service.EditoraService;
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

@Tag(name = "Editoras", description = "Cadastro de editoras")
@RestController
@RequestMapping("/api/v1/editoras")
public class EditoraController {
    private final EditoraService editoraService;
    private final EditoraMapper editoraMapper;

    public EditoraController(EditoraService editoraService, EditoraMapper editoraMapper) {
        this.editoraService = editoraService;
        this.editoraMapper = editoraMapper;
    }

    @Operation(summary = "Cadastra uma editora")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Editora cadastrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido")
    })
    @PostMapping
    public ResponseEntity<EditoraResponseDto> cadastrarEditora(@RequestBody @Valid EditoraRequestDto dto) {
        Editora editora = editoraMapper.toEntity(dto);
        Editora editoraCriada = editoraService.criarEditora(editora);
        URI location = URI.create("/api/v1/editoras/" + editoraCriada.getId());
        return ResponseEntity.created(location).body(editoraMapper.toResponse(editoraCriada));
    }

    @Operation(summary = "Lista todas as editoras")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido")
    })
    @GetMapping
    public ResponseEntity<List<EditoraResponseDto>> listarEditoras(){
        List<Editora> editoras = editoraService.listarEditoras();
        return ResponseEntity.ok(editoraMapper.toResponseList(editoras));
    }

    @Operation(summary = "Busca uma editora pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Editora encontrada"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EditoraResponseDto> buscarEditora(@PathVariable UUID id){
        Editora editora = editoraService.buscarEditora(id);
        return ResponseEntity.ok(editoraMapper.toResponse(editora));
    }

    @Operation(summary = "Atualiza uma editora")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Editora atualizada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EditoraResponseDto> atualizarEditora(@PathVariable UUID id, @RequestBody @Valid EditoraRequestDto dto){
        Editora editora = editoraMapper.toEntity(dto);
        Editora atualizada = editoraService.atualizarEditora(id, editora);
        return ResponseEntity.ok(editoraMapper.toResponse(atualizada));
    }

    @Operation(summary = "Exclui uma editora")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Editora excluída"),
            @ApiResponse(responseCode = "401", description = "Sem token ou token inválido"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada"),
            @ApiResponse(responseCode = "409", description = "Existem livros vinculados a esta editora")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEditora(@PathVariable UUID id){
        editoraService.excluirEditora(id);
        return ResponseEntity.noContent().build();
    }
}
