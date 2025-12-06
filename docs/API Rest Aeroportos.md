# Trabalho Prático

## API Rest: Gerenciamento de Aeroportos

1. # Sistema de Gerenciamento de Aeroportos

   A [OpenFlights](https://openflights.org/) é um projeto de código aberto que se dedica a coletar, armazenar e disponibilizar dados relacionados à aviação, incluindo informações sobre aeroportos, rotas de voo e companhias aéreas. O projeto busca fornecer uma fonte aberta e acessível de dados para desenvolvedores, pesquisadores e entusiastas da aviação em todo o mundo. Nesta atividade, usaremos um conjunto de dados de aeroportos do OpenFlights para construir um serviço que gerencia essas informações.

2. # Objetivo

   Desenvolver uma API REST completa para gerenciar o cadastro de aeroportos espalhados pelo mundo. A API deverá seguir os padrões e endpoints especificados abaixo e ser capaz de manipular os dados de aeroportos em um banco de dados relacional.

3. # Dicionário de Dados

   Antes de implementar a API, é necessário criar o esquema do banco de dados considerando os dados do dicionário de dados abaixo e popular a tabela Aeroporto com os dados do arquivo `airports.csv` que está disponível em: `https://raw.githubusercontent.com/profdiegoaugusto/banco-dados/master/mysql/linguagem-consulta-dados/data/airports.csv`

| Campo | Tipo | Descrição |
| :---- | :---- | :---- |
| **`id_aeroporto`** | Inteiro | Chave primária que identifica de maneira única cada estação metereológica. |
| **`nome_aeroporto`** | Texto | Nome do aeroporto. |
| **`codigo_iata`** | Texto | O código aeroportuário IATA é uma sigla composta por três letras, utilizada para designar os aeroportos em todo o mundo. |
| **`cidade`** | Texto | Nome da cidade onde está localizado o aeroporto. |
| **`codigo_pais_iso`** | Texto | Código (ISO 3166-1) para nomes de países que utiliza um sistema de 2 letras (alfa-2), por exemplo: Brasil (BR), Estados Unidos (US) |
| **`latitude`** | Real | Um número real representando a latitude. |
| **`longitude`** | Real | Um número real representando a longitude. |
| **`altitude`** | Real | Um número real representando a altitude. |

   

4. # Especificação da API (*Endpoints*)

| HTTP | URL | Descrição |
| :---- | :---- | :---- |
| **`GET`** | **/api/v1/aeroportos** | Obter todos os aeroportos. |
| **`GET`** | **/api/v1/aeroportos/{iata}** | Obter um aeroporto pelo código aeroportuário IATA. |
| **`POST`** | **/api/v1/aeroportos** | Adicionar um novo aeroporto |
| **`PUT`** | **/api/v1/aeroportos/{iata}** | Atualizar um aeroporto existente. |
| **`DELETE`** | **/api/v1/aeroportos/{iata}** | Excluir um aeroporto. |

   

5. # Testes

   Para garantir a qualidade e a robustez da API, vocês deverão implementar testes automatizados, cobrindo tanto as unidades lógicas quanto a integração da API como um todo. Desta forma, o projeto O projeto deve ser configurado para usar os plugins corretos do Maven para cada tipo de teste:

* **Maven Surefire Plugin ([`maven-surefire-plugin`](https://maven.apache.org/surefire/maven-surefire-plugin/)):** Deve ser configurado para executar os **testes de unidade** (ex: classes terminadas em **`*Test.java`**).

* **Maven Failsafe Plugin ([`maven-failsafe-plugin`](https://maven.apache.org/surefire/maven-failsafe-plugin/)):** Deve ser configurado para executar os **testes de integração** (ex: classes terminadas em **`*IT.java`** \- "IT" de *Integration Test*).

  1. # Testes de Unidade

     O foco aqui é testar as "unidades" de lógica de forma isolada, sem depender da API estar no ar ou de um banco de dados real.

* **Camada de Domínio**

  * A função que converte Pés para Metros (ex: converterPesParaMetros(1000)) retorna o valor esperado (304.8)?

  * A função que mapeia o Nome do País para o Código ISO (ex: obterIsoPais("Brazil")) retorna "BR"?

* **Camada de Serviço**

  * Ao tentar buscar um aeroporto por um IATA que não existe, o serviço lança a exceção esperada (ex: `AeroportoNaoEncontradoException`)?

  * Ao tentar salvar um aeroporto com dados inválidos (ex: `codigo_iata` com 4 letras ou altitude negativa), o serviço rejeita a operação ou lança uma exceção de validação?


  2. # Testes de Integração

     O foco aqui é testar a API "de ponta a ponta", garantindo que os *endpoints* estão funcionando corretamente, recebendo requisições HTTP e interagindo com o banco de dados (pode ser um banco em memória, como o H2, ou um banco real de testes). Você deverá testar o fluxo completo de cada endpoint da API (Seção 4):

  * **`POST /api/v1/aeroportos`:** Enviar um JSON de um novo aeroporto, verificar se a API retorna `Status 201 (Created)` e se o aeroporto foi realmente salvo no banco.

  * **`GET /api/v1/aeroportos/{iata}`:** Após criar um aeroporto, fazer uma requisição para este endpoint e verificar se a API retorna `Status 200 (OK)` e o JSON correto do aeroporto.

  * **`PUT /api/v1/aeroportos/{iata}`:** Enviar uma atualização para um aeroporto existente e verificar se a API retorna `Status 200 (OK)` e se os dados foram alterados no banco.

  * **`DELETE /api/v1/aeroportos/{iata}`:** Deletar um aeroporto e verificar se a API retorna `Status 204 (No Content)`.

  * **`GET /api/v1/aeroportos/{iata}` (Após DELETE):** Verificar se o mesmo `GET` de antes agora retorna `Status 404 (Not Found)`.

    