
@produtos_autenticados
Feature: Acesso a Produtos com Autenticacao
  Como um usuario autenticado do sistema de gerenciamento de produtos eletronicos
  Quero acessar os endpoints protegidos de produtos
  Para visualizar o catalogo restrito

  @smoke @produtos_auth_sucesso
  Scenario: Buscar produtos autenticados com token valido
    Given que o usuario esta autenticado
    When o usuario autenticado busca os produtos protegidos
    Then o status da resposta deve ser 200
    And a resposta deve ser um JSON valido
    And a resposta deve conter os produtos autenticados

  @produtos_auth_sem_token
  Scenario: Tentar buscar produtos autenticados sem token
    Given que o usuario nao esta autenticado
    When o usuario sem autenticacao tenta buscar os produtos protegidos
    Then o status da resposta deve ser 401
    And a resposta de erro deve conter mensagem de autenticacao

  @produtos_auth_token_invalido
  Scenario: Tentar buscar produtos autenticados com token invalido
    Given que o usuario possui um token invalido
    When o usuario com token invalido tenta buscar os produtos protegidos
    Then o status da resposta deve ser 401
    And a resposta de erro deve conter mensagem de autenticacao

  @produtos_auth_fluxo_completo
  Scenario: Fluxo completo - login e acesso a produtos protegidos
    Given que o usuario possui credenciais validas
    When o usuario faz uma requisicao POST para login com as credenciais
    Then o status da resposta deve ser 200
    And a resposta deve conter um token de autenticacao
    When o usuario autenticado busca os produtos protegidos
    Then o status da resposta deve ser 200
    And a resposta deve conter os produtos autenticados
