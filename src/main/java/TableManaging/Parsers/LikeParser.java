package TableManaging.Parsers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ParameterClasses.Like;

public class LikeParser implements Parser<Like>{
    
    @Override
    public Like parseRow(ResultSet rs) throws SQLException {
        int user_id = rs.getInt("user_id");
        int post_id = rs.getInt("post_id");
        Like like = new Like(post_id, user_id);
        return like;
    }

    @Override
    public void toPreparedStatement(PreparedStatement stmt, Like like) throws SQLException {
        stmt.setInt(1, like.getPostID());
        stmt.setInt(2, like.getUserID());
    }

    @Override
    public String getColumns() {
        return "post_id, user_id";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?";
    }

    @Override
    public List<String> getUniqueIdentifierColumns() {
        return List.of("post_id", "user_id");
    }

    @Override
    public void setUniqueIdentifier(PreparedStatement stmt, Like like) throws SQLException {
        stmt.setInt(1, like.getPostID());
        stmt.setInt(2, like.getUserID());
    }

}
