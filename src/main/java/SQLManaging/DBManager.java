package SQLManaging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ParameterClasses.Post;
import ParameterClasses.User;
import SQLManaging.Parsers.PostParser;
import SQLManaging.Parsers.UserParser;

public class DBManager {

    private static String url = "jdbc:mysql://localhost:3306/quackstagram_db"; // Replace with your DB detail
    private static String username = "root"; // Your MySQL username
    private static String password = "1234"; // Your MySQL password

    public static TableSQL<User> userTable;
    public static TableSQL<Post> postTable;


    private static DBManager instance;
    private Connection connection;

    private DBManager(){
        try {
            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");
            userTable = new TableSQL<>(connection, "users", new UserParser());
            postTable = new TableSQL<>(connection, "posts", new PostParser());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBManager init() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
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
