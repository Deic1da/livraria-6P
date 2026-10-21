package br.com.melqui.livraria.service;

import br.com.melqui.livraria.exception.EditoraNaoEncontradaException;
import br.com.melqui.livraria.model.Editora;
import br.com.melqui.livraria.repository.EditoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EditoraService {
    private final EditoraRepository editoraRepository;

    public EditoraService(EditoraRepository editoraRepository) {
        this.editoraRepository = editoraRepository;
    }

    public Editora criarEditora(Editora editora) {
        return editoraRepository.save(editora);
    }

    public List<Editora> listarEditoras(){
        return editoraRepository.findAll();
    }

    public Editora buscarEditora(UUID id){
        return editoraRepository.findById(id).orElseThrow(() -> new EditoraNaoEncontradaException(id));
    }

    public Editora atualizarEditora(UUID id, Editora editora){
        Editora existente = buscarEditora(id);

        existente.setNome(editora.getNome());
        existente.setCidade(editora.getCidade());

        return editoraRepository.save(existente);
    }

    public void excluirEditora(UUID id){
        Editora existente = buscarEditora(id);
        editoraRepository.delete(existente);
    }
}
