package SQLManaging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ParameterClasses.Notification;
import SQLManaging.Parsers.NotificationParser;
import SQLManaging.Parsers.Parser;

// TODO : delete main
// TODO : make these reaise exception and handle it elsewhere

public class TableSQL<T> implements Table<T>{
    private final Connection connection;
    private final String tableName;
    private final Parser<T> parser;

    public TableSQL(Connection connection, String tableName, Parser<T> parser) {
        this.connection = connection;
        this.tableName = tableName;
        this.parser = parser;
    }

    // Fetch rows based on a WHERE condition (string-based)
    public List<T> fetchRows(String condition) {
        List<T> resultList = new ArrayList<>();

        String sql = "SELECT * FROM " + tableName;
        if (condition != null && !condition.isEmpty()) {
            sql += " WHERE " + condition;
        }

        System.out.println(sql);
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                resultList.add(parser.parseRow(rs));  // Using the parser to convert each row into a User object
            }
        } catch (SQLException e) {
            System.out.println("problem with fetch");
        }
        return resultList;
    }

    // Insert a new row (object T) into the table
    public void insert(T item) {
        String sql = "INSERT INTO " + tableName + " (" + parser.getColumns() + ") VALUES (" + parser.getPlaceholders() + ")";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            parser.toPreparedStatement(stmt, item);  // Use the parser to set values in the prepared statement
            stmt.executeUpdate();  // Execute the insert statement
        } catch (SQLException e) {
            System.out.println("problem with insert");
        }

    }

    public void update(String setClause) {
            String sql = "UPDATE " + tableName + " SET " + setClause;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.executeUpdate(); // no parameters to bind
            } catch (SQLException e) {
                System.out.println("problem with update");
            }
    }

    // Delete rows based on a WHERE condition
    public void delete(T item) {
        List<String> keys = parser.getUniqueIdentifierColumns();
        String whereClause = keys.stream()
            .map(k -> k + " = ?")
            .collect(Collectors.joining(" AND "));

        String sql = "DELETE FROM " + tableName + " WHERE " + whereClause;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            parser.setUniqueIdentifier(stmt, item); // You define this
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problem with delete");
        }
    }

    public static void main(String[] args) {
        Connection conn = DBManager.init().getConnection();
        Table<Notification> notificationTable = new TableSQL<>(conn, "notifications", new NotificationParser());

        List<Notification> n = notificationTable.fetchRows(null);
        for (Notification noti : n) {
            System.out.println("Found: " + n.toString());
        }
    //     TableSQL<User> userTable = new TableSQL<>(conn, "users", new UserParser());

    //     // User newUser = new User(0, "alice", "secure123", "Loves cats", "img/users/alice.png");
    //     // userTable.insert(newUser);

    //     List<User> foundUsers = userTable.fetchRows("username = 'alice'");
    //     for (User u : foundUsers) {
    //         System.out.println("Found: " + u.getUsername());
    //     }

    //     userTable.delete(foundUsers.get(0));

    }
}