package SQLManaging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import ParameterClasses.*;
import SQLManaging.Parsers.*;

public class DBManager {

    private static String url = "jdbc:mysql://localhost:3306/quackstagram_db"; // Replace with your DB detail
    private static String username = "root"; // Your MySQL username
    private static String password = "1234"; // Your MySQL password

    /**
     * All tables in the schema
     */
    public static TableSQL<User> userTable;
    public static TableSQL<Post> postTable;
    public static TableSQL<Like> likeTable;
    public static TableSQL<Follow> followTable ;
    public static TableSQL<Notification> notificationTable;
    public static TableSQL<Post> exploreTable;
    public static TableSQL<Post> homeTable;

    /**
     * this is a singleton class
     */
    private static DBManager instance;

    /**
     * the connection to the MySQL DB
     */
    private static Connection connection;

    /**
     * creates the connection and the tables
     */
    private DBManager(){
        try {
            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");
            userTable = new TableSQL<>(connection, "users", new UserParser());
            postTable = new TableSQL<>(connection, "posts", new PostParser());
            likeTable = new TableSQL<>(connection, "likes", new LikeParser());
            followTable = new TableSQL<>(connection, "follow", new FollowParser());
            notificationTable = new TableSQL<>(connection, "notifications", new NotificationParser());
            exploreTable = new TableSQL<>(connection, "feed_posts", new PostParser());
            homeTable = new TableSQL<>(connection, "following_posts", new PostParser());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * in case an instance does not yet exist - creates it and returnes the instance, else, returns the instance
     * @return DBManager singleton
     */
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

    /** 
     * @return the connection to the MySQL DB
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Ends the connection
     */
    public void terminateConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs saved functions in the schema
     * @param func - the name of the function, as shown in the schema
     * @return - the int value the function returns
     */
    public static int runFunc(String func) {
        String sql = "SELECT " + func;
    
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);  // Get the first (and only) column in the result
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return -1;
    }
        
}
