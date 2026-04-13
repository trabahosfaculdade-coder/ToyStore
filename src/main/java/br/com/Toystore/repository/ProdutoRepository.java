package br.com.Toystore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.Toystore.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByDestaqueTrue();
    List<Produto> findByIdIn(List<Long> ids);

}