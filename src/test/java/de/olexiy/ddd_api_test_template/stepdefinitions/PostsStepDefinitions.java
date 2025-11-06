package de.olexiy.ddd_api_test_template.stepdefinitions;

import de.olexiy.ddd_api_test_template.models.Comment;
import de.olexiy.ddd_api_test_template.models.Post;
import de.olexiy.ddd_api_test_template.utils.ApiConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Posts API testing.
 * Handles CRUD operations and comments retrieval for posts.
 */
public class PostsStepDefinitions {

    private RequestSpecification request;
    private Post postPayload;
    private final TestContext context;

    /**
     * Constructor for dependency injection via PicoContainer.
     *
     * @param context shared test context for the scenario
     */
    public PostsStepDefinitions(TestContext context) {
        this.context = context;
    }

    @Given("I have a valid API endpoint for posts")
    public void i_have_a_valid_api_endpoint_for_posts() {
        request = given().spec(ApiConfig.getRequestSpec());
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        Response response = request.when().get(endpoint);
        context.setResponse(response);
    }

    @When("I send a GET request to {string} with post id {int}")
    public void i_send_a_get_request_to_with_post_id(String endpoint, Integer postId) {
        String url = ApiConfig.buildEndpoint(endpoint, "{id}", postId);
        Response response = request.when().get(url);
        context.setResponse(response);
    }

    @Then("I should receive a list of posts")
    public void i_should_receive_a_list_of_posts() {
        try {
            List<Post> posts = context.getResponse().jsonPath().getList("", Post.class);
            assertThat(posts).isNotEmpty();
            assertThat(posts.get(0)).isNotNull();
            assertThat(posts.get(0).getId()).isNotNull();
        } catch (Exception e) {
            throw new AssertionError("Failed to deserialize posts list: " + e.getMessage(), e);
        }
    }

    @Then("I should receive a post with id {int}")
    public void i_should_receive_a_post_with_id(Integer expectedId) {
        try {
            Post post = context.getResponse().as(Post.class);
            assertThat(post.getId()).isEqualTo(expectedId);
            assertThat(post.getTitle()).isNotNull();
            assertThat(post.getBody()).isNotNull();
        } catch (Exception e) {
            throw new AssertionError("Failed to deserialize post: " + e.getMessage(), e);
        }
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
        try {
            Post createdPost = context.getResponse().as(Post.class);
            assertThat(createdPost.getId()).isNotNull();
            assertThat(createdPost.getTitle()).isEqualTo(postPayload.getTitle());
            assertThat(createdPost.getBody()).isEqualTo(postPayload.getBody());
            assertThat(createdPost.getUserId()).isEqualTo(postPayload.getUserId());
        } catch (Exception e) {
            throw new AssertionError("Failed to deserialize created post: " + e.getMessage(), e);
        }
    }

    @Given("I have an updated post with id {int}, title {string} and body {string}")
    public void i_have_an_updated_post_with_id_title_and_body(Integer postId, String title, String body) {
        // First, get the existing post to preserve userId
        try {
            RequestSpecification tempRequest = given().spec(ApiConfig.getRequestSpec());
            Response existingPostResponse = tempRequest.get("/posts/" + postId);
            Post existingPost = existingPostResponse.as(Post.class);

            postPayload = new Post();
            postPayload.setId(postId);
            postPayload.setTitle(title);
            postPayload.setBody(body);
            postPayload.setUserId(existingPost.getUserId()); // Preserve original userId
        } catch (Exception e) {
            // Fallback to default userId if post doesn't exist
            postPayload = new Post();
            postPayload.setId(postId);
            postPayload.setTitle(title);
            postPayload.setBody(body);
            postPayload.setUserId(1);
        }
    }

    @When("I send a PUT request to {string} with the updated post data")
    public void i_send_a_put_request_to_with_the_updated_post_data(String endpoint) {
        String url = ApiConfig.buildEndpoint(endpoint, "{id}", postPayload.getId());
        Response response = request
                .body(postPayload)
                .when()
                .put(url);
        context.setResponse(response);
    }

    @Then("I should receive the updated post")
    public void i_should_receive_the_updated_post() {
        try {
            Post updatedPost = context.getResponse().as(Post.class);
            assertThat(updatedPost.getId()).isEqualTo(postPayload.getId());
            assertThat(updatedPost.getTitle()).isEqualTo(postPayload.getTitle());
            assertThat(updatedPost.getBody()).isEqualTo(postPayload.getBody());
        } catch (Exception e) {
            throw new AssertionError("Failed to deserialize updated post: " + e.getMessage(), e);
        }
    }

    @When("I send a DELETE request to {string} with post id {int}")
    public void i_send_a_delete_request_to_with_post_id(String endpoint, Integer postId) {
        String url = ApiConfig.buildEndpoint(endpoint, "{id}", postId);
        Response response = request.when().delete(url);
        context.setResponse(response);
    }

