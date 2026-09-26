package com.raizesdonordeste.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    private String nome;
    private String email;
    private String telefone;

    @Column(name = "saldo_pontos_fidelidade")
    private Integer saldoPontosFidelidade;

    @Column(name = "consentimento_lgpd")
    @Builder.Default
    private Boolean consentimentoLgpd = false;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "pontos_fidelidade")
    @Builder.Default
    private Integer pontosFidelidade = 0;
}
