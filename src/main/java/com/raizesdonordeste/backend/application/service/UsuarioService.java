package com.raizesdonordeste.backend.application.service;

import com.raizesdonordeste.backend.domain.entity.Cliente;
import com.raizesdonordeste.backend.domain.entity.Usuario;
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
    public void registrarUsuario(String email, String senha) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(passwordEncoder.encode(senha));

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        Cliente novoCliente = Cliente.builder()
                .email(email)
                .saldoPontosFidelidade(0)
                .usuario(usuarioSalvo)
                .build();

        clienteRepository.save(novoCliente);
    }
}