    @Then("the post should be deleted successfully")
    public void the_post_should_be_deleted_successfully() {
        context.getValidatableResponse().statusCode(200);
    }

    @When("I send a GET request to {string} to get comments for post id {int}")
    public void i_send_a_get_request_to_to_get_comments_for_post_id(String endpoint, Integer postId) {
        String url = ApiConfig.buildEndpoint(endpoint, "{id}", postId);
        Response response = request.when().get(url);
        context.setResponse(response);
    }

    @Then("I should receive a list of comments for the post")
    public void i_should_receive_a_list_of_comments_for_the_post() {
        try {
            List<Comment> comments = context.getResponse().jsonPath().getList("", Comment.class);
            assertThat(comments).isNotEmpty();
            Comment firstComment = comments.get(0);
            assertThat(firstComment.getPostId()).isNotNull();
            assertThat(firstComment.getEmail()).isNotNull();
            assertThat(firstComment.getBody()).isNotNull();
        } catch (Exception e) {
            throw new AssertionError("Failed to deserialize comments list: " + e.getMessage(), e);
        }
    }

    @Then("I should receive a list of comments")
    public void i_should_receive_a_list_of_comments() {
        try {
            List<Comment> comments = context.getResponse().jsonPath().getList("", Comment.class);
            assertThat(comments).isNotEmpty();
            Comment firstComment = comments.get(0);
            assertThat(firstComment.getId()).isNotNull();
            assertThat(firstComment.getEmail()).isNotNull();
            assertThat(firstComment.getBody()).isNotNull();
        } catch (Exception e) {
            throw new AssertionError("Failed to deserialize comments list: " + e.getMessage(), e);
        }
    }

    // Negative test step definitions

    @Given("I have an invalid post with empty title and body for user {int}")
    public void i_have_an_invalid_post_with_empty_title_and_body_for_user(Integer userId) {
        postPayload = new Post(userId, "", "");
    }

    @Then("the created post should have empty title and body")
    public void the_created_post_should_have_empty_title_and_body() {
        try {
            Post createdPost = context.getResponse().as(Post.class);
            assertThat(createdPost.getId()).isNotNull();
            assertThat(createdPost.getTitle()).isEmpty();
            assertThat(createdPost.getBody()).isEmpty();
        } catch (Exception e) {
            throw new AssertionError("Failed to validate empty post: " + e.getMessage(), e);
        }
    }

    @Then("I should receive an empty comments list")
    public void i_should_receive_an_empty_comments_list() {
        try {
            List<Comment> comments = context.getResponse().jsonPath().getList("", Comment.class);
            assertThat(comments).isEmpty();
        } catch (Exception e) {
            throw new AssertionError("Failed to deserialize comments list: " + e.getMessage(), e);
        }
    }

    @Given("I have an updated post with id {int} and very long title")
    public void i_have_an_updated_post_with_id_and_very_long_title(Integer postId) {
        String longTitle = "A".repeat(500); // 500 character title
        String body = "Test body for long title validation";

        try {
            RequestSpecification tempRequest = given().spec(ApiConfig.getRequestSpec());
            Response existingPostResponse = tempRequest.get("/posts/" + postId);
            Post existingPost = existingPostResponse.as(Post.class);

            postPayload = new Post();
            postPayload.setId(postId);
            postPayload.setTitle(longTitle);
            postPayload.setBody(body);
            postPayload.setUserId(existingPost.getUserId());
        } catch (Exception e) {
            postPayload = new Post();
            postPayload.setId(postId);
            postPayload.setTitle(longTitle);
            postPayload.setBody(body);
            postPayload.setUserId(1);
        }
    }

    @Then("the response should contain the long title")
    public void the_response_should_contain_the_long_title() {
        try {
            Post updatedPost = context.getResponse().as(Post.class);
            assertThat(updatedPost.getTitle()).isEqualTo(postPayload.getTitle());
            assertThat(updatedPost.getTitle().length()).isGreaterThan(400);
        } catch (Exception e) {
            throw new AssertionError("Failed to validate long title: " + e.getMessage(), e);
        }
    }

    @Then("all posts should have required fields")
    public void all_posts_should_have_required_fields() {
        try {
            List<Post> posts = context.getResponse().jsonPath().getList("", Post.class);
            assertThat(posts).isNotEmpty();

            for (Post post : posts) {
                assertThat(post.getId()).as("Post ID should not be null").isNotNull();
                assertThat(post.getUserId()).as("Post userId should not be null").isNotNull();
                assertThat(post.getTitle()).as("Post title should not be null").isNotNull();
                assertThat(post.getBody()).as("Post body should not be null").isNotNull();
            }
        } catch (Exception e) {
            throw new AssertionError("Failed to validate all posts: " + e.getMessage(), e);
        }
    }
}
