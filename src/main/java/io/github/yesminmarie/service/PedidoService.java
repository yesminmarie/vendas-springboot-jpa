package io.github.yesminmarie.service;

import io.github.yesminmarie.domain.entity.Pedido;
import io.github.yesminmarie.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
}
