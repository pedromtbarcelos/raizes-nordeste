package com.raizesdonordeste.backend.application.service;

import com.raizesdonordeste.backend.application.exception.EstoqueInsuficienteException;
import com.raizesdonordeste.backend.application.exception.RecursoNaoEncontradoException;
import com.raizesdonordeste.backend.domain.*;
import com.raizesdonordeste.backend.domain.entity.*;
import com.raizesdonordeste.backend.domain.enums.StatusPedido;
import com.raizesdonordeste.backend.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;
    private final ClienteRepository clienteRepository;
    private final PagamentoService pagamentoService;

    @Transactional
    public Pedido criarPedido(Pedido pedidoSolicitado) {
        if (pedidoSolicitado.getIdempotencyKey() != null) {
            Optional<Pedido> pedidoExistente = pedidoRepository.findByIdempotencyKey(pedidoSolicitado.getIdempotencyKey());
            if (pedidoExistente.isPresent()) {
                return pedidoExistente.get();
            }
        }

        Unidade unidade = unidadeRepository.findById(pedidoSolicitado.getUnidade().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada."));

        Cliente cliente = clienteRepository.findById(pedidoSolicitado.getCliente().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));

        pedidoSolicitado.setUnidade(unidade);
        pedidoSolicitado.setCliente(cliente);

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemPedido item : pedidoSolicitado.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado."));

            Estoque estoque = estoqueRepository.findByUnidadeIdAndProdutoId(unidade.getId(), produto.getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque não configurado para o produto " + produto.getNome() + " nesta unidade."));

            if (estoque.getQuantidadeSaldo() < item.getQuantidade()) {
                throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            estoque.setQuantidadeSaldo(estoque.getQuantidadeSaldo() - item.getQuantidade());
            estoqueRepository.save(estoque);

            item.setProduto(produto);
            item.setPrecoUnitarioHistorico(produto.getPreco());
            item.setPedido(pedidoSolicitado);

            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
            valorTotal = valorTotal.add(subtotal);
        }

        pedidoSolicitado.setValorTotal(valorTotal);
        pedidoSolicitado.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedidoSolicitado.setDataPedido(LocalDateTime.now());

        Pedido pedidoSalvo = pedidoRepository.save(pedidoSolicitado);

        boolean pagamentoAprovado = pagamentoService.processarPagamento(pedidoSalvo);

        if (pagamentoAprovado) {
            pedidoSalvo.setStatusPedido(StatusPedido.EM_PREPARO);
        } else {
            pedidoSalvo.setStatusPedido(StatusPedido.PAGAMENTO_RECUSADO);
            estornarEstoque(pedidoSalvo);
        }

        return pedidoRepository.save(pedidoSalvo);
    }

    private void estornarEstoque(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            Estoque estoque = estoqueRepository.findByUnidadeIdAndProdutoId(pedido.getUnidade().getId(), item.getProduto().getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Erro ao localizar estoque para estorno."));

            estoque.setQuantidadeSaldo(estoque.getQuantidadeSaldo() + item.getQuantidade());
            estoqueRepository.save(estoque);
        }
    }
}
