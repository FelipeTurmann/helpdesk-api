# Help Desk API

API REST para gerenciamento de chamados técnicos em um cenário multiempresa. Ela permite que administradores cadastrem empresas e usuários, acompanhem indicadores e conduzam os chamados; usuários clientes podem abrir, consultar e comentar chamados vinculados à própria empresa.

## Sobre

O projeto centraliza a comunicação entre empresas clientes e a equipe de suporte. Seus recursos principais são:

- cadastro e manutenção de empresas e usuários;
- autenticação por e-mail e senha, com emissão de token JWT;
- abertura, consulta, atualização e exclusão de chamados;
- atualização do status de chamados pela administração;
- comentários por chamado;
- filtros para empresas, usuários e chamados;
- painel com totais de chamados por status;
- validação de entradas e respostas padronizadas para erros.

Há dois perfis de acesso:

| Perfil | Permissões |
| --- | --- |
| `ADMIN` | Administra empresas e usuários, consulta todos os chamados, altera status, exclui chamados e acessa o dashboard. |
| `CLIENTE` | Abre e edita chamados abertos, consulta e comenta somente chamados da empresa à qual está vinculado. |

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Web MVC e Bean Validation
- Spring Data JPA / Hibernate
- Spring Security
- JWT (JJWT)
- PostgreSQL 16
- Flyway
- Maven e Maven Wrapper
- Docker Compose
- Swagger / OpenAPI (Springdoc)
- Lombok e MapStruct
- Bucket4j para limitação de tentativas de login

## Arquitetura

O projeto segue uma organização em camadas, separada por domínio (`auth`, `empresa`, `usuario`, `chamado`, `comentario` e `dashboard`). Controllers expõem os recursos HTTP; services concentram regras de negócio e autorização complementar; repositories acessam o banco com JPA e Specifications; mappers convertem entidades e DTOs.

```text
Cliente HTTP
    ↓
Security filters (JWT e rate limit)
    ↓
Controller → DTO / validação
    ↓
Service → regras de negócio e autorização
    ↓
Repository / Specification
    ↓
PostgreSQL
```

As migrações do Flyway criam as tabelas `empresa`, `usuario`, `chamado` e `comentario`. O Hibernate permanece em modo `validate`, portanto não altera o esquema fora das migrações.

## Como executar

### Pré-requisitos

- JDK 21
- Docker e Docker Compose (recomendado para o PostgreSQL)

### 1. Inicie o banco de dados

```bash
docker compose up -d
```

O `docker-compose.yml` inicia o PostgreSQL em `localhost:5432`, com banco `helpdesk` e credenciais `postgres` / `postgres`.

### 2. Execute a aplicação

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Em Linux ou macOS:

```bash
./mvnw spring-boot:run
```

Na primeira inicialização, o Flyway aplica automaticamente as migrações em `src/main/resources/db/migration`.

> As migrações atuais não inserem um usuário administrador inicial. Como os endpoints de empresas e usuários exigem o papel `ADMIN`, é necessário provisionar o primeiro administrador no banco, ou adicionar uma migração/semente de desenvolvimento antes de operar a API pela primeira vez.

### Configuração

As propriedades locais estão em [`src/main/resources/application.yaml`](./src/main/resources/application.yaml). Para outro ambiente, ajuste especialmente:

- `spring.datasource.url`, `username` e `password`;
- `jwt.secret` — use um segredo forte, privado e fornecido por variável/gerenciador de segredos;
- `jwt.expiration` — duração do token em milissegundos.

## Documentação da API

Com a aplicação em execução, acesse:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Especificação OpenAPI: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

Para endpoints protegidos, obtenha um token no login e informe-o no Swagger pelo botão **Authorize**, no formato `Bearer <token>`.

### Autenticação

`POST /api/auth/login`

```json
{
  "email": "admin@empresa.com",
  "senha": "sua-senha"
}
```

A resposta contém o token JWT. O endpoint permite até cinco tentativas por minuto para cada IP; novas tentativas recebem `429 Too Many Requests` até o reabastecimento do limite.

## Endpoints principais

| Recurso | Endpoints | Acesso |
| --- | --- | --- |
| Autenticação | `POST /api/auth/login` | Público |
| Empresas | `GET`, `POST /api/empresas`; `GET`, `PUT`, `DELETE /api/empresas/{id}` | `ADMIN` |
| Usuários | `GET`, `POST /api/usuarios`; `GET`, `PUT`, `DELETE /api/usuarios/{id}` | `ADMIN` |
| Chamados | `GET`, `POST /api/chamados`; `GET`, `PUT`, `DELETE /api/chamados/{id}` | Conforme perfil |
| Status do chamado | `PATCH /api/chamados/{id}/status` | `ADMIN` |
| Comentários | `GET`, `POST /api/chamados/{chamadoId}/comentarios` | `ADMIN` ou `CLIENTE` da empresa do chamado |
| Dashboard | `GET /api/dashboard` | `ADMIN` |

Os chamados aceitam os status `ABERTO`, `EM_ATENDIMENTO`, `AGUARDANDO_CLIENTE`, `RESOLVIDO` e `FECHADO`, e as prioridades `BAIXA`, `MEDIA`, `ALTA` e `CRITICA`.

### Exemplo: abrir um chamado

```http
POST /api/chamados
Authorization: Bearer <token-do-cliente>
Content-Type: application/json

{
  "titulo": "Erro ao acessar o sistema",
  "descricao": "A página inicial apresenta erro ao carregar.",
  "categoria": "Acesso",
  "prioridade": "ALTA"
}
```

O usuário autenticado é associado automaticamente ao chamado, assim como sua empresa. Um chamado só pode ser editado pelo cliente enquanto estiver com status `ABERTO`.

## Filtros disponíveis

Os endpoints de listagem recebem parâmetros de consulta opcionais:

- `GET /api/empresas`: `nome`, `cnpj`, `telefone`, `email`;
- `GET /api/usuarios`: `nome`, `email`, `cargo`, `empresaId`, `ativo`;
- `GET /api/chamados`: `status`, `prioridade`, `categoria`, `empresaId`.

Para um `CLIENTE`, o filtro `empresaId` dos chamados é sempre limitado à empresa do usuário autenticado.

## Testes e build

```powershell
.\mvnw.cmd test
.\mvnw.cmd clean package
```

## Tratamento de erros

As respostas de erro são uniformizadas pela aplicação. Os principais códigos retornados são:

| Status | Situação |
| --- | --- |
| `400` | Dados de entrada inválidos. |
| `401` | Credenciais inválidas ou token ausente/inválido. |
| `403` | Usuário autenticado sem permissão. |
| `404` | Recurso não encontrado. |
| `409` | Violação de dado único ou integridade. |
| `422` | Regra de negócio não atendida. |
| `429` | Limite de tentativas de login excedido. |
