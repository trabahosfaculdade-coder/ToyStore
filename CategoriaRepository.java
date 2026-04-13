package br.com.Toystore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.Toystore.model.Categoria;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // 🔍 Buscar por nome (exato)
    Categoria findByNome(String nome);

    // 🔍 Buscar por nome contendo (tipo filtro)
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
}