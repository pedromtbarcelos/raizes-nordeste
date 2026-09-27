package com.raizesdonordeste.backend.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CriarEstoqueRequest(
        @NotNull(message = "O ID da unidade é obrigatório")
        Long idUnidade,

        @NotNull(message = "O ID do produto é obrigatório")
        Long idProduto,

        @NotNull(message = "A quantidade de saldo é obrigatória")
        @Min(value = 0, message = "O saldo não pode ser negativo")
        Integer quantidadeSaldo
) {}
