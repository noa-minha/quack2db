package SQLManaging.Parsers;

import java.util.Collections;
import java.util.List;

import javax.swing.plaf.basic.BasicListUI.ListSelectionHandler;

import ParameterClasses.Post;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * A parser for the Parameter Object Post
 * see Parser interface for method comments
 */
public class PostParser implements Parser<Post>{

     
    @Override
    public Post parseRow(ResultSet rs) throws SQLException {
        int postID = rs.getInt("post_id");
        int userID = rs.getInt("user_id");
        String imgPath = rs.getString("image_path");
        String caption = rs.getString("caption");
        Timestamp timestamp = rs.getTimestamp("created_at");
        LocalDateTime createdAt = timestamp.toLocalDateTime();

        Post newPost = new Post(postID, userID, imgPath, caption, createdAt);
        return newPost;
    }

    @Override
    public void toPreparedStatement(PreparedStatement stmt, Post post) throws SQLException {
        stmt.setInt(1, post.getPostID());
        stmt.setInt(2, post.getUserID());
        stmt.setString(3, post.getImgPath());
        stmt.setString(4, post.getCaption());
    }

    @Override
    public String getColumns() {
        return "user_id, image_path, caption";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?";
    }

    @Override
    public List<String> getUniqueIdentifierColumns() {
        return Collections.singletonList("post_id");
    }

    @Override
    public void setUniqueIdentifier(PreparedStatement stmt, Post post) throws SQLException {
        stmt.setInt(1, post.getPostID());
    }

}
