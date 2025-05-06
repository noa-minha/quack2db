package SQLManaging.Parsers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface used to translate between parameter objects and SQL statements.
 */
public interface Parser<T> {

    /**
     * Populates a parameter object from a PreparedStatement (e.g., after executing a SELECT query).
     * Note: In practice, you'd usually use a ResultSet here, not a PreparedStatement.
     *
     * @param stmt the PreparedStatement (or ResultSet) containing the query results
     * @return the parameter object created from the row
     */
    public T parseRow(ResultSet resultSet) throws SQLException;

    /**
     * Populates a PreparedStatement with values from a parameter object (e.g., for INSERT/UPDATE).
     *
     * @param stmt the PreparedStatement to populate
     * @param row the object to use for setting parameters
     */
    public void toPreparedStatement(PreparedStatement stmt, T row) throws SQLException;

    // Returns the columns for the INSERT SQL statement (comma-separated)
    String getColumns();

    // Returns the placeholders for the INSERT SQL statement (e.g., "?, ?, ?, ?")
    String getPlaceholders();

    // Returns the column name for the unique identifier for DELETE
    public List<String> getUniqueIdentifierColumns();

    // Sets the unique identifier in the PreparedStatement for DELETE
    void setUniqueIdentifier(PreparedStatement stmt, T item) throws SQLException;

}

