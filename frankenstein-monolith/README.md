# The Refactored Monolith — Codex of Authors & Books

> “Todo sistema legado carrega cicatrizes. Alguns chamam isso de dívida técnica. Outros chamam de dungeon.”

Projeto acadêmico originalmente desenvolvido utilizando:

* Angular
* Spring Boot

O objetivo deste projeto foi reconstruir completamente o backend da aplicação, substituindo a arquitetura original em Spring Boot por uma nova implementação utilizando Django REST Framework, mantendo compatibilidade total com o frontend Angular já existente.

---

# Objetivo do Projeto

O sistema original possuía diversos problemas estruturais propositalmente inseridos para fins educacionais:

* Controller excessivamente acoplado;
* Segurança inexistente;
* Problemas de performance;
* Violação de princípios SOLID;
* Ausência de separação arquitetural;
* Exposição direta de entidades;
* Estrutura monolítica frágil.

A missão foi transformar o “Monolito Frankenstein” em uma aplicação moderna, desacoplada e organizada.

---

# A Grande Refatoração

O backend original em Spring Boot NÃO foi convertido linha por linha.

A abordagem utilizada foi:

* engenharia reversa do frontend Angular;
* análise dos contratos REST;
* estudo da arquitetura original;
* reimplementação completa em Django;
* preservação da compatibilidade funcional.

O frontend Angular foi tratado como a principal fonte de verdade comportamental da aplicação.

---

# Stack Utilizada

## Frontend

* Angular
* TypeScript
* Standalone Components
* RxJS

## Backend

* Django
* Django REST Framework
* SimpleJWT
* SQLite
* Faker

---

# Estrutura do Projeto

```text
frankenstein-monolith/
│
├── frontend/
│
├── backend/
│   ├── authors/
│   ├── books/
│   ├── config/
│   ├── manage.py
│   └── requirements.txt
│
├── docs/
│
└── README.md
```

---

# Funcionalidades Implementadas

## Sistema de Autores

* Listagem de autores
* Cadastro de autores
* Exclusão de autores
* Relacionamento com livros

## Sistema de Livros

* Listagem de livros
* Cadastro de livros
* Exclusão de livros
* Relacionamento com autores

## API REST

* Endpoints RESTful
* Serialização segura
* JSON padronizado
* Compatibilidade com Angular

## Segurança

* JWT Authentication
* Restrição de rotas
* Remoção de credenciais hardcoded

---

# Tasks da Atividade — Resolvidas

---

## Task 1 — O Mistério do Banco de Dados

### Problema Original

A estrutura original sofria com problema de:

```text
N + 1 Query Problem
```

O sistema realizava múltiplas consultas desnecessárias ao carregar autores e livros.

### Solução Aplicada

No backend Django foram utilizados:

* `select_related`
* `prefetch_related`
* otimização ORM
* carregamento eficiente de relacionamentos

### Resultado

* menos consultas SQL;
* melhor performance;
* redução de carga no banco.

---

## Task 2 — A Segurança de Papel

### Problema Original

O backend original utilizava:

* `permitAll()`;
* credenciais hardcoded;
* ausência de autenticação real.

### Solução Aplicada

Foi implementado:

* JWT Authentication;
* controle de permissões;
* autenticação desacoplada;
* proteção de endpoints críticos.

### Resultado

* API protegida;
* autenticação moderna;
* maior segurança da aplicação.

---

## Task 3 — O Service Gordo

### Problema Original

Toda lógica estava concentrada no controller:

* validações;
* regras de negócio;
* transformação de dados;
* acesso ao banco.

### Solução Aplicada

Foi realizada separação arquitetural utilizando:

* Services;
* Serializers;
* camada de domínio;
* responsabilidade única;
* arquitetura desacoplada.

### Resultado

* código mais limpo;
* manutenção simplificada;
* melhor escalabilidade;
* separação clara de responsabilidades.

---

# Integração Angular + Django

O frontend Angular permaneceu funcional sem alterações estruturais profundas.

A nova API Django foi construída mantendo:

* endpoints compatíveis;
* formatos JSON esperados;
* fluxo original da aplicação;
* comportamento REST equivalente.

---

# Seed de Dados

O sistema possui população automatizada de dados utilizando Faker.

Permite:

* testes realistas;
* validação de performance;
* demonstração visual da aplicação.

---

# Como Rodar o Projeto

---

# Backend

```bash
cd backend

pip install -r requirements.txt

python manage.py migrate

python manage.py runserver
```

Backend:

```text
http://localhost:8000
```

---

# Frontend

```bash
cd frontend

npm install

npm start
```

Frontend:

```text
http://localhost:4200
```

---

# Endpoints

## Authors

```text
GET     /api/v1/authors
POST    /api/v1/authors
DELETE  /api/v1/authors/{id}
```

## Books

```text
GET     /api/v1/books
POST    /api/v1/books
DELETE  /api/v1/books/{id}
```

---

# Considerações Técnicas

Este projeto representa uma reconstrução arquitetural completa do backend original.

O foco principal foi:

* desacoplamento;
* clareza estrutural;
* compatibilidade;
* manutenção;
* escalabilidade;
* aplicação de boas práticas modernas.

---

# Considerações Finais

O antigo monolito não foi destruído.

Foi estudado.
Mapeado.
Compreendido.

Depois disso, reescrito.

Como qualquer dungeon antiga:
o problema nunca era apenas o monstro.

Era a arquitetura do lugar.
