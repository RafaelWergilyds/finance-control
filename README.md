# FinanceControl

**FinanceControl** é uma API REST de controle financeiro pessoal que permite aos usuários monitorar gastos diários e mensais organizados por categorias, promovendo uma melhor gestão financeira.

---

## Índice

- [Objetivos](#objetivos-do-projeto)
- [Tecnologias](#tecnologias-utilizadas)
- [Arquitetura](#arquitetura)
- [Segurança](#segurança)
- [Documentação da API](#documentação-da-api)
- [Testes](#testes)
- [Como rodar localmente](#como-rodar-localmente)

---

## Objetivos do Projeto

- Proporcionar maior controle sobre finanças pessoais.
- Facilitar a visualização e acompanhamento dos gastos ao longo do tempo.
- Servir como exercício prático de arquitetura e boas práticas de desenvolvimento back-end.

---

## Tecnologias Utilizadas

| Tecnologia | Descrição |
|---|---|
| Java 21 | Linguagem principal |
| Spring Boot 3.5 | Framework de aplicação |
| Spring Security + JWT | Autenticação e autorização |
| Spring Data JPA / Hibernate | Persistência de dados |
| PostgreSQL | Banco de dados em produção/dev |
| H2 | Banco de dados em memória (testes) |
| Lombok | Redução de boilerplate |
| SpringDoc / Swagger UI | Documentação da API |
| Maven | Gerenciamento de dependências e build |
| Docker / Docker Compose | Containerização |
| JUnit / Mockito | Testes unitários |
| Testcontainers + Rest Assured | Testes de integração e E2E |

---

## Arquitetura

O projeto segue princípios de **DDD (Domain-Driven Design)** com separação de responsabilidades em três camadas:

```
src/
├── domain/           → Entidades e regras de negócio
├── application/      → Casos de uso e lógica de aplicação
└── infrastructure/   → Controllers REST, persistência e configurações
```

---

## Segurança

A autenticação é feita via **JWT** com par de chaves RSA (pública/privada). O token é gerado no login e deve ser enviado no header `Authorization: Bearer <token>` nas requisições protegidas.

---

## Documentação da API

Com a aplicação rodando, acesse o Swagger UI em:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Testes

O projeto conta com testes unitários (JUnit + Mockito) e testes de integração/E2E (Testcontainers + Rest Assured). Para executar:

```bash
./mvnw test
```

> Os testes de integração requerem o **Docker** em execução, pois o Testcontainers sobe o banco automaticamente.

---

## Como Rodar Localmente

### Pré-requisitos

- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/)
- Java 21 e Maven (somente para rodar sem Docker)

---

### Opção 1 — Docker Compose (recomendado)

Sobe o PostgreSQL e a API juntos, sem configuração adicional.

**1. Clone o repositório:**

```bash
git clone https://github.com/RafaelWergilyds/finance-control.git
cd finance-control
```

**2. Gere o par de chaves RSA** (necessário para o JWT):

```bash
openssl genrsa -out src/main/resources/app.key 2048
openssl rsa -in src/main/resources/app.key -pubout -out src/main/resources/app.pub
```

**3. Suba os containers:**

```bash
docker-compose -f docker-compose.dev.yml up -d
```

A API estará disponível em `http://localhost:8080`.

---

### Opção 2 — Maven (sem Docker)

Requer um **PostgreSQL local** já em execução.

**1. Clone o repositório e gere as chaves RSA** (igual aos passos 1 e 2 acima).

**2. Configure o arquivo `src/main/resources/application.properties`:**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/financeControl
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.profiles.active=dev
jwt.app.issuer=my-app
jwt.expiration.time=3600
jwt.private.key=classpath:app.key
jwt.public.key=classpath:app.pub
```

**3. Execute a aplicação:**

```bash
./mvnw spring-boot:run
```

---

Desenvolvido por [RafaelWergilyds](https://github.com/RafaelWergilyds).
