package SQLManaging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import SQLManaging.Parsers.Parser;



public class TableSQL<T> implements Table<T>{

    private final Connection connection;
    private final String tableName;
    private final Parser<T> parser;

    /**
     * Creates a table
     * @param connection - the DB connection (from DBManager)
     * @param tableName - schema name as shown in SQL
     * @param parser - the appropriate parser for the type T of the table
     */
    public TableSQL(Connection connection, String tableName, Parser<T> parser) {
        this.connection = connection;
        this.tableName = tableName;
        this.parser = parser;
    }


    /**
     * Function that applies a WHERE statement
     * @param condition - of SQL form
     * @return a List<T> where T is the type of the table
     */
    public List<T> fetchRows(String condition) {
        List<T> resultList = new ArrayList<>();

        String sql = "SELECT * FROM " + tableName;
        if (condition != null && !condition.isEmpty()) {
            sql += " WHERE " + condition;
        }

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // Using the parser to convert each row into a User object
                resultList.add(parser.parseRow(rs)); 
            }
        } catch (SQLException e) {
            System.out.println("problem with fetch");
        }
        return resultList;
    }

    /**
     * Function that applies an INSERT statement
     */
    public void insert(T item) {
        String sql = "INSERT INTO " + tableName + " (" + parser.getColumns() + ") VALUES (" + parser.getPlaceholders() + ")";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            parser.toPreparedStatement(stmt, item);  // Use the parser to set values in the prepared statement
            stmt.executeUpdate();  // Execute the insert statement
        } catch (SQLException e) {
            System.out.println("problem with insert");
        }

    }

    /**
     * Function that applies an UPDATE statement
     * @param setCLuase = an SQL String that is all that follows the "SET" part of the function
     */
    public void update(String setClause) {
            String sql = "UPDATE " + tableName + " SET " + setClause;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.executeUpdate(); // no parameters to bind
            } catch (SQLException e) {
                System.out.println("problem with update");
            }
    }


    /**
     * Function that applies a DELETE statement
     */
    public void delete(T item) {
        List<String> keys = parser.getUniqueIdentifierColumns();
        String whereClause = keys.stream()
            .map(k -> k + " = ?")
            .collect(Collectors.joining(" AND "));

        String sql = "DELETE FROM " + tableName + " WHERE " + whereClause;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            parser.setUniqueIdentifier(stmt, item);             
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problem with delete");
        }
    }
}