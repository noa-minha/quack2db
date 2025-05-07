package SQLManaging.Parsers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import ParameterClasses.User;

/**
 * A parser for the Parameter Object User
 * see Parser interface for method comments
 */
public class UserParser implements Parser<User>{
    
    @Override
    public User parseRow(ResultSet rs) throws SQLException {
        int user_id = rs.getInt("user_id");
        String username = rs.getString("username");
        String password = rs.getString("passwrd");
        String bio = rs.getString("bio");
        String profilePicPath = rs.getString("profile_pic_path");
        Boolean currUser = rs.getBoolean("curr_user");
        User user = new User(user_id, username, password, bio, profilePicPath, currUser);
        return user;
    }

    @Override
    public void toPreparedStatement(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getBio());
        stmt.setString(4, user.getProfilePicPath());
        System.out.println(stmt);
    }

    @Override
    public String getColumns() {
        return "username, bio, passwrd, profile_pic_path";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?, ?";
    }

    @Override
    public List<String> getUniqueIdentifierColumns() {
        return Collections.singletonList("user_id");
    }

    @Override
    public void setUniqueIdentifier(PreparedStatement stmt, User user) throws SQLException {
        stmt.setInt(1, user.getUserID());
    }
}


