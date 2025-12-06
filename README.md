# Airport Management API

## Objetivo do Projeto
- Construir uma API REST para gerenciar aeroportos do mundo, baseada no conjunto de dados do OpenFlights. A API permite criar, consultar, atualizar e excluir aeroportos, seguindo boas práticas (arquitetura hexagonal, SOLID) e com cobertura de testes automatizados.

## Tecnologias Utilizadas
- Java (compatível com Spring Boot 3)
- Spring Boot 3 (Web, JDBC)
- Banco de dados de produção: `SQLite`
- Banco de dados de testes: `H2` (memória)
- Maven (build, plugins `surefire` para unit e `failsafe` para integração)
- JUnit 5 e Mockito

## Configurar Ambiente e Instalar Dependências
- Pré‑requisitos:
  - `Java` instalado
  - `Maven` instalado
- Clonar o repositório e instalar dependências:
  - `git clone https://github.com/GuilhermeCostax/AirportManagement.git`
  - `cd AirportManagement`
  - `mvn clean package -DskipTests`
- Configuração de banco (produção):
  - A aplicação usa `SQLite` com URL padrão `jdbc:sqlite:./data/db/airport_management.db` (em `src/main/resources/application.properties`).
  - Crie a tabela `aeroporto` no SQLite antes de rodar a aplicação:
    - Exemplo de SQL (SQLite):
      ```sql
      CREATE TABLE IF NOT EXISTS aeroporto (
        id_aeroporto INTEGER PRIMARY KEY AUTOINCREMENT,
        nome_aeroporto TEXT NOT NULL,
        codigo_iata TEXT,
        cidade TEXT NOT NULL,
        codigo_pais_iso TEXT,
        latitude REAL,
        longitude REAL,
        altitude REAL
      );
      ```
  - Opcional: popular com dados do CSV do OpenFlights ou usar o endpoint `POST` para cadastrar.

## Como Executar a Aplicação
- Via Maven:
  - `mvn spring-boot:run`
- Via JAR:
  - `java -jar target/airport-management-*.jar`
- Endpoints principais:
  - `GET /api/v1/aeroportos` — lista todos
  - `GET /api/v1/aeroportos/{iata}` — busca por IATA (3 letras)
  - `POST /api/v1/aeroportos` — cria novo
  - `PUT /api/v1/aeroportos/{iata}` — atualiza existente
  - `DELETE /api/v1/aeroportos/{iata}` — exclui
- Exemplo de `POST`:
  ```bash
  curl -X POST http://localhost:8080/api/v1/aeroportos \
    -H 'Content-Type: application/json' \
    -d '{
      "nomeAeroporto": "Teste Airport",
      "codigoIata": "TST",
      "cidade": "Teste City",
      "codigoPaisIso": null,
      "latitude": 1.23,
      "longitude": 4.56,
      "altitude": 100.0
    }'
  ```

## Como Executar os Testes
- Testes de unidade (Surefire):
  - `mvn test`
- Testes de integração (Failsafe):
  - `mvn verify`
- Observações:
  - Os testes de integração sob o profile `test` usam `H2` com inicialização via `schema.sql` e `data.sql` (em `src/test/resources`).
  - São validados: `POST` (`201` + persistência), `GET` (`200`), `PUT` (`200` + atualização), `DELETE` (`204`) e `GET` após `DELETE` (`404`).

## Seed de Dados (Opcional)
- Objetivo: popular automaticamente a base `SQLite` a partir do CSV de aeroportos do OpenFlights.
- Como executar:
  - `mvn spring-boot:run -Dspring-boot.run.profiles=seed`
- Comportamento:
  - Carrega o CSV remoto e insere aeroportos apenas quando a tabela está vazia.
  - Converte altitude de pés para metros (`converterPesParaMetros`).
  - Mapeia nome do país para código ISO 3166-1 alfa-2 (`obterIsoPais`).
  - Ignora linhas sem `IATA` válido (exatamente 3 letras) ou sem `nome`/`cidade`.
