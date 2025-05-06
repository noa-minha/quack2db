package SQLManaging.Parsers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import ParameterClasses.Comment;

public class CommentParser implements Parser<Comment>{
    
    @Override
    public Comment parseRow(ResultSet rs) throws SQLException {
        int commentID = rs.getInt("comment_id");
        int postID = rs.getInt("post_id");
        int userID = rs.getInt("user_id");
        String text = rs.getString("comment_text");

        Comment comment = new Comment(commentID, postID, userID, text);
        return comment;
    }

    @Override
    public void toPreparedStatement(PreparedStatement stmt, Comment comment) throws SQLException {
        stmt.setInt(1, comment.getCommentID());
        stmt.setInt(2, comment.getPostID());
        stmt.setInt(3, comment.getUserID());
        stmt.setString(4, comment.getText());
    }

    @Override
    public String getColumns() {
        return "post_id, user_id, comment_text";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?";
    }

    @Override
    public List<String> getUniqueIdentifierColumns() {
        return Collections.singletonList("comment_id");
    }

    @Override
    public void setUniqueIdentifier(PreparedStatement stmt, Comment comment) throws SQLException {
        stmt.setInt(1, comment.getCommentID());
    }

}
