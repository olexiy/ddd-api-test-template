package de.olexiy.ddd_api_test_template.stepdefinitions;

import de.olexiy.ddd_api_test_template.utils.ApiConfig;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonStepDefinitions {
    
    private static final TestContext context = new TestContext();
    
    @Before
    public void setup() {
        ApiConfig.setup();
    }
    
    public static TestContext getContext() {
        return context;
    }
    
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        context.getValidatableResponse().statusCode(statusCode);
    }
    
    @Then("the response should contain header {string}")
    public void the_response_should_contain_header(String headerName) {
        assertThat(context.getResponse().getHeader(headerName)).isNotNull();
    }
    
    @Then("the response content type should be {string}")
    public void the_response_content_type_should_be(String contentType) {
        context.getValidatableResponse().contentType(contentType);
    }
}

