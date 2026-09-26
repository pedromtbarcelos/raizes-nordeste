package com.raizesdonordeste.backend.api.controller;

import com.raizesdonordeste.backend.api.dto.request.PedidoRequest;
import com.raizesdonordeste.backend.api.dto.response.PedidoResponse;
import com.raizesdonordeste.backend.application.service.PedidoService;
import com.raizesdonordeste.backend.domain.entity.*;
import com.raizesdonordeste.backend.domain.enums.StatusPedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para criação e gerenciamento de pedidos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

    private final PedidoService pedidoService;

    @Operation(
            summary = "Criar um novo pedido",
            description = "Processa a criação de um pedido validando estoque, idempotência e pagamento mock."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PedidoResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados do pedido incompletos ou incorretos."),
            @ApiResponse(responseCode = "404", description = "Cliente, unidade ou produto não encontrado."),
            @ApiResponse(responseCode = "409", description = "Estoque insuficiente para a quantidade solicitada.")
    })
    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(@Valid @RequestBody PedidoRequest request) {
        Pedido pedidoSolicitado = mapToEntity(request);
        Pedido pedidoCriado = pedidoService.criarPedido(pedidoSolicitado);
        PedidoResponse response = mapToResponse(pedidoCriado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private Pedido mapToEntity(PedidoRequest request) {
        Pedido pedido = new Pedido();
        pedido.setIdempotencyKey(request.getIdempotencyKey());
        pedido.setCanalPedido(request.getCanalPedido());
        pedido.setFormaPagamento(request.getFormaPagamento());

        Cliente cliente = new Cliente();
        cliente.setId(request.getIdCliente());
        pedido.setCliente(cliente);

        Unidade unidade = new Unidade();
        unidade.setId(request.getIdUnidade());
        pedido.setUnidade(unidade);

        List<ItemPedido> itens = request.getItens().stream().map(itemRequest -> {
            ItemPedido item = new ItemPedido();
            item.setQuantidade(itemRequest.getQuantidade());
            Produto produto = new Produto();
            produto.setId(itemRequest.getIdProduto());
            item.setProduto(produto);
            return item;
        }).collect(Collectors.toList());

        itens.forEach(pedido::adicionarItem);

        return pedido;
    }

    private PedidoResponse mapToResponse(Pedido pedido) {
        return PedidoResponse.builder()
                .idPedido(pedido.getId())
                .idCliente(pedido.getCliente().getId())
                .idUnidade(pedido.getUnidade().getId())
                .canalPedido(pedido.getCanalPedido())
                .valorTotal(pedido.getValorTotal())
                .statusPedido(pedido.getStatusPedido())
                .dataPedido(pedido.getDataPedido())
                .build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualiza o status do pedido (Cozinha/Atendimento)")
    public ResponseEntity atualizarStatus(
            @PathVariable("id") Long idPedido,
            @RequestParam StatusPedido novoStatus
    ) {
        Pedido pedidoAtualizado = pedidoService.atualizarStatus(idPedido, novoStatus);
        return ResponseEntity.ok(mapToResponse(pedidoAtualizado));
    }
}
