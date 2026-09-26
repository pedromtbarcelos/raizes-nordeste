package com.raizesdonordeste.backend.infrastructure.repository;

import com.raizesdonordeste.backend.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
