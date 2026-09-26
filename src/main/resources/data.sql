INSERT INTO unidades (id_unidade, nome, descricao)
VALUES (1, 'Raízes do Nordeste - Matriz Centro', 'Unidade principal localizada no centro da cidade.')
    ON CONFLICT DO NOTHING;

INSERT INTO unidades (id_unidade, nome, descricao)
VALUES (2, 'Raízes do Nordeste - Shopping Sul', 'Quiosque localizado na praça de alimentação.')
    ON CONFLICT DO NOTHING;

INSERT INTO produtos (id_produto, nome, descricao, preco, categoria)
VALUES (1, 'Carne de Sol com Mandioca', 'Deliciosa carne de sol acebolada acompanhada de mandioca frita.', 45.90, 'Pratos Principais')
    ON CONFLICT DO NOTHING;

INSERT INTO produtos (id_produto, nome, descricao, preco, categoria)
VALUES (2, 'Dado de Tapioca', 'Dadinhos de tapioca com queijo coalho e melaço de cana.', 28.50, 'Entradas')
    ON CONFLICT DO NOTHING;

INSERT INTO produtos (id_produto, nome, descricao, preco, categoria)
VALUES (3, 'Baião de Dois', 'Arroz, feijão de corda, queijo coalho, carne seca e bacon.', 38.00, 'Pratos Principais')
    ON CONFLICT DO NOTHING;

INSERT INTO estoque (id_estoque, id_unidade, id_produto, quantidade_saldo)
VALUES (1, 1, 1, 50)
    ON CONFLICT DO NOTHING;

INSERT INTO estoque (id_estoque, id_unidade, id_produto, quantidade_saldo)
VALUES (2, 1, 2, 30)
    ON CONFLICT DO NOTHING;

INSERT INTO estoque (id_estoque, id_unidade, id_produto, quantidade_saldo)
VALUES (3, 2, 3, 20)
    ON CONFLICT DO NOTHING;