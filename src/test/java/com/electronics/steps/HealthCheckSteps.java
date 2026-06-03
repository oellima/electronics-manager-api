package com.electronics.steps;

import com.electronics.utils.Constants;
import com.electronics.utils.RequestHelper;
import com.electronics.utils.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.Assert.*;

public class HealthCheckSteps {

    private final ScenarioContext context;

    public HealthCheckSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("o usuario faz uma requisicao GET para o endpoint de health check")
    public void userGetsHealthCheck() {
        Response response = RequestHelper.get(Constants.ENDPOINT_TEST);
        context.setResponse(response);
    }

    @Then("o status da resposta deve ser {int}")
    public void statusCodeShouldBe(int expectedStatus) {
        Response response = context.getResponse();
        assertEquals("Status code incorreto. Body: " + response.getBody().asString(),
                expectedStatus, response.getStatusCode());
    }

    @Then("o campo status da resposta deve ser {string}")
    public void responseStatusFieldShouldBe(String expectedStatus) {
        Response response = context.getResponse();
        String actualStatus = response.jsonPath().getString("status");
        assertEquals("Campo 'status' incorreto", expectedStatus, actualStatus);
    }

    @Then("o campo method da resposta deve ser {string}")
    public void responseMethodFieldShouldBe(String expectedMethod) {
        Response response = context.getResponse();
        String actualMethod = response.jsonPath().getString("method");
        assertEquals("Campo 'method' incorreto", expectedMethod, actualMethod);
    }

    @Then("a resposta deve ser um JSON valido")
    public void responseShouldBeValidJson() {
        Response response = context.getResponse();
        assertNotNull("O corpo da resposta nao deve ser nulo", response.getBody().asString());
        assertFalse("O corpo da resposta nao deve ser vazio", response.getBody().asString().isEmpty());
        response.jsonPath().getMap("$");
    }
}
