package SQLManaging.Parsers;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import ParameterClasses.Notification;

/**
 * A parser for the Notifications
 * see Parser interface for method comments
 */
public class NotificationParser implements Parser<Notification> {
    
    @Override
    public Notification parseRow(ResultSet rs) throws SQLException {
        String username = rs.getString("username");
        int userID = rs.getInt("user_id");
        String action = rs.getString("interaction_type");
  
        Notification n = new Notification(username, userID, action);
        return n;
    }

    @Override
    public void toPreparedStatement(PreparedStatement stmt, Notification n) throws SQLException {
        stmt.setString(1, n.getUsername());
        stmt.setInt(2, n.getUserID());
        stmt.setString(3, n.getAction());
    }

    @Override
    public String getColumns() {
        return "username, user_id, action";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?";
    }

    @Override
    public List<String> getUniqueIdentifierColumns() {
        return Collections.singletonList("user_id");
    }

    @Override
    public void setUniqueIdentifier(PreparedStatement stmt, Notification n) throws SQLException {
        stmt.setString(1, n.getUsername());
        stmt.setInt(2, n.getUserID());
        stmt.setString(3, n.getAction());
    }

}
