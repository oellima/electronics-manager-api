# Electronics Manager — API Test Suite

Suíte de testes automatizados para o sistema de **gerenciamento de produtos eletrônicos**, construída com **Java 11 + RestAssured + Cucumber (Gherkin)**, com relatórios automáticos via **Allure** e **ExtentReports**, e pipeline de CI/CD via **GitHub Actions**.

---

## 📋 Informações do Projeto

| Item | Detalhe |
|------|---------|
| **Linguagem** | Java 11 |
| **Build Tool** | Maven 3.8+ |
| **Framework de Testes** | JUnit 5 + Cucumber 7 |
| **Client HTTP** | REST Assured 5.4 |
| **BDD** | Gherkin (Cucumber) |
| **Relatórios** | Allure + ExtentReports + Cucumber HTML |
| **CI/CD** | GitHub Actions |
| **API Alvo** | [DummyJSON](https://dummyjson.com) |

---

## 🏗️ Estrutura do Projeto

```
electronics-manager/
├── .github/
│   └── workflows/
│       └── ci.yml                        # Pipeline GitHub Actions
├── src/
│   └── test/
│       ├── java/
│       │   └── com/electronics/
│       │       ├── hooks/
│       │       │   └── Hooks.java        # Setup/Teardown por cenário
│       │       ├── models/
│       │       │   ├── Product.java      # Model de produto
│       │       │   └── LoginRequest.java # Model de login
│       │       ├── runners/
│       │       │   └── CucumberRunner.java # Runner principal
│       │       ├── steps/
│       │       │   ├── AuthSteps.java        # Steps de autenticação
│       │       │   ├── CommonSteps.java      # Steps compartilhados
│       │       │   ├── HealthCheckSteps.java # Steps de health check
│       │       │   └── ProductSteps.java     # Steps de produtos
│       │       └── utils/
│       │           ├── Constants.java        # Constantes globais
│       │           ├── RequestHelper.java    # Helper REST Assured
│       │           └── ScenarioContext.java  # Contexto por cenário
│       └── resources/
│           ├── features/
│           │   ├── autenticacao.feature          # Cenários de autenticação
│           │   ├── health_check.feature          # Cenários de health check
│           │   ├── produtos.feature              # Cenários de produtos
│           │   └── produtos_autenticados.feature # Cenários produtos auth
│           ├── allure.properties
│           ├── extent.properties
│           └── logback-test.xml
└── pom.xml
```

---

## 🚀 Como Executar

### Pré-requisitos

- **Java 11+** instalado (`java -version`)
- **Maven 3.8+** instalado (`mvn -version`)
- Conexão com a internet (API DummyJSON online)

### Clonar o repositório

```bash
git clone https://github.com/<seu-usuario>/electronics-manager.git
cd electronics-manager
```

### Executar todos os testes

```bash
mvn clean test
```

### Executar por tag

```bash
# Apenas smoke tests
mvn clean test -Dcucumber.filter.tags="@smoke"

# Apenas testes de autenticação
mvn clean test -Dcucumber.filter.tags="@autenticacao"

# Apenas testes de produtos
mvn clean test -Dcucumber.filter.tags="@produtos"

# Ignorar cenários marcados com @ignore
mvn clean test -Dcucumber.filter.tags="not @ignore"
```

### Gerar relatório Allure (após rodar os testes)

```bash
mvn allure:report
# Relatório gerado em: target/site/allure-maven-plugin/
```

### Abrir relatório Allure no browser

```bash
mvn allure:serve
```

---

## 📊 Relatórios Gerados Automaticamente

Após a execução (`mvn clean test`) os seguintes relatórios são gerados:

| Relatório | Caminho |
|-----------|---------|
| **Cucumber HTML** | `target/cucumber-reports/cucumber.html` |
| **Cucumber JSON** | `target/cucumber-reports/cucumber.json` |
| **ExtentReports Spark** | `target/extent-reports/SparkReport.html` |
| **Allure Results** | `target/allure-results/` |
| **Surefire XML** | `target/surefire-reports/` |
| **Logs de Execução** | `target/logs/test-execution.log` |

---

## 🧪 Plano de Teste e Estratégia

### Estratégia

Os testes seguem a pirâmide de testes com foco em **testes de API (contrato e integração)**:

1. **Smoke Tests** (`@smoke`): Cenários críticos de happy path — executados em cada build.
2. **Testes Funcionais**: Cobertura completa dos endpoints com fluxos positivos e negativos.
3. **Testes de Exceção/Erro**: Validação de respostas de erro (400, 401, 403, 404).
4. **Testes Parametrizados**: `Scenario Outline` para cobrir múltiplas combinações de entrada.

### Endpoints Cobertos

| Endpoint | Método | Cenários |
|----------|--------|----------|
| `/test` | GET | Status OK, tempo de resposta, campos de resposta |
| `/auth/login` | POST | Login válido, credenciais inválidas, sem body, senha errada, combinações |
| `/products` | GET | Listar todos, campos obrigatórios, paginação |
| `/products/{id}` | GET | ID válido, ID inexistente (0, 9999), múltiplos IDs |
| `/products/add` | POST | Produto válido, sem título, preço negativo, body vazio, dados específicos |
| `/auth/products` | GET | Com token válido, sem token, token inválido, fluxo completo |

### Tipos de Validação

- ✅ Status HTTP (200, 201, 400, 401, 403, 404)
- ✅ Schema / campos obrigatórios presentes
- ✅ Valores de campos específicos
- ✅ Presença de token JWT na resposta de login
- ✅ Mensagens de erro descritivas
- ✅ Tempo de resposta aceitável (< 10s)
- ✅ Corpo de resposta JSON válido

---

## 🐛 Bugs Identificados

| # | Endpoint | Severidade | Descrição |
|---|----------|------------|-----------|
| B01 | `POST /products/add` | Média | A API aceita produtos com body vazio (retorna 201 em vez de 400). O DummyJSON é uma API de mock e não valida campos obrigatórios. Os cenários de erro para criação de produto sem título/preço negativo/body vazio estão marcados — se a API retornar 201 nesses casos, o teste irá falhar, documentando o comportamento incorreto. |
| B02 | `POST /auth/login` | Baixa | A documentação indica status 201 para login bem-sucedido, mas a API retorna 200. Os testes usam 200 (comportamento real). |
| B03 | `GET /auth/products` | Baixa | Com token vazio (`Bearer `), a API pode retornar 403 em vez de 401 dependendo da versão. Os testes aceitam ambos os códigos de erro. |

---

## 💡 Melhorias Identificadas

| # | Área | Descrição |
|---|------|-----------|
| M01 | Cobertura | Adicionar testes para `PUT /products/{id}` e `DELETE /products/{id}` quando disponíveis. |
| M02 | Performance | Adicionar validações de tempo de resposta para todos os endpoints (atualmente apenas no health check). |
| M03 | Segurança | Adicionar testes de SQL injection e XSS nos campos de texto do produto. |
| M04 | Contratos | Integrar com JSON Schema Validator para validação de contrato completo das respostas. |
| M05 | Paralelismo | Habilitar execução paralela dos cenários (`cucumber.execution.parallel.enabled=true`) após estabilização. |
| M06 | Dados | Usar data providers externos (CSV/Excel) para o `Scenario Outline` de login. |
| M07 | Relatório | Publicar Allure Report automaticamente no GitHub Pages via branch `gh-pages`. |
| M08 | Retry | Implementar mecanismo de retry para flaky tests de rede. |

---

## ⚙️ Pipeline CI/CD (GitHub Actions)

O arquivo `.github/workflows/ci.yml` executa automaticamente:

1. **Trigger**: Push em `main`/`develop` e Pull Requests para `main`.
2. **Passos**:
   - Checkout do código
   - Setup Java 11 (Temurin)
   - `mvn clean test`
   - Upload de artefatos: Cucumber HTML, ExtentReports, Allure Results, Logs
   - Geração do Allure Report
   - Deploy do Allure Report no GitHub Pages (branch `main` apenas)
   - Comentário com resultados em Pull Requests

---

## 🌿 Tags Gherkin

| Tag | Descrição |
|-----|-----------|
| `@smoke` | Testes críticos de happy path |
| `@autenticacao` | Testes de autenticação |
| `@produtos` | Testes de CRUD de produtos |
| `@produtos_autenticados` | Testes de endpoints protegidos |
| `@health_check` | Testes de status da API |
| `@ignore` | Cenários desabilitados temporariamente |

---

## 👤 Autores

Projeto gerado para fins de avaliação técnica. 
API utilizada: [DummyJSON](https://dummyjson.com) — API fake gratuita para prototipagem.
