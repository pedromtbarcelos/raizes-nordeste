package com.raizesdonordeste.backend.domain.entity;

import com.raizesdonordeste.backend.domain.enums.CanalPedido;
import com.raizesdonordeste.backend.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_unidade")
    private Unidade unidade;
    @Enumerated(EnumType.STRING)
    @Column(name = "canal_pedido", nullable = false)
    private CanalPedido canalPedido;
    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;
    @Column(name = "valor_total")
    private BigDecimal valorTotal;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido")
    private StatusPedido statusPedido;
    @Column(name = "forma_pagamento")
    private String formaPagamento;
    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ItemPedido> itens = new ArrayList<>();

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        item.setPedido(this);
    }
}
