
@health_check
Feature: Health Check da API
  Como um usuario do sistema de gerenciamento de produtos eletronicos
  Quero verificar o status da API
  Para garantir que ela esta operacional antes de executar operacoes

  Background:
    Given a API esta disponivel em "https://dummyjson.com"

  @smoke @health_check_success
  Scenario: Verificar status da API com sucesso
    When o usuario faz uma requisicao GET para o endpoint de health check
    Then o status da resposta deve ser 200
    And a resposta deve ser um JSON valido
    And o campo status da resposta deve ser "ok"
    And o campo method da resposta deve ser "GET"

  @smoke @health_check_response_time
  Scenario: Verificar tempo de resposta do health check
    When o usuario faz uma requisicao GET para o endpoint de health check
    Then o status da resposta deve ser 200
    And o tempo de resposta deve ser aceitavel
