package io.github.yesminmarie.service.impl;

import io.github.yesminmarie.domain.entity.Cliente;
import io.github.yesminmarie.domain.entity.ItemPedido;
import io.github.yesminmarie.domain.entity.Pedido;
import io.github.yesminmarie.domain.entity.Produto;
import io.github.yesminmarie.domain.repository.Clientes;
import io.github.yesminmarie.domain.repository.ItensPedido;
import io.github.yesminmarie.domain.repository.Pedidos;
import io.github.yesminmarie.domain.repository.Produtos;
import io.github.yesminmarie.exception.RegraNegocioException;
import io.github.yesminmarie.rest.dto.ItemPedidoDTO;
import io.github.yesminmarie.rest.dto.PedidoDTO;
import io.github.yesminmarie.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos pedidosRepository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedido itensPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
        pedidosRepository.save(pedido);
        itensPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        return pedido;
    }

    // método auxiliar responsável por converter a lista de ItemPedidoDTO em uma lista de ItemPedido
    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        }

        return itens
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: " + idProduto
                                    ));
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
