package com.raizesdonordeste.backend.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AtualizarEstoqueRequest(
        @NotNull(message = "A quantidade de saldo é obrigatória.")
        @Min(value = 0, message = "A quantidade de saldo não pode ser negativa.")
        Integer quantidadeSaldo
) {}
