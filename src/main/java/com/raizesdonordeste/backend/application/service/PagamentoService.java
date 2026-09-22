package com.raizesdonordeste.backend.application.service;

import com.raizesdonordeste.backend.domain.entity.Pedido;
import org.springframework.stereotype.Service;


@Service
public class PagamentoService {
    public boolean processarPagamento(Pedido pedido) {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return Math.random() < 0.9;
    }
}
