Feature: Comments API Testing
  As a tester
  I want to test the Comments API endpoints
  So that I can verify comment data retrieval works correctly

  Background:
    Given I have a valid API endpoint for posts

  Scenario: Get comments for a specific post
    When I send a GET request to "/posts/{id}/comments" to get comments for post id 1
    Then the response status code should be 200
    And the response content type should be "application/json; charset=utf-8"
    And I should receive a list of comments for the post

  Scenario: Get all comments
    When I send a GET request to "/comments"
    Then the response status code should be 200
    And the response content type should be "application/json; charset=utf-8"
    And I should receive a list of comments

