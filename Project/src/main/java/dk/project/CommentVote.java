// Package
package dk.project;

// Imports
import java.time.LocalDateTime;

public class CommentVote {

    // Attributes
    private int id;
    private int commentId;
    private User user;
    private int voteType; // +1 = upvote, -1 = downvote
    private LocalDateTime createdAt;

    // __________________________________________________

    public CommentVote(int id, int commentId, User user, int voteType, LocalDateTime createdAt) {
        this.id = id;
        this.commentId = commentId;
        this.user = user;
        this.voteType = voteType;
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

    public int getCommentId() {
        return commentId;
    }

    // __________________________________________________

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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

    public int getVoteType() {
        return voteType;
    }

    // __________________________________________________

    public void setVoteType(int voteType) {
        this.voteType = voteType;
    }

    // __________________________________________________

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // __________________________________________________

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

} // CommentVote end