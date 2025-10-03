// Package
package dk.project;

// Imports
import java.time.LocalDateTime;

public class User {

    // Attributes
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;

    // __________________________________________________

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // __________________________________________________

    public User(int id, String username, String email, String passwordHash, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
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

    public String getUsername() {
        return username;
    }

    // __________________________________________________

    public void setUsername(String username) {
        this.username = username;
    }

    // __________________________________________________

    public String getEmail() {
        return email;
    }

    // __________________________________________________

    public void setEmail(String email) {
        this.email = email;
    }

    // __________________________________________________

    public String getPasswordHash() {
        return passwordHash;
    }

    // __________________________________________________

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // __________________________________________________

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // __________________________________________________

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

} // User end