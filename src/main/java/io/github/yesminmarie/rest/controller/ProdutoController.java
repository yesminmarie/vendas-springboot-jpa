package io.github.yesminmarie.rest.controller;

import io.github.yesminmarie.domain.entity.Produto;
import io.github.yesminmarie.domain.repository.ProdutosRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutosRepository produtosRepository;

    public ProdutoController(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }


    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado"),
            @ApiResponse(code = 403, message = "Este usuário não tem permissão para buscar produto")
    })
    public Produto getProdutoById(
            @PathVariable
            @ApiParam("Id do produto") Integer id){
        return produtosRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação"),
            @ApiResponse(code = 403, message = "Este usuário não tem permissão para salvar produto")
    })
    public Produto save(@RequestBody @Valid Produto produto){
        return produtosRepository.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Deleta um produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto deletado com sucesso"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado"),
            @ApiResponse(code = 403, message = "Este usuário não tem permissão para deletar produto")
    })
    public void delete(
            @PathVariable
            @ApiParam("Id do produto") Integer id){
        produtosRepository
                .findById(id)
                .map(produto -> {
                    produtosRepository.delete(produto);
                    return produto;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualiza um produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado"),
            @ApiResponse(code = 403, message = "Este usuário não tem permissão para atualizar produto")
    })
    public void update(@RequestBody @Valid Produto produto,
                       @PathVariable @ApiParam("Id do produto") Integer id){
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

    @GetMapping
    @ApiOperation("Faz um filtro por qualquer propriedade")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado"),
            @ApiResponse(code = 403, message = "Este usuário não tem permissão para buscar produto")
    })
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return produtosRepository.findAll(example);
    }
}
