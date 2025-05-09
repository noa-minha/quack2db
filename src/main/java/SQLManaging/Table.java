package SQLManaging;

import java.util.List;

public interface Table<T> {

    /**
     * inserts an item
     * @param item - of type T (some object from parameter classes)
     */
    public void insert(T item);

    /**
     * updates an existing item in a table
     * @param setClause - which atributes to update and where
     */
    public void update(String setClause);

    /**
     * deletes an item
     * @param item - of type T (some object from parameter classes)
     */
    public void delete(T item);

    /**
     * selects rows from a table that match the clause
     * @param clause
     * @return a list of rows that matched the clause, if none matched then returns en empty list
     */
    public List<T> fetchRows(String clause);

}
