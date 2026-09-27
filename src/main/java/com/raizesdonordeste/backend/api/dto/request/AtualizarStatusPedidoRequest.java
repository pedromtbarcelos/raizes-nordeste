package com.raizesdonordeste.backend.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AtualizarStatusPedidoRequest(
        @NotBlank(message = "O novo status do pedido é obrigatório")
        String status
) {}
