# Livraria API

API REST de uma livraria, feita na disciplina de Laboratório de Programação (AV1 e AV2). Gerencia **livros, autores, categorias e editoras**, com login por **JWT**.

## Tecnologias
- Java 25 e Spring Boot 4.1 (Web MVC, Data JPA, Validation, Security)
- Banco H2 em memória, versionado com **Flyway**
- Autenticação **JWT** (jjwt 0.11.5) e senhas com **BCrypt**
- Documentação com **Swagger** (springdoc-openapi)
- **Docker** e Docker Compose

## Como rodar

Com Docker (não precisa ter Java instalado):
```bash
docker compose up --build
```

Sem Docker (precisa do Java 25):
```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. O banco é em memória: começa vazio a cada vez que a API sobe.

## Como usar
1. Abra o Swagger: `http://localhost:8080/swagger-ui/index.html`.
2. Crie uma conta em `POST /api/auth/register` e faça login em `POST /api/auth/login`.
3. Copie o `token` da resposta, clique em **Authorize** e cole.
4. Pronto: as outras rotas passam a funcionar. Sem token, elas respondem **401**.

## Rotas

| Recurso | Rotas |
|---|---|
| Autenticação (livres) | `POST /api/auth/register` · `POST /api/auth/login` |
| Livros | `/api/v1/livros` |
| Autores | `/api/v1/autores` |
| Categorias | `/api/v1/categorias` |
| Editoras | `/api/v1/editoras` |

Cada recurso tem `POST` (criar), `GET` (listar), `GET /{id}` (buscar), `PUT /{id}` (atualizar) e `DELETE /{id}` (excluir).

O livro é ligado ao autor, à categoria e à editora por **chave estrangeira**: no cadastro, o JSON leva os ids deles (`autor`, `categoria`, `editora`). Excluir um autor, categoria ou editora que ainda tem livro responde **409**.

## Estrutura

```
src/main/java/br/com/melqui/livraria/
├── controller/   rotas HTTP (5 controllers)
├── service/      regra de negócio
├── repository/   acesso ao banco (Spring Data JPA)
├── model/        entidades (tabelas)
├── dto/          formato do JSON de entrada e saída
├── mapper/       converte DTO ↔ entidade
├── exception/    exceções e o tratamento global de erros
├── security/     JwtService e JwtFilter
└── config/       SecurityConfig e OpenApiConfig
src/main/resources/
├── application.yaml
└── db/migration/   migrations do Flyway (V1 a V5)
```

## Material de estudo
Na pasta [`aulas/av2/`](aulas/av2/) estão os PDFs que explicam o projeto: o código de cada arquivo com a explicação, o papel de cada parte, o roteiro da apresentação e as perguntas prováveis com resposta.
