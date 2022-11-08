package io.github.yesminmarie.domain.repository;

import io.github.yesminmarie.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
