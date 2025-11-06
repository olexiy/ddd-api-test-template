package de.olexiy.ddd_api_test_template.stepdefinitions;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class TestContext {
    
    private Response response;
    private ValidatableResponse validatableResponse;
    
    public void setResponse(Response response) {
        this.response = response;
        this.validatableResponse = response.then();
    }
    
    public Response getResponse() {
        return response;
    }
    
    public ValidatableResponse getValidatableResponse() {
        return validatableResponse;
    }
}

