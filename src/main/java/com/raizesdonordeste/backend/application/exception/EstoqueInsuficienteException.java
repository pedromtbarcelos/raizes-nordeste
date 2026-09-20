package com.raizesdonordeste.backend.application.exception;

public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }
}
