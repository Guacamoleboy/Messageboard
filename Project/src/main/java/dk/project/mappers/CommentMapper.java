// Package
package dk.project.mappers;

// Imports
import dk.project.Comment;
import dk.project.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    // Attributes
    private Connection connection;

    // __________________________________________________

    public CommentMapper(Connection connection) {
        this.connection = connection;
    }

    // __________________________________________________

    public Comment getCommentById(int id) throws SQLException {
        String sql = "SELECT * FROM comments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User author = new UserMapper(connection).getUserById(rs.getInt("user_id"));
                return new Comment(
                        rs.getInt("id"),
                        rs.getInt("post_id"),
                        author,
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }
        }
        return null;
    }

    // __________________________________________________

    public List<Comment> getCommentsForPost(int postId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE post_id = ? ORDER BY created_at ASC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User author = new UserMapper(connection).getUserById(rs.getInt("user_id"));
                comments.add(new Comment(
                        rs.getInt("id"),
                        rs.getInt("post_id"),
                        author,
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        }
        return comments;
    }

    // __________________________________________________

    public void insertComment(Comment comment) throws SQLException {
        String sql = "INSERT INTO comments (user_id, post_id, content, created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, comment.getAuthor().getId());
            stmt.setInt(2, comment.getPostId());
            stmt.setString(3, comment.getContent());
            stmt.setTimestamp(4, Timestamp.valueOf(comment.getCreatedAt()));
            stmt.executeUpdate();
        }
    }

    // __________________________________________________

    public void updateComment(Comment comment) throws SQLException {
        String sql = "UPDATE comments SET content = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, comment.getContent());
            stmt.setInt(2, comment.getId());
            stmt.executeUpdate();
        }
    }

    // __________________________________________________

    public void deleteComment(int id) throws SQLException {
        String sql = "DELETE FROM comments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

} // CommentMapper end