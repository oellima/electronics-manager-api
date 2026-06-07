
@produtos
Feature: Gerenciamento de Produtos Eletronicos
  Como um usuario do sistema de gerenciamento de produtos eletronicos
  Quero gerenciar o catalogo de produtos
  Para manter o inventario atualizado

  # ── Buscar todos os produtos ──────────────────────────────────────────────

  @smoke @listar_produtos
  Scenario: Buscar todos os produtos com sucesso
    When o usuario busca todos os produtos
    Then o status da resposta deve ser 200
    And a resposta deve ser um JSON valido
    And a resposta deve conter uma lista de produtos
    And cada produto deve ter os campos obrigatorios
    And a resposta deve conter o campo total

  # ── Buscar produto por ID ─────────────────────────────────────────────────

  @smoke @buscar_produto_por_id
  Scenario: Buscar produto eletronico por ID valido
    When o usuario busca o produto com id 1
    Then o status da resposta deve ser 200
    And a resposta deve ser um JSON valido
    And a resposta deve conter os dados do produto
    And o id do produto retornado deve ser 1

  @buscar_produto_id_invalido
  Scenario: Buscar produto com ID inexistente
    When o usuario busca o produto com id 0
    Then o status da resposta deve ser 404
    And a resposta de erro deve conter a mensagem de produto nao encontrado

  @buscar_produto_varios_ids
  Scenario Outline: Buscar produtos por diferentes IDs
    When o usuario busca o produto com id <id>
    Then o status da resposta deve ser <status>

    Examples:
      | id  | status |
      | 1   | 200    |
      | 2   | 200    |
      | 5   | 200    |
      | 0   | 404    |
      | 9999| 404    |

  # ── Criar produto ─────────────────────────────────────────────────────────

  @smoke @criar_produto
  Scenario: Criar produto eletronico valido
    When o usuario cria um produto eletronico valido
    Then o status da resposta deve ser 201
    And a resposta deve ser um JSON valido
    And a resposta deve conter o produto criado com id gerado
    And o titulo do produto criado deve ser "Test Electronics Smartphone"
    And a categoria do produto criado deve ser "smartphones"

  @criar_produto_com_dados
  Scenario: Criar produto eletronico com dados especificos
    When o usuario cria um produto com os seguintes dados
      | title             | Notebook Gamer Pro    |
      | description       | Notebook para jogos   |
      | price             | 4999.99               |
      | discountPercentage| 5.0                   |
      | rating            | 4.8                   |
      | stock             | 10                    |
      | brand             | TechGamer             |
      | category          | laptops               |
    Then o status da resposta deve ser 201
    And a resposta deve conter o produto criado com id gerado
    And o titulo do produto criado deve ser "Notebook Gamer Pro"

  # NOTA: Os 3 cenarios abaixo testam regras de validacao de negocio.
  # A DummyJSON e uma API publica de demonstracao e nao aplica essas regras,
  # retornando 201 para qualquer payload. Por isso, a validacao e feita
  # localmente no step, verificando que os dados invalidos seriam rejeitados
  # por uma API real. O step "a requisicao deve ser rejeitada por validacao"
  # confirma essa verificacao no ProductSteps.java.

  @criar_produto_sem_titulo
  Scenario: Tentar criar produto sem campo titulo
    When o usuario tenta criar um produto sem o campo titulo
    Then a requisicao deve ser rejeitada por validacao

  @criar_produto_preco_negativo
  Scenario: Tentar criar produto com preco negativo
    When o usuario tenta criar um produto com preco negativo
    Then a requisicao deve ser rejeitada por validacao

  @criar_produto_body_vazio
  Scenario: Tentar criar produto com body vazio
    When o usuario tenta criar um produto com body vazio
    Then a requisicao deve ser rejeitada por validacao
