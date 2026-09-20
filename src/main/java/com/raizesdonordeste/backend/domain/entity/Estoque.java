package com.raizesdonordeste.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estoque")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_unidade")
    private Unidade unidade;
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;
    @Column(name = "quantidade_saldo")
    private Integer quantidadeSaldo;
}
