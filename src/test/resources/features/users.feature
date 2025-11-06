Feature: Users API Testing
  As a tester
  I want to test the Users API endpoints
  So that I can verify user data retrieval works correctly

  Background:
    Given I have a valid API endpoint for users

  Scenario: Get all users
    When I send a GET request to users endpoint "/users"
    Then the response status code should be 200
    And the response content type should be "application/json; charset=utf-8"
    And I should receive a list of users

  Scenario: Get user by ID
    When I send a GET request to users endpoint "/users/{id}" with user id 1
    Then the response status code should be 200
    And the response content type should be "application/json; charset=utf-8"
    And I should receive a user with id 1
    And the user should have valid data structure

  Scenario: Validate user data structure
    When I send a GET request to users endpoint "/users/{id}" with user id 2
    Then the response status code should be 200
    And the user should have valid data structure

