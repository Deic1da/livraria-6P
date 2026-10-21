package br.com.melqui.livraria.service;

import br.com.melqui.livraria.exception.AutorNaoEncontradoException;
import br.com.melqui.livraria.model.Autor;
import br.com.melqui.livraria.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor criarAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    public List<Autor> listarAutores(){
        return autorRepository.findAll();
    }

    public Autor buscarAutor(UUID id){
        return autorRepository.findById(id).orElseThrow(() -> new AutorNaoEncontradoException(id));
    }

    public Autor atualizarAutor(UUID id, Autor autor){
        Autor existente = buscarAutor(id);

        existente.setNome(autor.getNome());
        existente.setNacionalidade(autor.getNacionalidade());

        return autorRepository.save(existente);
    }

    public void excluirAutor(UUID id){
        Autor existente = buscarAutor(id);
        autorRepository.delete(existente);
    }
}
