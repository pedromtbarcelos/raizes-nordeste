package com.raizesdonordeste.backend.api.controller;

import com.raizesdonordeste.backend.api.dto.request.AtualizarProdutoRequest;
import com.raizesdonordeste.backend.api.dto.request.CriarProdutoRequest;
import com.raizesdonordeste.backend.application.exception.RecursoNaoEncontradoException;
import com.raizesdonordeste.backend.domain.entity.Produto;
import com.raizesdonordeste.backend.infrastructure.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Gestão do catálogo de produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    @PostMapping
    @Operation(summary = "Criar um novo produto (Restrito para GERENTE ou ADMIN)")
    @SecurityRequirement(name = "bearerAuth") // Exibe o cadeado no Swagger para esta rota protegida
    @PreAuthorize("hasAnyRole('GERENTE', 'ADMIN')")
    public ResponseEntity criarProduto(@RequestBody @Valid CriarProdutoRequest request) {
        Produto produto = Produto.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .preco(BigDecimal.valueOf(request.preco()))
                .categoria(request.categoria())
                .build();

        Produto produtoSalvo = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @GetMapping
    @Operation(summary = "Listar produtos com paginação (Acesso Público)")
    public ResponseEntity listarProdutos(Pageable pageable) {
        Page produtos = produtoRepository.findAll(pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar produto por ID (Acesso Público)")
    public ResponseEntity buscarProdutoPorId(@PathVariable("id") Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado."));
        return ResponseEntity.ok(produto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um produto existente (Restrito para GERENTE ou ADMIN)")
    @SecurityRequirement(name = "bearerAuth") // Exibe o cadeado no Swagger para esta rota protegida
    @PreAuthorize("hasAnyRole('GERENTE', 'ADMIN')")
    public ResponseEntity atualizarProduto(
            @PathVariable("id") Long id,
            @RequestBody @Valid AtualizarProdutoRequest request) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado."));

        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setPreco(BigDecimal.valueOf(request.preco()));
        produto.setCategoria(request.categoria());

        Produto produtoAtualizado = produtoRepository.save(produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um produto do catálogo (Restrito para GERENTE ou ADMIN)")
    @SecurityRequirement(name = "bearerAuth") // Exibe o cadeado no Swagger para esta rota protegida
    @PreAuthorize("hasAnyRole('GERENTE', 'ADMIN')")
    public ResponseEntity excluirProduto(@PathVariable("id") Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado."));

        produtoRepository.delete(produto);
        return ResponseEntity.noContent().build();
    }
}
