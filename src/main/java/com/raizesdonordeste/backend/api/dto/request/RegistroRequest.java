package com.raizesdonordeste.backend.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RegistroRequest(
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        String senha,

        String role,

        @NotNull(message = "O consentimento LGPD é obrigatório.")
        Boolean consentimentoLgpd
) {
}
