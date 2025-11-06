package de.olexiy.ddd_api_test_template.stepdefinitions;

import de.olexiy.ddd_api_test_template.models.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static de.olexiy.ddd_api_test_template.utils.ApiConfig.getRequestSpec;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UsersStepDefinitions {
    
    private RequestSpecification request;
    private final TestContext context = CommonStepDefinitions.getContext();
    
    @Given("I have a valid API endpoint for users")
    public void i_have_a_valid_api_endpoint_for_users() {
        request = given().spec(getRequestSpec());
    }
    
    @When("I send a GET request to users endpoint {string}")
    public void i_send_a_get_request_to_users_endpoint(String endpoint) {
        Response response = request.when().get(endpoint);
        context.setResponse(response);
    }
    
    @When("I send a GET request to users endpoint {string} with user id {int}")
    public void i_send_a_get_request_to_users_endpoint_with_user_id(String endpoint, Integer userId) {
        Response response = request.when().get(endpoint.replace("{id}", String.valueOf(userId)));
        context.setResponse(response);
    }
    
    @Then("I should receive a list of users")
    public void i_should_receive_a_list_of_users() {
        List<User> users = context.getResponse().jsonPath().getList("", User.class);
        assertThat(users).isNotEmpty();
        User firstUser = users.get(0);
        assertThat(firstUser.getId()).isNotNull();
        assertThat(firstUser.getName()).isNotNull();
        assertThat(firstUser.getEmail()).isNotNull();
    }
    
    @Then("I should receive a user with id {int}")
    public void i_should_receive_a_user_with_id(Integer expectedId) {
        User user = context.getResponse().as(User.class);
        assertThat(user.getId()).isEqualTo(expectedId);
        assertThat(user.getName()).isNotNull();
        assertThat(user.getUsername()).isNotNull();
        assertThat(user.getEmail()).isNotNull();
    }
    
    @Then("the user should have valid data structure")
    public void the_user_should_have_valid_data_structure() {
        User user = context.getResponse().as(User.class);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isNotEmpty();
        assertThat(user.getUsername()).isNotEmpty();
        assertThat(user.getEmail()).matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}

