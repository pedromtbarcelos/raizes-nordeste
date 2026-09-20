package com.raizesdonordeste.backend.infrastructure.repository;

import com.raizesdonordeste.backend.domain.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
}
