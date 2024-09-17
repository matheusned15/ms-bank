# Bank System Project
Este projeto é uma solução completa para um sistema bancário simulado, integrado com várias funcionalidades que podem ser aplicadas a um jogo como Banco Imobiliário. O sistema gerencia a criação de cartões, validação de cartões, realização de transações e auditoria de todas as operações, além de enviar notificações relacionadas às ações realizadas.

## Módulos:
User Management: Serviço responsável por gerenciar usuários (criação, atualização, remoção e busca).
Card Generation: Serviço responsável por gerar cartões com base nos dados fornecidos e associá-los a usuários.
Card Validation: Serviço que valida a autenticidade e o saldo do cartão.
Card Transaction: Serviço que realiza transações entre cartões e atualiza saldos.
Audit Service: Serviço que audita as operações (transações e geração de cartões) realizadas no sistema.
Notification Service: Serviço que envia notificações via email ou outro canal após operações realizadas.


## Tecnologias Utilizadas
Java 11: Linguagem de programação utilizada para o desenvolvimento.
Spring Boot: Framework principal para a criação de microserviços.
Spring Cloud OpenFeign: Para comunicação entre os microserviços.
Spring Data JPA: Para persistência e gerenciamento de dados.
H2 Database: Banco de dados em memória utilizado para testes.
Lombok: Para reduzir a verbosidade do código.
JUnit e Mockito: Para criação de testes unitários.
Maven: Ferramenta de gerenciamento de dependências e build.
Spring Mail: Utilizado para enviar notificações via e-mail.


Pré-requisitos
Para rodar o projeto, você precisará ter instalado:

JDK 11+
Maven 3.6+

## Como Rodar o Projeto
Passo a Passo
Clone o repositório:

```bash
git clone https://github.com/seu-usuario/bank-system.git
```

Entre no diretório do projeto:

```bash
cd bank-system
```
Compile o projeto: Execute o comando para compilar todos os módulos:

```bash
mvn clean install
```
Suba os microserviços: Cada serviço pode ser iniciado de forma independente. Você pode rodá-los com o seguinte comando dentro de cada diretório:

Para o user-management:

cd user-management
mvn spring-boot:run
Faça o mesmo para os outros módulos:

card-generation
card-validation
card-transaction
audit-service
notification-service
Testar as APIs: Use uma ferramenta como Postman ou cURL para fazer requisições HTTP para os serviços. Cada serviço roda na porta configurada (geralmente 8080, 8081, etc.).

Exemplo de requisição:
Para criar um novo usuário no serviço de gerenciamento de usuários (User Management):

```bash
POST http://localhost:8080/api/users
{
    "username": "johndoe",
    "email": "johndoe@example.com",
    "password": "senhaSegura123"
}
```
Para gerar um novo cartão (Card Generation):

POST http://localhost:8081/api/cards
{
    "username": "johndoe",
    "initialBalance": 1000.0
}

## Script para criação das tabelas
```sql
INSERT INTO users (username, email, password_hash) VALUES 
('johndoe', 'johndoe@example.com', 'senhaSegura123'),
('janedoe', 'janedoe@example.com', 'outraSenhaSegura321');

INSERT INTO cards (card_number, card_holder_name, cvv, expiration_date, balance) VALUES 
('1234567812345678', 'John Doe', '123', '2026-12-31', 1000.0),
('8765432187654321', 'Jane Doe', '456', '2026-12-31', 2000.0);

INSERT INTO transactions (transaction_id, sender_card, receiver_card, amount, timestamp) VALUES 
('tx123', '1234567812345678', '8765432187654321', 150.0, '2024-01-01 12:00:00'),
('tx124', '1234567812345678', '8765432187654321', 200.0, '2024-01-02 14:30:00');


