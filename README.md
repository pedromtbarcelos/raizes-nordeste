# Raízes do Nordeste

## Requisitos

- **Linguagem:** Java 21
- **Framework:** Spring Boot 4.x
- **Base de Dados:** PostgreSQL
- **Gestor de Dependências:** Maven

## Como Configurar o Banco de Dados

Para rodar o projeto localmente, não é necessário criar arquivos .env extras. 

Basta abrir o arquivo src/main/resources/application.properties e garantir que as configurações do PostgreSQL estejam estruturadas conforme abaixo:
```
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
```
Observação: O banco de dados é povoado automaticamente ao iniciar a aplicação (spring.sql.init.mode=always). 
Os dados necessários para a execução dos testes T01 a T10 são inseridos sem a necessidade de rodar scripts manuais.

## Como Executar o Projeto

### 1) Criar a Base de Dados

Certifique-se de que o PostgreSQL está em execução localmente e crie a base de dados:
```
CREATE DATABASE db_raizes_nordeste;
```
### 2) Instalar Dependências

Execute no terminal (Linux/macOS):
```
./mvnw clean install -DskipTests
```
No Windows:
```
mvnw.cmd clean install -DskipTests
```
### 3) Iniciar a API

Execute no terminal (Linux/macOS):
```
./mvnw spring-boot:run
```
No Windows:
```
mvnw.cmd spring-boot:run
```
## Coleção de Testes Postman (.json)

O arquivo com a coleção de chamadas (raizes-do-nordeste.postman_collection.json) está disponível na pasta docs/postman/. 
Para facilitar a correção e avaliação do projeto, os testes foram organizados de T01 a T10, cobrindo o fluxo principal, regras de negócio e bloqueios de segurança conforme detalhado na documentação.

### Ordem para Execução dos Testes:

```
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
```

## Diagramas do Sistema

### Diagrama Entidade Relacionamento
<img src="docs/diagramas/der/diagrama-entidade-relacionamento.png" width="900">

O DER está disponível no repositório. As principais tabelas mapeadas para o banco PostgreSQL incluem:

```
- Unidades
- Produtos
- Estoque
- Pedidos
- Itens do Pedido
- Clientes
```

### Diagrama de Caso de Uso
<img src="docs/diagramas/uml/diagrama-de-casos-de-uso.png" width="700">

### Diagrama de Classe
<img src="docs/diagramas/uml/diagrama-de-classe.png" width="900">

### Diagrama Entidade Relacionamento
<img src="docs/diagramas/uml/diagrama-de-sequencia.png" width="700">

## Evidências

URL do Swagger Local: http://localhost:8080/swagger-ui/index.html

Ficheiro da Coleção Postman: \docs\postman\raizes-do-nordeste.postman_collection.json



