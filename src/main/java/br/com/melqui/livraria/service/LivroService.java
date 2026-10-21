package br.com.melqui.livraria.service;

import br.com.melqui.livraria.exception.LivroNaoEncontradoException;
import br.com.melqui.livraria.model.Livro;
import br.com.melqui.livraria.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LivroService {
    private final LivroRepository livroRepository;
    private final AutorService autorService;
    private final CategoriaService categoriaService;
    private final EditoraService editoraService;

    public LivroService(LivroRepository livroRepository, AutorService autorService, CategoriaService categoriaService, EditoraService editoraService) {
        this.livroRepository = livroRepository;
        this.autorService = autorService;
        this.categoriaService = categoriaService;
        this.editoraService = editoraService;
    }
    public Livro criarLivro(Livro livro, UUID autorId, UUID categoriaId, UUID editoraId){
        livro.setAutor(autorService.buscarAutor(autorId));
        livro.setCategoria(categoriaService.buscarCategoria(categoriaId));
        livro.setEditora(editoraService.buscarEditora(editoraId));

        return livroRepository.save(livro);
    }

    public List<Livro> listarLivros(){
        return livroRepository.findAll();
    }

    public Livro buscarLivro(UUID id){
        return livroRepository.findById(id).orElseThrow(() -> new LivroNaoEncontradoException(id));
    }

    public Livro atualizarLivro(UUID id, Livro livro, UUID autorId, UUID categoriaId, UUID editoraId){
        Livro existente = buscarLivro(id);

        existente.setTitulo(livro.getTitulo());
        existente.setAutor(autorService.buscarAutor(autorId));
        existente.setCategoria(categoriaService.buscarCategoria(categoriaId));
        existente.setEditora(editoraService.buscarEditora(editoraId));
        existente.setIsbn(livro.getIsbn());
        existente.setAnoPublicacao(livro.getAnoPublicacao());
        existente.setPreco(livro.getPreco());

        return livroRepository.save(existente);
    }

    public void excluirLivro(UUID id){
        Livro existente = buscarLivro(id);
        livroRepository.delete(existente);
    }
}
