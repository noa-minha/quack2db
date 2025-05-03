package SQLManaging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    private static String url = "jdbc:mysql://localhost:3306/quackstagram_db"; // Replace with your DB detail
    private static String username = "root"; // Your MySQL username
    private static String password = "1234"; // Your MySQL password

    private static DBconnection instance;
    private Connection connection;

    private DBconnection(){
        try {
            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBconnection getInstance() {
        if (instance == null) {
            synchronized (DBconnection.class) {
                if (instance == null) {
                    instance = new DBconnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void terminateConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
}
