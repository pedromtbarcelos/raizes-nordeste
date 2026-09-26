package com.raizesdonordeste.backend.api.controller;

import com.raizesdonordeste.backend.api.dto.request.AtualizarEstoqueRequest;
import com.raizesdonordeste.backend.application.exception.RecursoNaoEncontradoException;
import com.raizesdonordeste.backend.domain.entity.Estoque;
import com.raizesdonordeste.backend.infrastructure.repository.EstoqueRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
