INSERT INTO unidades (id_unidade, nome, descricao) VALUES (1, 'Raízes do Nordeste - Matriz Centro', 'Unidade principal localizada no centro da cidade.') ON CONFLICT DO NOTHING;
INSERT INTO unidades (id_unidade, nome, descricao) VALUES (2, 'Raízes do Nordeste - Shopping Sul', 'Quiosque localizado na praça de alimentação.') ON CONFLICT DO NOTHING;

INSERT INTO produtos (id_produto, nome, descricao, preco, categoria) VALUES (1, 'Carne de Sol com Mandioca', 'Deliciosa carne de sol acebolada acompanhada de mandioca frita.', 45.90, 'Pratos Principais') ON CONFLICT DO NOTHING;
INSERT INTO produtos (id_produto, nome, descricao, preco, categoria) VALUES (2, 'Dado de Tapioca', 'Dadinhos de tapioca com queijo coalho e melaço de cana.', 28.50, 'Entradas') ON CONFLICT DO NOTHING;
INSERT INTO produtos (id_produto, nome, descricao, preco, categoria) VALUES (3, 'Baião de Dois', 'Arroz, feijão de corda, queijo coalho, carne seca e bacon.', 38.00, 'Pratos Principais') ON CONFLICT DO NOTHING;

INSERT INTO estoque (id_estoque, id_unidade, id_produto, quantidade_saldo) VALUES (1, 1, 1, 50) ON CONFLICT DO NOTHING;
INSERT INTO estoque (id_estoque, id_unidade, id_produto, quantidade_saldo) VALUES (2, 1, 2, 30) ON CONFLICT DO NOTHING;
INSERT INTO estoque (id_estoque, id_unidade, id_produto, quantidade_saldo) VALUES (3, 2, 3, 20) ON CONFLICT DO NOTHING;

-- Senha padrão: 123456
INSERT INTO usuarios (id, email, senha, role) VALUES (1, 'admin@raizes.com', '$2a$10$XbXvQW5hD0cK6jW5m6B1b.Jz5lH1O0X3V2bXvQW5hD0cK6jW5m6B1b', 'ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO usuarios (id, email, senha, role) VALUES (2, 'cliente@raizes.com', '$2a$10$XbXvQW5hD0cK6jW5m6B1b.Jz5lH1O0X3V2bXvQW5hD0cK6jW5m6B1b', 'ROLE_CLIENTE') ON CONFLICT DO NOTHING;

INSERT INTO clientes (id_cliente, nome, email, telefone, saldo_pontos_fidelidade, consentimento_lgpd, usuario_id, pontos_fidelidade)
VALUES (1, 'João da Silva', 'cliente@raizes.com', '71999999999', 150, true, 2, 150) ON CONFLICT DO NOTHING;
