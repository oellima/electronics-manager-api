package com.electronics.steps;

import com.electronics.models.LoginRequest;
import com.electronics.utils.Constants;
import com.electronics.utils.RequestHelper;
import com.electronics.utils.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.Assert.*;

public class AuthSteps {

    private final ScenarioContext context;

    public AuthSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("que o usuario possui credenciais validas")
    public void userHasValidCredentials() {
        context.set("username", Constants.VALID_USERNAME);
        context.set("password", Constants.VALID_PASSWORD);
    }

    @Given("que o usuario possui credenciais invalidas")
    public void userHasInvalidCredentials() {
        context.set("username", Constants.INVALID_USERNAME);
        context.set("password", Constants.INVALID_PASSWORD);
    }

    @Given("que o usuario possui username valido mas senha invalida")
    public void userHasValidUsernameButInvalidPassword() {
        context.set("username", Constants.VALID_USERNAME);
        context.set("password", Constants.INVALID_PASSWORD);
    }

    @Given("que o usuario esta autenticado")
    public void userIsAuthenticated() {
        LoginRequest login = new LoginRequest(Constants.VALID_USERNAME, Constants.VALID_PASSWORD);
        Response response = RequestHelper.post(Constants.ENDPOINT_AUTH_LOGIN, login.toMap());
        assertEquals("Falha ao autenticar no setup. Body: " + response.getBody().asString(),
                200, response.getStatusCode());

        // FIX: DummyJSON retorna o token no campo "accessToken", não "token"
        String token = response.jsonPath().getString("accessToken");
        assertNotNull("Token nao pode ser nulo no setup", token);
        context.setToken(token);
    }

    @Given("que o usuario nao esta autenticado")
    public void userIsNotAuthenticated() {
        context.setToken(null);
    }

    @Given("que o usuario possui um token invalido")
    public void userHasInvalidToken() {
        context.setToken("token.invalido.qualquer");
    }

    @When("o usuario faz uma requisicao POST para login com as credenciais")
    public void userPostsLoginWithCredentials() {
        String username = context.get("username");
        String password = context.get("password");
        LoginRequest login = new LoginRequest(username, password);
        Response response = RequestHelper.post(Constants.ENDPOINT_AUTH_LOGIN, login.toMap());
        context.setResponse(response);
    }

    @When("o usuario faz login com username {string} e password {string}")
    public void userLoginsWithSpecificCredentials(String username, String password) {
        LoginRequest login = new LoginRequest(username, password);
        Response response = RequestHelper.post(Constants.ENDPOINT_AUTH_LOGIN, login.toMap());
        context.setResponse(response);
    }

    @When("o usuario faz uma requisicao POST para login sem body")
    public void userPostsLoginWithoutBody() {
        Response response = RequestHelper.post(Constants.ENDPOINT_AUTH_LOGIN, new java.util.HashMap<>());
        context.setResponse(response);
    }

    @Then("a resposta deve conter um token de autenticacao")
    public void responseShouldContainAuthToken() {
        Response response = context.getResponse();

        // FIX: DummyJSON retorna o token no campo "accessToken", não "token"
        String token = response.jsonPath().getString("accessToken");
        assertNotNull("Token nao deve ser nulo", token);
        assertFalse("Token nao deve ser vazio", token.isEmpty());
        context.setToken(token);
    }

    @Then("a resposta deve conter os dados do usuario autenticado")
    public void responseShouldContainUserData() {
        Response response = context.getResponse();
        assertNotNull("Username nao deve ser nulo", response.jsonPath().getString("username"));
        assertNotNull("Email nao deve ser nulo",    response.jsonPath().getString("email"));
        assertNotNull("FirstName nao deve ser nulo",response.jsonPath().getString("firstName"));
    }

    @Then("a resposta deve conter um refreshToken")
    public void responseShouldContainRefreshToken() {
        Response response = context.getResponse();
        String refreshToken = response.jsonPath().getString("refreshToken");
        assertNotNull("RefreshToken nao deve ser nulo", refreshToken);
        assertFalse("RefreshToken nao deve ser vazio", refreshToken.isEmpty());
    }

    @Then("a resposta de erro deve conter a mensagem de credenciais invalidas")
    public void responseErrorShouldContainInvalidCredentialsMessage() {
        Response response = context.getResponse();
        String body = response.getBody().asString();
        assertFalse("O corpo de erro nao deve ser vazio", body.isEmpty());
        assertTrue("Mensagem de erro esperada nao encontrada. Body: " + body,
                body.contains("Invalid") || body.contains("invalid") || body.contains("credentials"));
    }
}