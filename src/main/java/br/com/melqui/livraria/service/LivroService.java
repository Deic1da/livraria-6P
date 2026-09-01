package br.com.melqui.livraria.service;

import br.com.melqui.livraria.model.Livro;
import br.com.melqui.livraria.repository.LivroRepository;
import org.springframework.stereotype.Service;

@Service
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }
    public Livro criarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

}
