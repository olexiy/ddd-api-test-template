@posts @regression
Feature: Posts API Testing
  As a tester
  I want to test the Posts API endpoints
  So that I can verify CRUD operations work correctly

  Background:
    Given I have a valid API endpoint for posts

  @smoke @get
  Scenario: Get all posts
    When I send a GET request to "/posts"
    Then the response status code should be 200
    And the response content type should be "application/json; charset=utf-8"
    And I should receive a list of posts

  @smoke @get
  Scenario: Get post by ID
    When I send a GET request to "/posts/{id}" with post id 1
    Then the response status code should be 200
    And the response content type should be "application/json; charset=utf-8"
    And I should receive a post with id 1

  @post @crud
  Scenario: Create a new post
    Given I have a new post with title "Test Post Title" and body "Test Post Body" for user 1
    When I send a POST request to "/posts" with the post data
    Then the response status code should be 201
    And the response content type should be "application/json; charset=utf-8"
    And I should receive the created post with id

  @put @crud
  Scenario: Update an existing post
    Given I have an updated post with id 1, title "Updated Post Title" and body "Updated Post Body"
    When I send a PUT request to "/posts/{id}" with the updated post data
    Then the response status code should be 200
    And the response content type should be "application/json; charset=utf-8"
    And I should receive the updated post

  @delete @crud
  Scenario: Delete a post
    When I send a DELETE request to "/posts/{id}" with post id 1
    Then the response status code should be 200
    And the post should be deleted successfully

  @get @comments
  Scenario: Get comments for a post
    When I send a GET request to "/posts/{id}/comments" to get comments for post id 1
    Then the response status code should be 200
    And the response content type should be "application/json; charset=utf-8"
    And I should receive a list of comments for the post

