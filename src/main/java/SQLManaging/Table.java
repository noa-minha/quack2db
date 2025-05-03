package SQLManaging;

import java.util.List;

public interface Table<T> {

    public void insert(T item);
    // public void update(T item);
    public void delete(T item);
    public List<T> fetchRows(String clause);

}
