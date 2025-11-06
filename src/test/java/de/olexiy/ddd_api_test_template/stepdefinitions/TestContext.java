package de.olexiy.ddd_api_test_template.stepdefinitions;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

/**
 * Test context for sharing state between Cucumber step definitions.
 * Managed by PicoContainer for dependency injection.
 * New instance is created for each scenario to ensure test isolation.
 */
public class TestContext {

    private Response response;
    private ValidatableResponse validatableResponse;

    /**
     * Sets the API response and creates a validatable response wrapper.
     *
     * @param response the RestAssured Response object from API call
     */
    public void setResponse(Response response) {
        this.response = response;
        this.validatableResponse = response.then();
    }

    /**
     * Gets the stored API response.
     *
     * @return the Response object
     * @throws IllegalStateException if response hasn't been set yet
     */
    public Response getResponse() {
        if (response == null) {
            throw new IllegalStateException(
                "Response has not been set. Ensure an API request was made before accessing the response.");
        }
        return response;
    }

    /**
     * Gets the validatable response for assertions.
     *
     * @return the ValidatableResponse object
     * @throws IllegalStateException if response hasn't been set yet
     */
    public ValidatableResponse getValidatableResponse() {
        if (validatableResponse == null) {
            throw new IllegalStateException(
                "Response has not been set. Ensure an API request was made before accessing the response.");
        }
        return validatableResponse;
    }

    /**
     * Cleans up the context by clearing stored responses.
     * Called after each scenario to prevent state leakage.
     */
    public void cleanup() {
        this.response = null;
        this.validatableResponse = null;
    }
}

