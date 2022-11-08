package io.github.yesminmarie.domain.repository;

import io.github.yesminmarie.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer> {
}
