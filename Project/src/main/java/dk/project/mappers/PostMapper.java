// Package
package dk.project.mappers;

// Imports
import dk.project.Post;
import dk.project.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostMapper {

    // Attributes
    private Connection connection;

    // __________________________________________________

    public PostMapper(Connection connection) {
        this.connection = connection;
    }

    // __________________________________________________

    public Post getPostById(int id) throws SQLException {
        String sql = "SELECT * FROM posts WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User author = new UserMapper(connection).getUserById(rs.getInt("user_id"));
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                return new Post(
                        rs.getInt("id"),
                        author,
                        rs.getString("title"),
                        rs.getString("description"),
                        createdAt
                );
            }
        }
        return null;
    }

    // __________________________________________________

    public List<Post> getAllPosts() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts ORDER BY created_at DESC";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User author = new UserMapper(connection).getUserById(rs.getInt("user_id"));
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                posts.add(new Post(
                        rs.getInt("id"),
                        author,
                        rs.getString("title"),
                        rs.getString("description"),
                        createdAt
                ));
            }
        }
        return posts;
    }

    // __________________________________________________

    public int insertPost(Post post) throws SQLException {

        String sql = "INSERT INTO posts (user_id, title, description, created_at) VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, post.getAuthor().getId());
            stmt.setString(2, post.getTitle());
            stmt.setString(3, post.getDescription());
            stmt.setTimestamp(4, Timestamp.valueOf(post.getCreatedAt()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                post.setId(id);
                return id;
            }
        }
        return -1;
    }

    // __________________________________________________

    public List<Post> searchPostsByTitle(String keyword) throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts WHERE title ILIKE ? ORDER BY created_at DESC"; // Ignores caps etc
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%"; // Makes sure our keywoard is being searched for no matter whats infront of behind it
            stmt.setString(1, pattern);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User author = new UserMapper(connection).getUserById(rs.getInt("user_id"));
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                posts.add(new Post(
                        rs.getInt("id"),
                        author, // user_id
                        rs.getString("title"),
                        rs.getString("description"),
                        createdAt
                ));
            }
        }
        return posts;
    }

    // __________________________________________________
    // NOT IN USE - FOR FUTURE PROJECTS

    public void updatePost(Post post) throws SQLException {
        String sql = "UPDATE posts SET title = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getDescription());
            stmt.setInt(3, post.getId());
            stmt.executeUpdate();
        }
    }

    // __________________________________________________
    // NOT IN USE - FOR FUTURE PROJECTS

    public void deletePost(int id) throws SQLException {
        String sql = "DELETE FROM posts WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

} // PostMapper end