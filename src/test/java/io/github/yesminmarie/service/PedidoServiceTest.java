package io.github.yesminmarie.service;

import io.github.yesminmarie.domain.entity.Cliente;
import io.github.yesminmarie.domain.entity.ItemPedido;
import io.github.yesminmarie.domain.entity.Pedido;
import io.github.yesminmarie.domain.entity.Produto;
import io.github.yesminmarie.domain.enums.StatusPedido;
import io.github.yesminmarie.domain.repository.ClientesRepository;
import io.github.yesminmarie.domain.repository.ItensPedidoRepository;
import io.github.yesminmarie.domain.repository.PedidosRepository;
import io.github.yesminmarie.domain.repository.ProdutosRepository;
import io.github.yesminmarie.exception.PedidoNaoEncontradoException;
import io.github.yesminmarie.exception.RegraNegocioException;
import io.github.yesminmarie.rest.dto.ItemPedidoDTO;
import io.github.yesminmarie.rest.dto.PedidoDTO;
import io.github.yesminmarie.service.impl.PedidoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PedidoServiceTest {

    private PedidoService pedidoService;

    @Mock
    private PedidosRepository pedidosRepository;

    @Mock
    private ClientesRepository clientesRepository;

    @Mock
    private ProdutosRepository produtosRepository;

    @Mock
    private ItensPedidoRepository itensPedidoRepository;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
        this.pedidoService = new PedidoServiceImpl(
                pedidosRepository,
                clientesRepository,
                produtosRepository,
                itensPedidoRepository);
    }

    @Test
    @DisplayName("Verifica se o pedido é salvo passando todos os dados corretamente")
    void verificaSeSalvaPedidoPassandoTodosOsDadosCorretmente() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");
        Produto produto = criaProduto(2, "Mouse", new BigDecimal("10.00"));

        PedidoDTO pedidoDTO = criaPedidoDTO(cliente, new BigDecimal("50.00"), 2, 5);
        Pedido pedido = criaPedido(cliente, produto, new BigDecimal("50.00"), 5);

        Mockito.when(clientesRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        Mockito.when(produtosRepository.findById(produto.getId())).thenReturn(Optional.of(produto));

        pedidoService.salvar(pedidoDTO);

        assertEquals(1, pedido.getId());
        assertEquals("Maria", pedido.getCliente().getNome());
        assertEquals(new BigDecimal("50.00"), pedido.getTotal());
        assertEquals(LocalDate.now(), pedido.getDataPedido());
        assertEquals(StatusPedido.REALIZADO, pedido.getStatus());
    }

    @Test
    @DisplayName("Verifica se lança exceção caso id do cliente esteja incorreto")
    void verificaSeLancaExcecaoCasoIdClienteIncorreto() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");
        PedidoDTO pedidoDTO = criaPedidoDTO(cliente, new BigDecimal("50.00"), 2, 5);

        Exception exception = assertThrows(RegraNegocioException.class,
                () -> pedidoService.salvar(pedidoDTO));
        assertEquals("Código de cliente inválido.", exception.getMessage());
    }

    @Test
    @DisplayName("Verifica se lança exceção caso id do produto esteja incorreto")
    void verificaSeLancaExcecaoCasoIdProdutoIncorreto() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");

        PedidoDTO pedidoDTO = criaPedidoDTO(cliente, new BigDecimal("50.00"), 2, 5);

        Mockito.when(clientesRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        Exception exception = assertThrows(RegraNegocioException.class,
                () -> pedidoService.salvar(pedidoDTO));
        assertEquals("Código de produto inválido: 2", exception.getMessage());
    }

    @Test
    @DisplayName("Verifica se lança exceção caso pedido não tenha itens")
    void verificaSeLancaExcecaoCasoPedidoNaoTenhaItens() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");
        Produto produto = criaProduto(2, "Mouse", new BigDecimal("10.00"));

        PedidoDTO pedidoSemItens = criaPedidoDTOSemItens(cliente, new BigDecimal("50.00"));

        Mockito.when(clientesRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        Mockito.when(produtosRepository.findById(produto.getId())).thenReturn(Optional.of(produto));

        Exception exception = assertThrows(RegraNegocioException.class,
                () -> pedidoService.salvar(pedidoSemItens));
        assertEquals("Não é possível realizar um pedido sem itens.", exception.getMessage());
    }

    @Test
    @DisplayName("Verifica o pedido completo é obtido")
    void verificaSeObtemPedidoCompleto() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");
        Produto produto = criaProduto(2, "Mouse", new BigDecimal("10.00"));

        PedidoDTO pedidoDTO = criaPedidoDTO(cliente, new BigDecimal("50.00"), 2, 5);
        Pedido pedido = criaPedido(cliente, produto, new BigDecimal("50.00"), 5);

        Mockito.when(clientesRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        Mockito.when(produtosRepository.findById(produto.getId())).thenReturn(Optional.of(produto));
        Mockito.when(pedidosRepository.findByIdFetchItens(pedido.getId())).thenReturn(Optional.of(pedido));

        pedidoService.obterPedidoCompleto(pedido.getId());

        assertEquals(1, pedido.getId());
        assertEquals("Maria", pedido.getCliente().getNome());
        assertEquals(new BigDecimal("50.00"), pedido.getTotal());
        assertEquals(LocalDate.now(), pedido.getDataPedido());
        assertEquals(StatusPedido.REALIZADO, pedido.getStatus());
    }

    @Test
    @DisplayName("Verifica se atualiza status do pedido caso id do pedido seja passado corretamente")
    void verificaSeAtualizaStatusCasoIdPedidoSejaPassadoCorretamente() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");
        Produto produto = criaProduto(2, "Mouse", new BigDecimal("10.00"));

        Pedido pedido = criaPedido(cliente, produto, new BigDecimal("50.00"), 5);

        Mockito.when(pedidosRepository.findById(pedido.getId())).thenReturn(Optional.of(pedido));
        Mockito.when(pedidosRepository.save(pedido)).thenReturn(pedido);

        pedidoService.atualizarStatus(pedido.getId(), StatusPedido.CANCELADO);

        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
    }

    @Test
    @DisplayName("Verifica se lança exceção caso id do pedido não exista")
    void verificaSeLancaExcecaoCasoIdPedidoNaoExista() {
        Cliente cliente = criaCliente(1, "Maria", "76328651015");
        Produto produto = criaProduto(2, "Mouse", new BigDecimal("10.00"));

        Pedido pedido = criaPedido(cliente, produto, new BigDecimal("50.00"), 5);

        Exception exception = assertThrows(PedidoNaoEncontradoException.class,
                () -> pedidoService.atualizarStatus(pedido.getId(), StatusPedido.CANCELADO));
        assertEquals("Pedido não encontrado.", exception.getMessage());
    }

    private PedidoDTO criaPedidoDTO(Cliente cliente,
                                 BigDecimal total,
                                 Integer idProduto,
                                 Integer quantidade){

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setCliente(cliente.getId());
        pedidoDTO.setTotal(total);

        ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
        itemPedidoDTO.setProduto(idProduto);
        itemPedidoDTO.setQuantidade(quantidade);

        List<ItemPedidoDTO> listaItensPedidoDTO = new ArrayList<>();
        listaItensPedidoDTO.add(itemPedidoDTO);

        pedidoDTO.setItens(listaItensPedidoDTO);

        return pedidoDTO;
    }

    private Pedido criaPedido(Cliente cliente,
                              Produto produto,
                              BigDecimal total,
                              Integer quantidade){

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setCliente(cliente);
        pedido.setTotal(total);
        pedido.setDataPedido(LocalDate.now());
        pedido.setStatus(StatusPedido.REALIZADO);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(quantidade);

        List<ItemPedido> listaItensPedido = new ArrayList<>();
        listaItensPedido.add(itemPedido);

        pedido.setItens(listaItensPedido);

        return pedido;
    }

    private PedidoDTO criaPedidoDTOSemItens(Cliente cliente,
                                    BigDecimal total){

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setCliente(cliente.getId());
        pedidoDTO.setTotal(total);

        pedidoDTO.setItens(new ArrayList<>());

        return pedidoDTO;
    }

    private Cliente criaCliente(Integer id, String nome, String cpf){
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setCpf(cpf);

        return cliente;
    }

    private Produto criaProduto(Integer id, String descricao, BigDecimal preco){
        Produto produto = new Produto();
        produto.setId(id);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        return produto;
    }
}