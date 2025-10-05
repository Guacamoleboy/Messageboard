// Package
package dk.project.mappers;

// Imports
import dk.project.CommentVote;
import dk.project.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentVoteMapper {

    // Attributes
    private Connection connection;

    // _______________________________________________________________________

    public CommentVoteMapper(Connection connection) {
        this.connection = connection;
    }

    // _______________________________________________________________________

    public CommentVote getVoteById(int id) throws SQLException {
        String sql = "SELECT * FROM comment_votes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new UserMapper(connection).getUserById(rs.getInt("user_id"));
                return new CommentVote(
                        rs.getInt("id"),
                        rs.getInt("comment_id"),
                        user,
                        rs.getInt("vote"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }
        }
        return null;
    }

    // _______________________________________________________________________

    public List<CommentVote> getVotesForComment(int commentId) throws SQLException {
        List<CommentVote> votes = new ArrayList<>();
        String sql = "SELECT * FROM comment_votes WHERE comment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, commentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new UserMapper(connection).getUserById(rs.getInt("user_id"));
                votes.add(new CommentVote(
                        rs.getInt("id"),
                        rs.getInt("comment_id"),
                        user,
                        rs.getInt("vote"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        }
        return votes;
    }

    // _______________________________________________________________________
    // NOT IN USE - FOR FUTURE PROJECTS

    public void insertVote(CommentVote vote) throws SQLException {
        String sql = "INSERT INTO comment_votes (user_id, comment_id, vote, created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vote.getUser().getId());
            stmt.setInt(2, vote.getCommentId());
            stmt.setInt(3, vote.getVote());
            stmt.setTimestamp(4, Timestamp.valueOf(vote.getCreatedAt()));
            stmt.executeUpdate();
        }
    }

    // _______________________________________________________________________
    // NOT IN USE - FOR FUTURE PROJECTS

    public void updateVote(CommentVote vote) throws SQLException {
        String sql = "UPDATE comment_votes SET vote = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, vote.getVote());
            stmt.setInt(2, vote.getId());
            stmt.executeUpdate();
        }
    }

    // _______________________________________________________________________
    // NOT IN USE - FOR FUTURE PROJECTS

    public void deleteVote(int id) throws SQLException {
        String sql = "DELETE FROM comment_votes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

} // CommentVoteMapper end