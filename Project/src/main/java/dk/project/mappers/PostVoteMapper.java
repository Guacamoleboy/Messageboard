// Package
package dk.project.mappers;

// Imports
import java.sql.*;

public class PostVoteMapper {

    // Attributes
    private Connection connection;

    // _________________________________________________________________

    public PostVoteMapper(Connection connection) {
        this.connection = connection;
    }

    // _________________________________________________________________

    public int getUpvotes(int postId) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM post_votes WHERE post_id = ? AND vote = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    // _________________________________________________________________

    public int getDownvotes(int postId) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM post_votes WHERE post_id = ? AND vote = -1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    // _________________________________________________________________

    public Integer getUserVote(int postId, int userId) throws SQLException {
        String sql = "SELECT vote FROM post_votes WHERE post_id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("vote"); // 1 eller -1
            }
        }
        return null;
    }

    // _________________________________________________________________

    public void vote(int postId, int userId, int vote) throws SQLException {
        Integer existingVote = getUserVote(postId, userId);
        if (existingVote == null) {
            String sql = "INSERT INTO post_votes (post_id, user_id, vote) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.setInt(2, userId);
                stmt.setInt(3, vote);
                stmt.executeUpdate();
            }
        } else if (existingVote != vote) {
            String sql = "UPDATE post_votes SET vote = ? WHERE post_id = ? AND user_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, vote);
                stmt.setInt(2, postId);
                stmt.setInt(3, userId);
                stmt.executeUpdate();
            }
        }
    }

    // _________________________________________________________________

    public void removeVote(int postId, int userId) throws SQLException {
        String sql = "DELETE FROM post_votes WHERE post_id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

} // PostVoteMapper end