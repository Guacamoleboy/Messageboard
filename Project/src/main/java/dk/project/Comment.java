// Package
package dk.project;

// Imports
import java.time.LocalDateTime;

public class Comment {

    // Attributes
    private int id;
    private int postId;
    private User author;
    private String content;
    private LocalDateTime createdAt;

    // __________________________________________________

    public Comment(int id, int postId, User author, String content, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    // __________________________________________________

    public int getId() {
        return id;
    }

    // __________________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // __________________________________________________

    public int getPostId() {
        return postId;
    }

    // __________________________________________________

    public void setPostId(int postId) {
        this.postId = postId;
    }

    // __________________________________________________

    public User getAuthor() {
        return author;
    }

    // __________________________________________________

    public void setAuthor(User author) {
        this.author = author;
    }

    // __________________________________________________

    public String getContent() {
        return content;
    }

    // __________________________________________________

    public void setContent(String content) {
        this.content = content;
    }

    // __________________________________________________

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // __________________________________________________

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

} // Comment end