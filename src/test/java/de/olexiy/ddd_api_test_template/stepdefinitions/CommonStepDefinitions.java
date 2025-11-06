package de.olexiy.ddd_api_test_template.stepdefinitions;

import de.olexiy.ddd_api_test_template.utils.ApiConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Common step definitions shared across all feature files.
 * Handles setup, teardown, and common assertion steps.
 */
public class CommonStepDefinitions {

    private final TestContext context;

    /**
     * Constructor for dependency injection via PicoContainer.
     *
     * @param context shared test context for the scenario
     */
    public CommonStepDefinitions(TestContext context) {
        this.context = context;
    }

    /**
     * Setup hook executed before each scenario.
     * Initializes RestAssured configuration.
     */
    @Before
    public void setup() {
        ApiConfig.setup();
    }

    /**
     * Teardown hook executed after each scenario.
     * Cleans up test context to prevent state leakage.
     */
    @After
    public void teardown() {
        context.cleanup();
    }

    /**
     * Validates the HTTP response status code.
     *
     * @param statusCode expected HTTP status code
     */
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        context.getValidatableResponse().statusCode(statusCode);
    }

    /**
     * Validates that a specific header exists in the response.
     *
     * @param headerName the header name to check
     */
    @Then("the response should contain header {string}")
    public void the_response_should_contain_header(String headerName) {
        assertThat(context.getResponse().getHeader(headerName)).isNotNull();
    }

    /**
     * Validates the response content type.
     *
     * @param contentType expected content type
     */
    @Then("the response content type should be {string}")
    public void the_response_content_type_should_be(String contentType) {
        context.getValidatableResponse().contentType(contentType);
    }
}
