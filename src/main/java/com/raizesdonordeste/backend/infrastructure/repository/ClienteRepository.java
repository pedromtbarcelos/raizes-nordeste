package com.raizesdonordeste.backend.infrastructure.repository;

import com.raizesdonordeste.backend.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
