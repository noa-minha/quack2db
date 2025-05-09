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
     * Populates a parameter object from a PreparedStatement 
     * @param resultSet - the ResultSet containing the query results
     * @return item of type T (some object from parameter classes)
     */
    public T parseRow(ResultSet resultSet) throws SQLException;

    /**
     * Populates a PreparedStatement with values from a parameter object
     * @param stmt the PreparedStatement to populate
     * @param row the object to use for setting parameters
     */
    public void toPreparedStatement(PreparedStatement stmt, T row) throws SQLException;

    /**
     * returns the cols of the table for the insert statement (e.g., "username, bio, passwrd, profile_pic_path")
     * @return
     */
    String getColumns();

    /**
     * returns the placeholders for the insert statement (e.g., "?, ?, ?, ?")
     * @return
     */
    String getPlaceholders();

    /**
     * @return the primary key column(s) name(s)
     */
    public List<String> getUniqueIdentifierColumns();

    /**
     * sets the primary key cols for the PreparedStmt for DELETE
     * @param stmt - the SQL statement with placeholders    
     * @param item - the item to be deleted
     * @throws SQLException
     */
    void setUniqueIdentifier(PreparedStatement stmt, T item) throws SQLException;

}

