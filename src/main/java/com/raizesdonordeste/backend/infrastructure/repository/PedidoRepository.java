package com.raizesdonordeste.backend.infrastructure.repository;

import com.raizesdonordeste.backend.domain.entity.Pedido;
import com.raizesdonordeste.backend.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByIdempotencyKey(String idempotencyKey);
}
