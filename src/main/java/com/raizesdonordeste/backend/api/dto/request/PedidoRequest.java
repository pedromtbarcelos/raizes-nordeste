package com.raizesdonordeste.backend.api.dto.request;

import com.raizesdonordeste.backend.domain.enums.CanalPedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


@Data
public class PedidoRequest {
    @NotBlank(message = "Código de identificação do pedido não informado.")
    private String idempotencyKey;

    @NotNull(message = "O ID do cliente é obrigatório")
    private Long idCliente;

    @NotNull(message = "O ID da unidade é obrigatório")
    private Long idUnidade;

    @NotNull(message = "O canal do pedido é obrigatório")
    private CanalPedido canalPedido;

    @Valid
    @NotEmpty(message = "O pedido deve conter pelo menos um item")
    private List<ItemPedidoRequest> itens;

    private String formaPagamento;
}
