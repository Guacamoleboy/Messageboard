// Package
package dk.project.mappers;

// Imports
import java.sql.*;

public class PostVoteMapper {

    // Attributes
    private Connection connection;

    // __________________________________________________

    public PostVoteMapper(Connection connection) {
        this.connection = connection;
    }

    // __________________________________________________

    public int getUpvotes(int postId) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM post_votes WHERE post_id = ? AND vote_type = 'UP'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    // __________________________________________________

    public int getDownvotes(int postId) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM post_votes WHERE post_id = ? AND vote_type = 'DOWN'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    // __________________________________________________

    public String getUserVote(int postId, int userId) throws SQLException {
        String sql = "SELECT vote_type FROM post_votes WHERE post_id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("vote_type");
            }
        }
        return null;
    }

    // __________________________________________________

    public void vote(int postId, int userId, String voteType) throws SQLException {

        String existingVote = getUserVote(postId, userId);
        if (existingVote == null) {

            String sql = "INSERT INTO post_votes (post_id, user_id, vote_type) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, postId);
                stmt.setInt(2, userId);
                stmt.setString(3, voteType);
                stmt.executeUpdate();
            }
        } else if (!existingVote.equals(voteType)) {

            String sql = "UPDATE post_votes SET vote_type = ? WHERE post_id = ? AND user_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, voteType);
                stmt.setInt(2, postId);
                stmt.setInt(3, userId);
                stmt.executeUpdate();
            }
        }

    }

    // __________________________________________________

    public void removeVote(int postId, int userId) throws SQLException {
        String sql = "DELETE FROM post_votes WHERE post_id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

} // PostVote end