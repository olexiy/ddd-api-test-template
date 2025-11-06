# DDD API Test Template

A lightweight API testing framework using **Cucumber (BDD)**, **RestAssured**, **PicoContainer**, and **JUnit5**.

## Overview

This project demonstrates how to test REST APIs using:
- **Cucumber** for Behavior-Driven Development (BDD) test scenarios
- **RestAssured** for API testing
- **PicoContainer** for dependency injection (thread-safe scenario context)
- **JUnit5** as the test runner
- **JSONPlaceholder API** as the test target (https://jsonplaceholder.typicode.com)

## Features

- ✅ BDD-style test scenarios written in Gherkin
- ✅ Comprehensive API testing (GET, POST, PUT, DELETE)
- ✅ Thread-safe test context with PicoContainer DI
- ✅ Lightweight - no Spring framework overhead
- ✅ HTML test reports with Cucumber
- ✅ GitHub Actions workflow for automated testing
- ✅ GitHub Pages integration for test report publishing

## Project Structure

```
src/test/
├── java/de/olexiy/ddd_api_test_template/
│   ├── runner/
│   │   └── CucumberTestRunner.java          # JUnit5 test runner
│   ├── stepdefinitions/
│   │   ├── CommonStepDefinitions.java       # Common step definitions
│   │   ├── PostsStepDefinitions.java        # Posts API step definitions
│   │   └── UsersStepDefinitions.java         # Users API step definitions
│   ├── models/
│   │   ├── Post.java                         # Post entity model
│   │   ├── User.java                         # User entity model
│   │   └── Comment.java                      # Comment entity model
│   └── utils/
│       └── ApiConfig.java                    # API configuration
└── resources/
    └── features/
        ├── posts.feature                     # Posts API scenarios
        ├── users.feature                     # Users API scenarios
        └── comments.feature                  # Comments API scenarios
```

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- Internet connection (for API calls)

## Running Tests Locally

### Run all tests
```bash
mvn clean test
```

### Run tests and generate reports
```bash
mvn clean test verify
```

### Run specific feature
```bash
mvn test -Dcucumber.filter.tags="@posts"
```

## Test Reports

After running tests, reports are generated in:
- **HTML Report**: `target/cucumber-reports/cucumber.html`
- **JSON Report**: `target/cucumber-reports/cucumber.json`
- **JUnit XML**: `target/cucumber-reports/cucumber.xml`

## GitHub Actions

The project includes a GitHub Actions workflow (`.github/workflows/api-tests.yml`) that:

1. **Manual Trigger**: Can be started manually from the Actions tab
2. **Runs Tests**: Executes all Cucumber tests
3. **Generates Reports**: Creates HTML test reports
4. **Publishes to GitHub Pages**: Automatically publishes reports to GitHub Pages

### How to Use GitHub Actions

1. Go to the **Actions** tab in your GitHub repository
2. Select **API Tests with Cucumber** workflow
3. Click **Run workflow**
4. Select the branch and click **Run workflow**
5. After completion, view reports at: `https://<username>.github.io/ddd-api-test-template/reports/cucumber.html`

### Configure GitHub Pages (First Time Setup)

To view test reports online, you need to enable GitHub Pages:

1. Go to your repository **Settings**
2. Navigate to **Pages** (under "Code and automation")
3. Under **Source**, select **Deploy from a branch**
4. Under **Branch**, select:
   - Branch: `gh-pages`
   - Folder: `/ (root)`
5. Click **Save**
6. Wait 2-5 minutes for GitHub to deploy your site
7. Reports will be available at: `https://<username>.github.io/ddd-api-test-template/reports/cucumber.html`

**Note**: The workflow automatically creates and updates the `gh-pages` branch with test reports. You only need to configure GitHub Pages settings once.

## Test Scenarios

### Posts API
- Get all posts
- Get post by ID
- Create new post
- Update existing post
- Delete post
- Get comments for a post

### Users API
- Get all users
- Get user by ID
- Validate user data structure

### Comments API
- Get comments for a specific post
- Get all comments

## Technologies Used

- **Java 21**
- **Cucumber 7.18.0** (BDD framework)
- **PicoContainer 7.18.0** (lightweight DI for Cucumber)
- **RestAssured 5.3.2** (API testing)
- **JUnit5 Platform 6.0.0** (test runner)
- **AssertJ 3.25.3** (fluent assertions)
- **Jackson 2.17.0** (JSON processing)
- **Maven 3.9+** (build tool)

## API Under Test

This project tests the **JSONPlaceholder API** - a free fake REST API for testing:
- Base URL: `https://jsonplaceholder.typicode.com`
- No authentication required
- Supports CRUD operations

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests to ensure everything works
5. Submit a pull request

## License

This project is open source and available for educational purposes.

