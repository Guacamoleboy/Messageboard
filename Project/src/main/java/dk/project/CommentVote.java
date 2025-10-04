package dk.project;

import java.time.LocalDateTime;

public class CommentVote {

    private int id;
    private int commentId;
    private User user;
    private int vote; // +1 = upvote, -1 = downvote
    private LocalDateTime createdAt;

    public CommentVote(int id, int commentId, User user, int vote, LocalDateTime createdAt) {
        this.id = id;
        this.commentId = commentId;
        this.user = user;
        this.vote = vote;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
