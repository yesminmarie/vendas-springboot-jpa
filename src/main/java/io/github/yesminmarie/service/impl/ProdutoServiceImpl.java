package io.github.yesminmarie.service.impl;

import io.github.yesminmarie.domain.entity.Produto;
import io.github.yesminmarie.domain.repository.ProdutosRepository;
import io.github.yesminmarie.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutosRepository produtosRepository;

    @Override
    public Produto getProdutoById(Integer id) {
        return produtosRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
    }

    @Override
    public Produto salvar(Produto produto) {
        return produtosRepository.save(produto);
    }

    @Override
    public void delete(Integer id) {
        produtosRepository
                .findById(id)
                .map(produto -> {
                    produtosRepository.delete(produto);
                    return produto;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
    }

    @Override
    public void update(Integer id, Produto produto) {
        produtosRepository
                .findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    produtosRepository.save(produto);
                    return produto;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
    }

    @Override
    public List<Produto> find(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return produtosRepository.findAll(example);
    }
}
