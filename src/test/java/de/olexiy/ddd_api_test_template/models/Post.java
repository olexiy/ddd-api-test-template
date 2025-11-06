package de.olexiy.ddd_api_test_template.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Model representing a Post resource from the API.
 * Maps to JSONPlaceholder Posts endpoint.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    public Post() {
    }

    public Post(Integer userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) &&
               Objects.equals(userId, post.userId) &&
               Objects.equals(title, post.title) &&
               Objects.equals(body, post.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, body);
    }

    @Override
    public String toString() {
        return "Post{" +
               "id=" + id +
               ", userId=" + userId +
               ", title='" + title + '\'' +
               ", body='" + body + '\'' +
               '}';
    }
}

