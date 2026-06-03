
@autenticacao
Feature: Autenticacao de Usuario
  Como um usuario do sistema de gerenciamento de produtos eletronicos
  Quero me autenticar na API
  Para poder acessar os recursos protegidos

  @smoke @login_sucesso
  Scenario: Login com credenciais validas
    Given que o usuario possui credenciais validas
    When o usuario faz uma requisicao POST para login com as credenciais
    Then o status da resposta deve ser 200
    And a resposta deve ser um JSON valido
    And a resposta deve conter um token de autenticacao
    And a resposta deve conter os dados do usuario autenticado
    And a resposta deve conter um refreshToken

  @login_credenciais_invalidas
  Scenario: Login com credenciais invalidas
    Given que o usuario possui credenciais invalidas
    When o usuario faz uma requisicao POST para login com as credenciais
    Then o status da resposta deve ser 400
    And a resposta de erro deve conter a mensagem de credenciais invalidas

  @login_senha_errada
  Scenario: Login com username valido e senha incorreta
    Given que o usuario possui username valido mas senha invalida
    When o usuario faz uma requisicao POST para login com as credenciais
    Then o status da resposta deve ser 400
    And a resposta de erro deve conter a mensagem de credenciais invalidas

  @login_sem_body
  Scenario: Tentativa de login sem enviar body
    When o usuario faz uma requisicao POST para login sem body
    Then o status da resposta deve ser 400

  @login_parametrizado
  Scenario Outline: Login com diferentes combinacoes de credenciais
    When o usuario faz login com username "<username>" e password "<password>"
    Then o status da resposta deve ser <status>

    Examples:
      | username    | password     | status |
      | emilys      | emilyspass   | 200    |
      | invalid     | invalid      | 400    |
      | emilys      | wrongpass    | 400    |
      |             | emilyspass   | 400    |
