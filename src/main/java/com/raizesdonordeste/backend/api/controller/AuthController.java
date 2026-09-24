package com.raizesdonordeste.backend.api.controller;

import com.raizesdonordeste.backend.api.dto.request.AuthRequest;
import com.raizesdonordeste.backend.application.service.UsuarioService;
import com.raizesdonordeste.backend.domain.entity.Usuario;
import com.raizesdonordeste.backend.infrastructure.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para cadastro, login e geração de tokens JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    @PostMapping("/registrar")
    @Operation(summary = "Cadastra um novo usuário no banco de dados")
    public ResponseEntity<Map<String, String>> registrar(@RequestBody @Valid AuthRequest request) {
        usuarioService.registrarUsuario(request.email(), request.senha());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Usuário cadastrado com sucesso!"));
    }

    @PostMapping("/login")
    @Operation(summary = "Realiza login e gera o token JWT para usuários cadastrados")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid AuthRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
        var authentication = authenticationManager.authenticate(authToken);

        var token = tokenService.gerarToken(((Usuario) authentication.getPrincipal()).getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
