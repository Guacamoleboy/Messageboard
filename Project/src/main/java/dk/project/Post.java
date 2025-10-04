// Package
package dk.project;

// Imports
import java.time.LocalDateTime;
import java.util.List;

public class Post {

    // Attributes
    private int id;
    private User author;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private List<Comment> comments;
    private int commentCount;
    private int upvotes;
    private int downvotes;

    // __________________________________________________

    public Post(int id, User author, String title, String description) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
    }

    // __________________________________________________

    public Post(int id, User author, String title, String description, LocalDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
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

    public int getCommentCount() {
        return commentCount;
    }

    // __________________________________________________

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    // __________________________________________________

    public int getUpvotes() {
        return upvotes;
    }

    // __________________________________________________

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    // __________________________________________________

    public int getDownvotes() {
        return downvotes;
    }

    // __________________________________________________

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
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

    public String getTitle() {
        return title;
    }

    // __________________________________________________

    public void setTitle(String title) {
        this.title = title;
    }

    // __________________________________________________

    public String getDescription() {
        return description;
    }

    // __________________________________________________

    public void setDescription(String description) {
        this.description = description;
    }

    // __________________________________________________

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // __________________________________________________

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // __________________________________________________

    public List<Comment> getComments() {
        return comments;
    }

    // __________________________________________________

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

} // Post end