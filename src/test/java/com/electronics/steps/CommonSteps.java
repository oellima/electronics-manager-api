package com.electronics.steps;

import com.electronics.utils.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class CommonSteps {

    private static final Logger log = LoggerFactory.getLogger(CommonSteps.class);
    private final ScenarioContext context;

    public CommonSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("a API esta disponivel em {string}")
    public void apiIsAvailableAt(String url) {
        log.info("Configurando base URL: {}", url);
    }

    @Then("o tempo de resposta deve ser aceitavel")
    public void responseTimeShouldBeAcceptable() {
        Response response = context.getResponse();
        long timeMs = response.getTime();
        log.info("Tempo de resposta: {} ms", timeMs);
        assertTrue("Tempo de resposta excedeu 10s: " + timeMs + "ms", timeMs < 10_000);
    }
}
