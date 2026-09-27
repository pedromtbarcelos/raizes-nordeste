package com.raizesdonordeste.backend.api.controller;

import com.raizesdonordeste.backend.api.dto.request.AtualizarEstoqueRequest;
import com.raizesdonordeste.backend.api.dto.request.CriarEstoqueRequest;
import com.raizesdonordeste.backend.application.exception.RecursoNaoEncontradoException;
import com.raizesdonordeste.backend.domain.entity.Estoque;
import com.raizesdonordeste.backend.domain.entity.Produto;
import com.raizesdonordeste.backend.domain.entity.Unidade;
import com.raizesdonordeste.backend.infrastructure.repository.EstoqueRepository;
import com.raizesdonordeste.backend.infrastructure.repository.ProdutoRepository;
import com.raizesdonordeste.backend.infrastructure.repository.UnidadeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/estoque")
@RequiredArgsConstructor
@Tag(name = "Estoque", description = "Gestão e controle de estoque por unidade")
@SecurityRequirement(name = "bearerAuth")
public class EstoqueController {

    private final EstoqueRepository estoqueRepository;
    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;

    @PostMapping
    @Operation(summary = "Adiciona um novo produto ao estoque de uma unidade (Restrito para GERENTE ou ADMIN)")
    @PreAuthorize("hasAnyRole('GERENTE', 'ADMIN')")
    public ResponseEntity criarEstoque(@RequestBody @Valid CriarEstoqueRequest request) {

        Unidade unidade = unidadeRepository.findById(request.idUnidade())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada."));

        Produto produto = produtoRepository.findById(request.idProduto())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado."));

        if (estoqueRepository.findByUnidadeIdAndProdutoId(unidade.getId(), produto.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um registro de estoque para este produto nesta unidade. Use a rota de atualização.");
        }

        Estoque novoEstoque = Estoque.builder()
                .unidade(unidade)
                .produto(produto)
                .quantidadeSaldo(request.quantidadeSaldo())
                .build();

        Estoque estoqueSalvo = estoqueRepository.save(novoEstoque);
        return ResponseEntity.status(HttpStatus.CREATED).body(estoqueSalvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza a quantidade de saldo em estoque (Restrito para GERENTE ou ADMIN)")
    @PreAuthorize("hasAnyRole('GERENTE', 'ADMIN')")
    public ResponseEntity atualizarEstoque(
            @PathVariable("id") Long idEstoque,
            @RequestBody @Valid AtualizarEstoqueRequest request
    ) {
        Estoque estoque = estoqueRepository.findById(idEstoque)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro de estoque não encontrado."));

        estoque.setQuantidadeSaldo(request.quantidadeSaldo());
        Estoque estoqueAtualizado = estoqueRepository.save(estoque);

        return ResponseEntity.ok(estoqueAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um registro de estoque (Restrito para GERENTE ou ADMIN)")
    @PreAuthorize("hasAnyRole('GERENTE', 'ADMIN')")
    public ResponseEntity removerEstoque(@PathVariable("id") Long idEstoque) {
        Estoque estoque = estoqueRepository.findById(idEstoque)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro de estoque não encontrado."));

        estoqueRepository.delete(estoque);
        return ResponseEntity.noContent().build();
    }
}