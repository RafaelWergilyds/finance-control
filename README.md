# FinanceControl

**FinanceControl** é um projeto de aplicativo de **controle financeiro pessoal**, desenvolvido com o objetivo de ajudar usuários a **monitorar seus gastos diários e mensais**, **organizando-os por categorias** e promovendo uma melhor gestão financeira.

---

## Objetivos do Projeto

- Proporcionar aos usuários um **maior controle sobre suas finanças pessoais**;  
- Facilitar a **visualização e acompanhamento dos gastos ao longo do tempo**;  
- Oferecer uma **experiência simples, organizada e intuitiva**;  
- Servir como um **exercício prático de arquitetura e boas práticas de desenvolvimento back-end**.

---

## Tecnologias Utilizadas

- **Java 24**  
- **Spring Boot**  
- **JPA / Hibernate**  
- **Maven**
- **JUnit / Mockito**
- **Testcontainers / Rest Assured**
- **Docker**

---

## Arquitetura e Boas Práticas

O projeto segue alguns **princípios de DDD (Domain-Driven Design)**, com foco em organização e separação de responsabilidades:

- **Domain** → Contém as entidades e regras de negócio principais.  
- **Application** → Contém os casos de uso e lógica de aplicação.  
- **Infrastructure** → Camada de entrada da aplicação (controllers REST) e pesistência de dados. 

---

## Testes

O projeto conta com:
- **Testes unitários**, para validar regras de negócio isoladamente;  
- **Testes de integração e E2E**, garantindo o correto funcionamento entre os módulos e endpoints
