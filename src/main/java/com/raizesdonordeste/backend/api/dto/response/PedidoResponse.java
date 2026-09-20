package com.raizesdonordeste.backend.api.dto.response;

import com.raizesdonordeste.backend.domain.enums.CanalPedido;
import com.raizesdonordeste.backend.domain.enums.StatusPedido;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
public class PedidoResponse {
    private Long idPedido;
    private Long idCliente;
    private Long idUnidade;
    private CanalPedido canalPedido;
    private BigDecimal valorTotal;
    private StatusPedido statusPedido;
    private LocalDateTime dataPedido;
}
