package br.com.melqui.livraria.repository;

import br.com.melqui.livraria.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, UUID> {

}
