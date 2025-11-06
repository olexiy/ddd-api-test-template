package de.olexiy.ddd_api_test_template.stepdefinitions;

import de.olexiy.ddd_api_test_template.models.Comment;
import de.olexiy.ddd_api_test_template.models.Post;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static de.olexiy.ddd_api_test_template.utils.ApiConfig.getRequestSpec;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PostsStepDefinitions {

    private RequestSpecification request;
    private Post postPayload;
    private final TestContext context;

    // PicoContainer will inject TestContext via constructor
    public PostsStepDefinitions(TestContext context) {
        this.context = context;
    }

    @Given("I have a valid API endpoint for posts")
    public void i_have_a_valid_api_endpoint_for_posts() {
        request = given().spec(getRequestSpec());
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        Response response = request.when().get(endpoint);
        context.setResponse(response);
    }

    @When("I send a GET request to {string} with post id {int}")
    public void i_send_a_get_request_to_with_post_id(String endpoint, Integer postId) {
        Response response = request.when().get(endpoint.replace("{id}", String.valueOf(postId)));
        context.setResponse(response);
    }

    @Then("I should receive a list of posts")
    public void i_should_receive_a_list_of_posts() {
        List<Post> posts = context.getResponse().jsonPath().getList("", Post.class);
        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0)).isNotNull();
    }

    @Then("I should receive a post with id {int}")
    public void i_should_receive_a_post_with_id(Integer expectedId) {
        Post post = context.getResponse().as(Post.class);
        assertThat(post.getId()).isEqualTo(expectedId);
        assertThat(post.getTitle()).isNotNull();
        assertThat(post.getBody()).isNotNull();
    }

    @Given("I have a new post with title {string} and body {string} for user {int}")
    public void i_have_a_new_post_with_title_and_body_for_user(String title, String body, Integer userId) {
        postPayload = new Post(userId, title, body);
    }

    @When("I send a POST request to {string} with the post data")
    public void i_send_a_post_request_to_with_the_post_data(String endpoint) {
        Response response = request
                .body(postPayload)
                .when()
                .post(endpoint);
        context.setResponse(response);
    }

    @Then("I should receive the created post with id")
    public void i_should_receive_the_created_post_with_id() {
        Post createdPost = context.getResponse().as(Post.class);
        assertThat(createdPost.getId()).isNotNull();
        assertThat(createdPost.getTitle()).isEqualTo(postPayload.getTitle());
        assertThat(createdPost.getBody()).isEqualTo(postPayload.getBody());
        assertThat(createdPost.getUserId()).isEqualTo(postPayload.getUserId());
    }

    @Given("I have an updated post with id {int}, title {string} and body {string}")
    public void i_have_an_updated_post_with_id_title_and_body(Integer postId, String title, String body) {
        postPayload = new Post();
        postPayload.setId(postId);
        postPayload.setTitle(title);
        postPayload.setBody(body);
        postPayload.setUserId(1);
    }

    @When("I send a PUT request to {string} with the updated post data")
    public void i_send_a_put_request_to_with_the_updated_post_data(String endpoint) {
        Response response = request
                .body(postPayload)
                .when()
                .put(endpoint.replace("{id}", String.valueOf(postPayload.getId())));
        context.setResponse(response);
    }

    @Then("I should receive the updated post")
    public void i_should_receive_the_updated_post() {
        Post updatedPost = context.getResponse().as(Post.class);
        assertThat(updatedPost.getId()).isEqualTo(postPayload.getId());
        assertThat(updatedPost.getTitle()).isEqualTo(postPayload.getTitle());
        assertThat(updatedPost.getBody()).isEqualTo(postPayload.getBody());
    }

    @When("I send a DELETE request to {string} with post id {int}")
    public void i_send_a_delete_request_to_with_post_id(String endpoint, Integer postId) {
        Response response = request.when().delete(endpoint.replace("{id}", String.valueOf(postId)));
        context.setResponse(response);
    }

    @Then("the post should be deleted successfully")
    public void the_post_should_be_deleted_successfully() {
        context.getValidatableResponse().statusCode(200);
    }

    @When("I send a GET request to {string} to get comments for post id {int}")
    public void i_send_a_get_request_to_to_get_comments_for_post_id(String endpoint, Integer postId) {
        Response response = request.when().get(endpoint.replace("{id}", String.valueOf(postId)));
        context.setResponse(response);
    }

    @Then("I should receive a list of comments for the post")
    public void i_should_receive_a_list_of_comments_for_the_post() {
        List<Comment> comments = context.getResponse().jsonPath().getList("", Comment.class);
        assertThat(comments).isNotEmpty();
        Comment firstComment = comments.get(0);
        assertThat(firstComment.getPostId()).isNotNull();
        assertThat(firstComment.getEmail()).isNotNull();
        assertThat(firstComment.getBody()).isNotNull();
    }

    @Then("I should receive a list of comments")
    public void i_should_receive_a_list_of_comments() {
        List<Comment> comments = context.getResponse().jsonPath().getList("", Comment.class);
        assertThat(comments).isNotEmpty();
        Comment firstComment = comments.get(0);
        assertThat(firstComment.getId()).isNotNull();
        assertThat(firstComment.getEmail()).isNotNull();
        assertThat(firstComment.getBody()).isNotNull();
    }
}
