<h1 align="center" style="font-weight: bold;">ms-bank 💻</h1>

<p align="center">
 <a href="#tech">Tecnologia</a> •
 <a href="#Modulos">Modulos</a>•
 <a href="#started">Começando</a> • 
 <a href="#contribute">Contribuição</a>
</p>

<p align="center">
    <b>Este projeto é uma solução completa para um sistema bancário simulado, integrado com várias funcionalidades que podem ser aplicadas a um jogo como Banco Imobiliário. O sistema gerencia a criação de cartões, validação de cartões, realização de transações e auditoria de todas as operações, além de enviar notificações relacionadas às ações realizadas.</b>
</p>

# Projeto de Sistema Bancário
Este projeto é uma solução completa para um sistema bancário simulado, integrado com várias funcionalidades que podem ser aplicadas a um jogo como Banco Imobiliário. O sistema gerencia a criação de cartões, validação de cartões, realização de transações e auditoria de todas as operações, além de enviar notificações relacionadas às ações realizadas.

<h2 id="Modulos">💻 Modulos</h2>

- User Management: Serviço responsável por gerenciar usuários (criação, atualização, remoção e busca).
- Card Generation: Serviço responsável por gerar cartões com base nos dados fornecidos e associá-los a usuários.
- Card Validation: Serviço que valida a autenticidade e o saldo do cartão.
- Card Transaction: Serviço que realiza transações entre cartões e atualiza saldos.
- Audit Service: Serviço que audita as operações (transações e geração de cartões) realizadas no sistema.


<h2 id="tech">💻 Tecnologia</h2>

- Java 11: Linguagem de programação utilizada para o desenvolvimento.
- Spring Boot: Framework principal para a criação de microserviços.
- Spring Cloud OpenFeign: Para comunicação entre os microserviços.
- Spring Data JPA: Para persistência e gerenciamento de dados.
- H2 Database: Banco de dados em memória utilizado para testes.
- Lombok: Para reduzir a verbosidade do código.
- JUnit e Mockito: Para criação de testes unitários.
- Maven: Ferramenta de gerenciamento de dependências e build.
- Spring Mail: Utilizado para enviar notificações via e-mail.


<h2 id="started">🚀 Começando</h2>
<h3>Pré-requisitos</h3>
Para rodar o projeto, você precisará ter instalado:

- JDK 11+
- Maven 3.6+

## Como Rodar o Projeto
Passo a Passo
Clone o repositório:

<h3>Clonando</h3>

```bash
git clone https://github.com/seu-usuario/bank-system.git
```

Entre no diretório do projeto:

<h3>Comandos</h3>

```bash
cd bank-system
```
Compile o projeto: Execute o comando para compilar todos os módulos:

```bash
mvn clean install
```
Suba os microserviços: Cada serviço pode ser iniciado de forma independente. Você pode rodá-los com o seguinte comando dentro de cada diretório:

Para o user-management:

```bash
cd user-management
mvn spring-boot:run
```

Faça o mesmo para os outros módulos:

Here you list all prerequisites necessary for running your project. For example:

- Card-generation
- Card-validation
- Card-transaction
- Audit-service

<h3>Testar as APIs: Use uma ferramenta como Postman ou cURL para fazer requisições HTTP para os serviços. Cada serviço roda na porta configurada (geralmente 8080, 8081, etc.)</h3>

Exemplo de requisição:
Para criar um novo usuário no serviço de gerenciamento de usuários (User Management):

```bash
POST http://localhost:8080/api/users
{
    "username": "Netero",
    "email": "isaacnetero@example.com",
    "password": "senhaSegura123"
}
```
Para gerar um novo cartão (Card Generation):

```bash
POST http://localhost:8081/api/cards
{
    "username": "Luffy",
    "age": 25
}

```

## Script para criação das tabelas
```sql
-- Criar a tabela users
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT true,
    card_id BIGINT -- Para referenciar o cartão do usuário
);

-- Criar a tabela cards
CREATE TABLE cards (
    id BIGSERIAL PRIMARY KEY,
    card_number VARCHAR(16) NOT NULL,
    cvv VARCHAR(3) NOT NULL,
    expiration_date DATE NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user_card FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Garantir que o usuário tenha apenas um cartão
ALTER TABLE users
ADD CONSTRAINT fk_user_card FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE SET NULL;

-- Garantir unicidade do cartão para o usuário
ALTER TABLE cards
ADD CONSTRAINT unique_user_card UNIQUE (user_id);

-- Criar a tabela notifications
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Criar a tabela audit_logs
CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,
    action VARCHAR(255) NOT NULL,
    performed_by BIGINT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    CONSTRAINT fk_audit_user FOREIGN KEY (performed_by) REFERENCES users(id) ON DELETE CASCADE
);

-- Criar a tabela de transações de cartões
CREATE TABLE card_transactions (
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    card_id BIGINT NOT NULL,
    CONSTRAINT fk_transaction_card FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
);

-- Inserir usuário exemplo
INSERT INTO users (username, email, password, created_at, active)
VALUES ('john_doe', 'john.doe@example.com', 'hashed_password', NOW(), true);

-- Inserir cartão exemplo para o usuário
INSERT INTO cards (card_number, cvv, expiration_date, balance, created_at, user_id)
VALUES ('1234567812345678', '123', '2026-12-31', 500.00, NOW(), 1);

-- Atualizar o usuário para associar o cartão recém-criado
UPDATE users SET card_id = (SELECT id FROM cards WHERE user_id = 1) WHERE id = 1;

-- Inserir notificação exemplo
INSERT INTO notifications (message, created_at, user_id)
VALUES ('Sua conta foi atualizada', NOW(), 1);

-- Inserir transação exemplo
INSERT INTO card_transactions (amount, transaction_date, card_id)
VALUES (100.00, NOW(), (SELECT id FROM cards WHERE user_id = 1));

-- Inserir log de auditoria exemplo
INSERT INTO audit_logs (action, performed_by, timestamp)
VALUES ('User account updated', 1, NOW());

```

<h2 id="contribute">📫 Contribuição</h2>


Caso queira contribuir para o projeto:
-Crie sua branch
1. `git clone https://github.com/matheusned15/text-editor.git`
2. `git checkout -b feature/NAME`

-Tutorial de como abrir um pull request
3. Abra um Pull Request explicando o problema resolvido ou recurso realizado, se existir, anexe screenshot das modificações visuais e aguarde a revisão!

<h3>Documentação</h3>

[📝 How to create a Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)






