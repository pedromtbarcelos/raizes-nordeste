package com.raizesdonordeste.backend.api.controller;

import com.raizesdonordeste.backend.api.dto.request.PedidoRequest;
import com.raizesdonordeste.backend.api.dto.response.PedidoResponse;
import com.raizesdonordeste.backend.application.service.PedidoService;
import com.raizesdonordeste.backend.domain.entity.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

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
}
