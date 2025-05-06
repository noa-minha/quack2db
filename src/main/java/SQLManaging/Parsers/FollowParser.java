package SQLManaging.Parsers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ParameterClasses.Follow;

/**
 * A parser for the Parameter Object Follow
 * see Parser interface for method comments
 */
public class FollowParser implements Parser<Follow>{
    
    @Override
    public Follow parseRow(ResultSet rs) throws SQLException {
        int followerID = rs.getInt("follower_id");
        int followeeID = rs.getInt("following_id");
  
        Follow f = new Follow(followerID, followeeID);
        return f;
    }

    @Override
    public void toPreparedStatement(PreparedStatement stmt, Follow follow) throws SQLException {
        stmt.setInt(1, follow.getFollowerID());
        stmt.setInt(2, follow.getFolloweeID());
    }

    @Override
    public String getColumns() {
        return "follower_id, following_id";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?";
    }

    @Override
    public List<String> getUniqueIdentifierColumns() {
        return List.of("follower_id", "followee_id");
    }

    @Override
    public void setUniqueIdentifier(PreparedStatement stmt, Follow follow) throws SQLException {
        stmt.setInt(1, follow.getFollowerID());
        stmt.setInt(2, follow.getFolloweeID());
    }
}