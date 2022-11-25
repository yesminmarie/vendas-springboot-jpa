package io.github.yesminmarie.domain.repository;

import io.github.yesminmarie.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {
}
