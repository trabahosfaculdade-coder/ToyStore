package br.com.Toystore.model;

import jakarta.persistence.*;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    // 🔥 RELACIONAMENTO CORRETO
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private String marca;
    private Double valor;
    private String imagem;

    // 🔥 CORREÇÃO DO ERRO DO BANCO
    @Column(columnDefinition = "TEXT")
    private String descricao;

    private boolean destaque;

    // =========================
    // GETTERS E SETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getMarca() {
        return marca;
    }

    public Double getValor() {
        return valor;
    }

    public String getImagem() {
        return imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }
}