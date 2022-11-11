package io.github.yesminmarie.service.impl;

import io.github.yesminmarie.domain.repository.Pedidos;
import io.github.yesminmarie.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    private Pedidos repository;

    public PedidoServiceImpl(Pedidos repository) {
        this.repository = repository;
    }
}
