---
title: "Livraria API — roteiro da apresentação (AV2)"
---

# Ordem da demo (~7 min)

**1. O que tem de novo (30 s)**
"Na AV1 era só o CRUD de livro. Agora tem autor, categoria e editora, o livro ligado a eles por chave estrangeira, autenticação com JWT, documentação com Swagger, migrations com Flyway e a aplicação em Docker. São 5 controllers: Livro, Autor, Categoria, Editora e Auth."

**2. Subir com Docker (1 min)**

- Terminal: `sudo docker compose up --build`.
- "O Dockerfile tem duas etapas: uma compila com o JDK e a outra roda só com o JRE, então a imagem final é menor. Roda com um usuário comum, não root, e as dependências são baixadas antes do código pra aproveitar o cache."
- No log, apontar `Successfully applied 5 migrations`: é o Flyway criando o banco.

**3. Flyway (1 min)**

- Mostrar a pasta `db/migration/`: V1 autor, V2 categoria, V3 editora, V4 livro, V5 usuário.
- "Cada mudança no banco é um arquivo SQL versionado. O Flyway aplica em ordem, uma vez só, e registra na tabela `flyway_schema_history`. Troquei o `ddl-auto` de `update` pra `validate`: o Hibernate só confere, quem cria é o Flyway."
- "O livro é a V4 porque tem chave estrangeira pras outras três, e elas precisam existir antes."

**4. Swagger + JWT na prática (3 min)**

1. Abrir `localhost:8080/swagger-ui/index.html`. Mostrar os grupos (`@Tag`) e os status documentados em cada rota.
2. `GET /api/v1/livros` **sem token** → **401**.
3. `POST /api/auth/register` (o exemplo já vem preenchido) → **201**.
4. `POST /api/auth/login` → copiar o token → botão **Authorize** → colar.
5. Cadastrar autor, categoria e editora; copiar os IDs.
6. Cadastrar livro com os 3 IDs → **201**; a resposta mostra os nomes.
7. Livro com ID de autor que não existe → **404** "Autor não encontrado".
8. Excluir um autor que tem livro → **409** (a chave estrangeira protegendo).

**5. JWT por dentro (1 min)**

- `JwtFilter`: "roda antes de toda requisição; pega o token do cabeçalho `Authorization: Bearer`, valida a assinatura e a expiração e marca o usuário como autenticado."
- `SecurityConfig`: "STATELESS, sem sessão; `/api/auth/**` e Swagger liberados; o resto exige token, senão 401."
- A senha é salva com BCrypt, nunca em texto puro.

# Se algo der errado na hora

- **Porta 8080 ocupada:** outra API rodando. Feche o outro terminal, ou troque no `compose.yaml` pra `"8081:8080"` e abra `localhost:8081`.
- **"Cannot connect to the Docker daemon":** `sudo systemctl start docker`.
- **Sem Docker:** dá pra rodar direto com `JAVA_HOME=/usr/lib/jvm/java-25-openjdk ./mvnw spring-boot:run`.
- **401 em tudo no Swagger:** o token não foi colado no Authorize, ou a API reiniciou (banco zerou) → register e login de novo.
