# Raízes do Nordeste
##

## Requisitos
•	Linguagem: Java

•	Versão: Java SDK 21

•	Banco: PostgreSQL

•	Dependências: Todas as dependências definidas no pom.xml

## Como Configurar o Banco de Dados

Para rodar o projeto localmente, não é necessário criar arquivos .env extras. 

Basta abrir o arquivo src/main/resources/application.properties e garantir que as configurações do PostgreSQL estejam estruturadas conforme abaixo:

spring.application.name=backend

server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/db_raizes_nordeste

spring.datasource.username=postgres

spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

spring.jpa.defer-datasource-initialization=true

spring.sql.init.mode=always

## Como Executar o Projeto

### Instalar Dependências

Execute o comando a seguir no terminal para baixar as dependências e compilar o projeto:

./mvnw clean install -DskipTests

### Criar a Base de Dados

Certifique-se de ter o PostgreSQL rodando localmente na sua máquina e execute o seguinte comando no seu SGBD para criar a base de dados:

CREATE DATABASE db_raizes_nordeste;

### Iniciar a API

./mvnw spring-boot:run

## Coleção de Testes Postman (.json)

O arquivo com a coleção de chamadas (raizes-do-nordeste.postman_collection.json) está disponível na raiz do repositório. 
Para facilitar a correção e avaliação do projeto, os testes foram organizados de T01 a T10, cobrindo o fluxo principal, regras de negócio e bloqueios de segurança conforme detalhado na documentação.

### Ordem para Execução dos Testes:

T01 - Registo de novo cliente no sistema

T02 - Tentativa de login com senha incorreta

T03 - Login com credenciais válidas

T04 - Acesso a recurso protegido sem enviar token

T05 - Tentativa de alteração de estoque com perfil de cliente

T06 - Consulta de cardápio filtrado por unidade

T07 - Criação de pedido com canal e estoque válidos

T08 - Criação de pedido com quantidade superior ao estoque

T09 - Processamento de pagamento simulado aprovado

T10 - Consulta do saldo do programa de fidelidade

## Banco de Dados e Modelo (DER)

O DER está disponível no repositório. As principais tabelas mapeadas para o banco PostgreSQL incluem:

• Unidades

• Produtos

• Estoque

• Pedidos

• Itens do Pedido

• Clientes

• Usuarios

## Evidências

URL do Swagger Local: http://localhost:8080/swagger-ui/index.html

Arquivo da Coleção Postman: \docs\postman\raizes-do-nordeste.postman_collection.json (Disponível na raiz do repositório)

