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

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }
    public Livro criarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public List<Livro> listarLivros(){
        return livroRepository.findAll();
    }

    public Livro buscarLivro(UUID id){
        return livroRepository.findById(id).orElseThrow(() -> new LivroNaoEncontradoException(id));
    }

    public Livro atualizarLivro(UUID id, Livro livro){
        Livro existente = livroRepository.findById(id).orElseThrow(() -> new LivroNaoEncontradoException(id));

        existente.setTitulo(livro.getTitulo());
        existente.setAutor(livro.getAutor());
        existente.setIsbn(livro.getIsbn());
        existente.setAnoPublicacao(livro.getAnoPublicacao());
        existente.setPreco(livro.getPreco());

        return livroRepository.save(existente);
    }

    public void excluirLivro(UUID id){
        livroRepository.deleteById(id);
    }
}
