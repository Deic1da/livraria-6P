package br.com.melqui.livraria.service;

import br.com.melqui.livraria.exception.CategoriaNaoEncontradaException;
import br.com.melqui.livraria.model.Categoria;
import br.com.melqui.livraria.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria criarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

    public Categoria buscarCategoria(UUID id){
        return categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNaoEncontradaException(id));
    }

    public Categoria atualizarCategoria(UUID id, Categoria categoria){
        Categoria existente = buscarCategoria(id);

        existente.setNome(categoria.getNome());
        existente.setDescricao(categoria.getDescricao());

        return categoriaRepository.save(existente);
    }

    public void excluirCategoria(UUID id){
        Categoria existente = buscarCategoria(id);
        categoriaRepository.delete(existente);
    }
}
