package io.github.yesminmarie.domain.repository;

import io.github.yesminmarie.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
}
