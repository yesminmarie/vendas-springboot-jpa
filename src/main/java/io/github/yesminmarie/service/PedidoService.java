package io.github.yesminmarie.service;

import io.github.yesminmarie.domain.entity.Pedido;
import io.github.yesminmarie.domain.enums.StatusPedido;
import io.github.yesminmarie.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizarStatus(Integer id, StatusPedido statusPedido);
}
