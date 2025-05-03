package SQLManaging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import TableManaging.Parsers.Parser;

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

    // Update rows (based on condition) with new data
    // public void update(T newItem) {
    //     String sql = "UPDATE " + tableName + " SET " + parser.getUpdateValues(newData);
    //     if (whereCondition != null && !whereCondition.isEmpty()) {
    //         sql += " WHERE " + whereCondition;
    //     }
    //     try (Statement stmt = connection.createStatement()) {
    //         stmt.executeUpdate(sql);
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

    // Delete rows based on a WHERE condition
    public void delete(T item) {
        String sql = "DELETE FROM " + tableName + " WHERE " + parser.getUniqueIdentifierColumn() + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            parser.setUniqueIdentifier(stmt, item);  // Use the parser to set the unique identifier for deletion
            stmt.executeUpdate();  // Execute the delete statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}