package com.raizesdonordeste.backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "unidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Unidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidade")
    private Long id;
    private String nome;
    private String endereco;
    private String cidade;
}
