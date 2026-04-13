package br.com.Toystore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.Toystore.model.Produto;
import br.com.Toystore.model.Categoria;
import br.com.Toystore.repository.ProdutoRepository;
import br.com.Toystore.repository.CategoriaRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // =========================
    // LISTAR TODOS
    // =========================
    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    // =========================
    // SALVAR
    // =========================
    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    // =========================
    // EXCLUIR
    // =========================
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    public Produto buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    // =========================
    // PRODUTO DESTAQUE
    // =========================
    public Produto getProdutoDestaque() {
        return repository.findByDestaqueTrue().stream().findFirst().orElse(null);
    }

    // =========================
    // LISTA DE DESTAQUES
    // =========================
    public List<Produto> buscarDestaques() {
        return repository.findByIdIn(List.of(1L, 3L, 5L, 7L, 9L));
    }

    // =========================
    // FILTRO POR CATEGORIA ✅
    // =========================
    public List<Produto> buscarPorCategoria(Long categoriaId) {
        return repository.findByCategoriaId(categoriaId);
    }

    // =========================
    // LISTAR CATEGORIAS ✅
    // =========================
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }
}