package de.olexiy.ddd_api_test_template.utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Configuration class for API testing with RestAssured.
 * Provides centralized configuration for base URL, timeouts, and request specifications.
 */
public class ApiConfig {

    // API Configuration Constants
    private static final String BASE_URL = System.getProperty("api.base.url",
        "https://jsonplaceholder.typicode.com");

    // Timeout Constants (in milliseconds)
    private static final int CONNECTION_TIMEOUT = 10000; // 10 seconds
    private static final int SOCKET_TIMEOUT = 10000; // 10 seconds

    // Content Type Constants
    public static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * Sets up global RestAssured configuration.
     * Configures base URI, logging, and timeout settings.
     */
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // Configure HTTP client with timeout settings
        RestAssured.config = RestAssuredConfig.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", CONNECTION_TIMEOUT)
                .setParam("http.socket.timeout", SOCKET_TIMEOUT));
    }

    /**
     * Creates a request specification with standard configuration.
     * Includes base URI, content type, and timeout settings.
     *
     * @return configured RequestSpecification
     */
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setConfig(RestAssuredConfig.config()
                    .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", CONNECTION_TIMEOUT)
                        .setParam("http.socket.timeout", SOCKET_TIMEOUT)))
                .build();
    }

    /**
     * Gets the base URL for API testing.
     * Can be overridden via system property 'api.base.url'.
     *
     * @return the base URL
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * Builds an endpoint URL by replacing path parameters.
     *
     * @param template endpoint template with placeholders (e.g., "/posts/{id}")
     * @param placeholder the placeholder to replace (e.g., "{id}")
     * @param value the value to substitute
     * @return the constructed endpoint
     */
    public static String buildEndpoint(String template, String placeholder, Object value) {
        return template.replace(placeholder, String.valueOf(value));
    }
}

