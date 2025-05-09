package SQLManaging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ParameterClasses.*;
import SQLManaging.Parsers.FollowParser;
import SQLManaging.Parsers.Parser;
import SQLManaging.Parsers.PostParser;

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
            System.out.println(stmt);
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
        System.out.println(sql);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            parser.setUniqueIdentifier(stmt, item); 
            System.out.println(stmt);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problem with delete");
        }
    }

    public static void main(String[] args) {
        Connection conn = DBManager.init().getConnection();
        Table<Follow> followTable = new TableSQL<>(conn, "follow", new FollowParser());

        Follow follow = new Follow(18, 30);
        followTable.delete(follow);

        // List<Post> n = postsTable.fetchRows(null);
        // for (Post p : n) {
        //     System.out.println("Found: " + n.toString());
        // }

        // List<Follow> following = DBManager.followTable.fetchRows("follower_id= " + 18 + " AND following_id= " + 10);

        // if (!following.isEmpty()) {
        //     System.out.println("is following");
        // }
        // else{
        //     System.out.println("not following");
        // }
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