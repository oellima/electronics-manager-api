package com.electronics.hooks;

import com.electronics.utils.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cucumber hooks: setup and teardown per scenario.
 */
public class Hooks {

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    private final ScenarioContext context;

    public Hooks(ScenarioContext context) {
        this.context = context;
    }

    @Before
    public void setUp(Scenario scenario) {
        log.info("▶ Iniciando cenario: [{}] {}", scenario.getId(), scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            log.error("✖ Cenario FALHOU: {}", scenario.getName());
            // Attach response body to report if available
            try {
                var response = context.getResponse();
                if (response != null) {
                    String body = response.getBody().asString();
                    scenario.attach(body.getBytes(), "application/json", "Response Body");
                }
            } catch (Exception e) {
                log.warn("Nao foi possivel anexar o response body ao relatorio: {}", e.getMessage());
            }
        } else {
            log.info("✔ Cenario PASSOU: {}", scenario.getName());
        }
        context.clear();
    }
}
