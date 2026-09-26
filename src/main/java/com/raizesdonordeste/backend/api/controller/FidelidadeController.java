package com.raizesdonordeste.backend.api.controller;

import com.raizesdonordeste.backend.domain.entity.Cliente;
import com.raizesdonordeste.backend.domain.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/fidelidade")
@RequiredArgsConstructor
@Tag(name = "Fidelidade", description = "Endpoints do programa de fidelidade do cliente")
@SecurityRequirement(name = "bearerAuth")
public class FidelidadeController {

    @GetMapping("/saldo")
    @Operation(summary = "Consulta o saldo de pontos do cliente autenticado")
    public ResponseEntity consultarSaldo(@AuthenticationPrincipal Usuario usuario) {
        if (usuario == null || usuario.getCliente() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Cliente não encontrado para o usuário logado."));
        }

        Cliente cliente = usuario.getCliente();

        if (Boolean.FALSE.equals(cliente.getConsentimentoLgpd())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "O cliente ainda não forneceu consentimento LGPD para o programa de fidelidade."));
        }

        return ResponseEntity.ok(Map.of(
                "idCliente", cliente.getId(),
                "pontosFidelidade", cliente.getPontosFidelidade() != null ? cliente.getPontosFidelidade() : 0,
                "consentimentoLgpd", cliente.getConsentimentoLgpd()
        ));
    }
}
