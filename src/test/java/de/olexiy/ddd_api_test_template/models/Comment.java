package de.olexiy.ddd_api_test_template.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Model representing a Comment resource from the API.
 * Maps to JSONPlaceholder Comments endpoint.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("postId")
    private Integer postId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("body")
    private String body;

    public Comment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
               Objects.equals(postId, comment.postId) &&
               Objects.equals(name, comment.name) &&
               Objects.equals(email, comment.email) &&
               Objects.equals(body, comment.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postId, name, email, body);
    }

    @Override
    public String toString() {
        return "Comment{" +
               "id=" + id +
               ", postId=" + postId +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", body='" + body + '\'' +
               '}';
    }
}
