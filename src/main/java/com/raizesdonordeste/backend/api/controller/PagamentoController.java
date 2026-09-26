package com.raizesdonordeste.backend.api.controller;

import com.raizesdonordeste.backend.application.exception.RecursoNaoEncontradoException;
import com.raizesdonordeste.backend.domain.entity.Pedido;
import com.raizesdonordeste.backend.domain.enums.StatusPedido;
import com.raizesdonordeste.backend.infrastructure.repository.PedidoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/pagamentos")
@RequiredArgsConstructor
@Tag(name = "Pagamentos Mock", description = "Endpoint de simulação de webhook de gateway de pagamento")
public class PagamentoController {

    private final PedidoRepository pedidoRepository;

    @PostMapping("/mock")
    @Operation(summary = "Simula a confirmação de pagamento por um gateway externo")
    public ResponseEntity<Map<String, String>> processarWebhookPagamento(
            @RequestParam Long idPedido,
            @RequestParam boolean aprovado
    ) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado."));

        if (aprovado) {
            pedido.setStatusPedido(StatusPedido.EM_PREPARO);
            pedidoRepository.save(pedido);
            return ResponseEntity.ok(Map.of("mensagem", "Pagamento aprovado com sucesso. Pedido enviado para a cozinha."));
        } else {
            pedido.setStatusPedido(StatusPedido.PAGAMENTO_RECUSADO);
            pedidoRepository.save(pedido);
            return ResponseEntity.ok(Map.of("mensagem", "Pagamento recusado pelo gateway. Pedido atualizado."));
        }
    }
}
