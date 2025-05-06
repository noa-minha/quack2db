package SQLManaging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ParameterClasses.User;
import TableManaging.Parsers.Parser;
import TableManaging.Parsers.UserParser;

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

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                resultList.add(parser.parseRow(rs));  // Using the parser to convert each row into a User object
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

    }

    public void update(String setClause, String whereClause) {
            String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE " + whereClause;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.executeUpdate(); // no parameters to bind
            } catch (SQLException e) {
                e.printStackTrace(); // or handle more gracefully
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
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection conn = DBconnection.getInstance().getConnection();
        TableSQL<User> userTable = new TableSQL<>(conn, "users", new UserParser());

        // User newUser = new User(0, "alice", "secure123", "Loves cats", "img/users/alice.png");
        // userTable.insert(newUser);

        List<User> foundUsers = userTable.fetchRows("username = 'alice'");
        for (User u : foundUsers) {
            System.out.println("Found: " + u.getUsername());
        }

        userTable.delete(foundUsers.get(0));

    }
}