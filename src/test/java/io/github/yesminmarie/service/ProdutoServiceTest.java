package io.github.yesminmarie.service;

import io.github.yesminmarie.domain.entity.Produto;
import io.github.yesminmarie.domain.repository.ProdutosRepository;
import io.github.yesminmarie.service.impl.ProdutoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdutoServiceTest {

    private ProdutoService produtoService;

    @Mock
    private ProdutosRepository produtosRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
        this.produtoService = new ProdutoServiceImpl(produtosRepository);
    }

    @Test
    @DisplayName("Verifica se o método getProdutoById() traz o produto quando é inserido um id válido")
    void verificaSeBuscaProdutoPorIdValido() {
        Produto produto = criaProduto(1, "Mouse", new BigDecimal("40.00"));

        Mockito.when(produtosRepository.findById(1)).thenReturn(Optional.of(produto));

        produtoService.getProdutoById(1);

        assertEquals(1, produto.getId());
        assertEquals("Mouse", produto.getDescricao());
        assertEquals(new BigDecimal("40.00"), produto.getPreco());
    }

    @Test
    @DisplayName("Verifica se o método getProdutoById() lança exceção quando produto não existe")
    void verificaSeLancaExcecaoNaBuscaPorIdQueNaoExiste() {
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> produtoService.getProdutoById(1));
        assertEquals("404 NOT_FOUND \"Produto não encontrado\"", exception.getMessage());
    }

    @Test
    @DisplayName("Verifica se o produto é salvo passando todos os dados corretamente")
    void verificaSeSalvaProdutoPassandoTodosOsDadosCorretmente() {
        Produto produto = criaProduto(1, "Mouse", new BigDecimal("40.00"));
        produtoService.salvar(produto);
        Mockito.verify(produtosRepository).save(produto);
    }

    @Test
    @DisplayName("Verifica se o produto é deletado caso o id passado esteja correto")
    void verificaSeProdutoDeletadoCasoIdCorreto() {
        Produto produto = criaProduto(1, "Mouse", new BigDecimal("40.00"));

        Mockito.when(produtosRepository.findById(1)).thenReturn(Optional.of(produto));

        produtoService.delete(1);

        Mockito.verify(produtosRepository).delete(produto);
    }

    @Test
    @DisplayName("Verifica se é lançada exceção caso o id do produto a ser deletado esteja incorreto")
    void verificaSeELancadaExcecaoCasoIdProdutoASerDeletadoEstejaIncorreto() {
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> produtoService.delete(1));
        assertEquals("404 NOT_FOUND \"Produto não encontrado\"", exception.getMessage());
    }

    @Test
    @DisplayName("Verifica se o produto é atualizado caso o id passado esteja correto")
    void verificaSeProdutoAtualizadoCasoIdCorreto() {
        Produto produto = criaProduto(1, "Mouse", new BigDecimal("40.00"));
        Produto produto2 = criaProduto(1, "Mouse", new BigDecimal("25.00"));

        Mockito.when(produtosRepository.findById(1)).thenReturn(Optional.of(produto));

        produtoService.update(1, produto2);

        Mockito.verify(produtosRepository).save(produto2);
    }

    @Test
    @DisplayName("Verifica se é lançada exceção caso o id do produto a ser atualizado esteja incorreto")
    void verificaSeELancadaExcecaoCasoIdProdutoASerAtualizadoEstejaIncorreto() {
        Produto produtoAtualizado = criaProduto(1, "Mouse", new BigDecimal("25.00"));

        Exception exception = assertThrows(ResponseStatusException.class,
                () -> produtoService.update(2, produtoAtualizado));
        assertEquals("404 NOT_FOUND \"Produto não encontrado\"", exception.getMessage());
    }

    @Test
    void verificaMetodofind() {
        Produto produto = criaProduto(1, "Mouse", new BigDecimal("40.00"));

        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);

        Mockito.when(produtosRepository.findAll()).thenReturn(produtos);

        produtoService.find(produto);

        assertEquals(1, produtos.get(0).getId());
        assertEquals("Mouse", produtos.get(0).getDescricao());
        assertEquals(new BigDecimal("40.00"), produtos.get(0).getPreco());
    }


    private Produto criaProduto(Integer id, String descricao, BigDecimal preco){
        Produto produto = new Produto();
        produto.setId(id);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        return produto;
    }
}