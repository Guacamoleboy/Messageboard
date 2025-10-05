// Package
package dk.project;

// Imports
import java.time.LocalDateTime;

public class CommentVote {

    // Attributes
    private int id;
    private int commentId;
    private User user;
    private int vote;
    private LocalDateTime createdAt;

    // _______________________________________________________________

    public CommentVote(int id, int commentId, User user, int vote, LocalDateTime createdAt) {
        this.id = id;
        this.commentId = commentId;
        this.user = user;
        this.vote = vote;
        this.createdAt = createdAt;
    }

    // _______________________________________________________________

    public int getId() {
        return id;
    }

    // _______________________________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // _______________________________________________________________

    public int getCommentId() {
        return commentId;
    }

    // _______________________________________________________________

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    // _______________________________________________________________

    public User getUser() {
        return user;
    }

    // _______________________________________________________________

    public void setUser(User user) {
        this.user = user;
    }

    // _______________________________________________________________

    public int getVote() {
        return vote;
    }

    // _______________________________________________________________

    public void setVote(int vote) {
        this.vote = vote;
    }

    // _______________________________________________________________

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // _______________________________________________________________

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

} // CommentVote end