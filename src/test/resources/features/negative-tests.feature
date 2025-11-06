@negative @regression
Feature: Negative API Testing
  As a tester
  I want to test API error scenarios
  So that I can verify proper error handling

  Background:
    Given I have a valid API endpoint for posts

  @error-handling
  Scenario: Get non-existent post by ID
    When I send a GET request to "/posts/{id}" with post id 99999
    Then the response status code should be 404

  @error-handling
  Scenario: Get non-existent user by ID
    Given I have a valid API endpoint for users
    When I send a GET request to users endpoint "/users/{id}" with user id 99999
    Then the response status code should be 404

  @error-handling
  Scenario: Get post with invalid ID - zero
    When I send a GET request to "/posts/{id}" with post id 0
    Then the response status code should be 404

  @error-handling
  Scenario: Get post with negative ID
    When I send a GET request to "/posts/{id}" with post id -1
    Then the response status code should be 404

  @error-handling
  Scenario: Create post with missing required fields
    Given I have an invalid post with empty title and body for user 1
    When I send a POST request to "/posts" with the post data
    Then the response status code should be 201
    And the created post should have empty title and body

  @error-handling
  Scenario: Create post with invalid user ID
    Given I have a new post with title "Test Post" and body "Test Body" for user 99999
    When I send a POST request to "/posts" with the post data
    Then the response status code should be 201
    And I should receive the created post with id

  @boundary-testing
  Scenario: Get post with maximum valid ID
    When I send a GET request to "/posts/{id}" with post id 100
    Then the response status code should be 200
    And I should receive a post with id 100

  @boundary-testing
  Scenario: Get user with maximum valid ID
    Given I have a valid API endpoint for users
    When I send a GET request to users endpoint "/users/{id}" with user id 10
    Then the response status code should be 200
    And I should receive a user with id 10

  @boundary-testing
  Scenario: Get comments for non-existent post
    When I send a GET request to "/posts/{id}/comments" to get comments for post id 99999
    Then the response status code should be 200
    And I should receive an empty comments list

  @data-validation
  Scenario: Update post with extremely long title
    Given I have an updated post with id 1 and very long title
    When I send a PUT request to "/posts/{id}" with the updated post data
    Then the response status code should be 200
    And the response should contain the long title

  @data-validation
  Scenario: Verify all posts have required fields
    When I send a GET request to "/posts"
    Then the response status code should be 200
    And all posts should have required fields

  @data-validation
  Scenario: Verify all users have valid email format
    Given I have a valid API endpoint for users
    When I send a GET request to users endpoint "/users"
    Then the response status code should be 200
    And all users should have valid email addresses
