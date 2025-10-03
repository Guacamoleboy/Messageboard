// Package
package dk.project;

// Imports
import java.time.LocalDateTime;

public class PostVote {

    // Attributes
    private int id;
    private Post post;
    private User user;
    private int vote;
    private LocalDateTime createdAt;

    // __________________________________________________

    public PostVote(int id, Post post, User user, int vote, LocalDateTime createdAt) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.vote = vote;
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

    public Post getPost() {
        return post;
    }

    // __________________________________________________

    public void setPost(Post post) {
        this.post = post;
    }

    // __________________________________________________

    public User getUser() {
        return user;
    }

    // __________________________________________________

    public void setUser(User user) {
        this.user = user;
    }

    // __________________________________________________

    public int getVote() {
        return vote;
    }

    // __________________________________________________

    public void setVote(int vote) {
        this.vote = vote;
    }

    // __________________________________________________

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // __________________________________________________

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

} // PostVote end