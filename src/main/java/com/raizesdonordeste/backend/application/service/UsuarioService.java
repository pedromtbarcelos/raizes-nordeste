package com.raizesdonordeste.backend.application.service;

import com.raizesdonordeste.backend.domain.entity.Cliente;
import com.raizesdonordeste.backend.domain.entity.Usuario;
import com.raizesdonordeste.backend.domain.enums.Role;
import com.raizesdonordeste.backend.infrastructure.repository.ClienteRepository;
import com.raizesdonordeste.backend.infrastructure.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registrarUsuario(String email, String senha, String roleStr, Boolean consentimentoLgpd) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(passwordEncoder.encode(senha));

        Role roleEnum = Role.ROLE_CLIENTE;
        if (roleStr != null && !roleStr.isBlank()) {
            try {
                roleEnum = Role.valueOf(roleStr);
            } catch (IllegalArgumentException e) {
                roleEnum = Role.ROLE_CLIENTE;
            }
        }
        novoUsuario.setRole(roleEnum);
        novoUsuario.setConsentimentoLgpd(consentimentoLgpd != null && consentimentoLgpd);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        Cliente novoCliente = Cliente.builder()
                .email(email)
                .saldoPontosFidelidade(0)
                .usuario(usuarioSalvo)
                .consentimentoLgpd(consentimentoLgpd != null && consentimentoLgpd)
                .build();

        clienteRepository.save(novoCliente);
    }
}
