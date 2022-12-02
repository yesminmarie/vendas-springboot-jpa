package io.github.yesminmarie.service;

import io.github.yesminmarie.domain.entity.Produto;

import java.util.List;

public interface ProdutoService {
    Produto getProdutoById(Integer id);
    Produto salvar(Produto produto);
    void delete(Integer id);
    void update(Integer id, Produto produto);
    List<Produto> find(Produto filtro);
}
