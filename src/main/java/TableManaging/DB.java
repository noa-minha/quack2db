package TableManaging;

import ParameterClasses.Follow;
import ParameterClasses.Notification;
import ParameterClasses.Post;
import ParameterClasses.User;
import TableManaging.Parsers.FollowParser;
import TableManaging.Parsers.NotificationParser;
import TableManaging.Parsers.PostParser;
import TableManaging.Parsers.UserParser;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 

/**
 * Class that holds all tables
 */
public class DB{

    public static final String TABLES_FOLDER = "src/Tables";

    /**
     * Table of User object that takes data from users.txt
     */
    public static final TableTXT<User> USERS = new TableTXT<>(new File(TABLES_FOLDER, "users.txt"), new UserParser());

    /**
     * Table of Post object that takes data from posts.txt
     */
    public static final TableTXT<Post> POSTS = new TableTXT<>(new File(TABLES_FOLDER, "posts.txt"), new PostParser());

    /**
     * Table of Notification object that takes data from notifications.txt
     */
    public static final TableTXT<Notification> NOTIFICATIONS = new TableTXT<>(new File(TABLES_FOLDER, "notifications.txt"), new NotificationParser());

    /**
     * Table of Following object that takes data from following.txt
     */
    public static final TableTXT<Follow> FOLLOWING = new TableTXT<>(new File(TABLES_FOLDER, "following.txt"), new FollowParser());

    /**
     * Table of User object that takes data from current_user.txt
     */
    public static final TableTXT<User> CURR_USER = new TableTXT<>(new File(TABLES_FOLDER, "current_user.txt"), new UserParser());

    public static void main(String[] args) {
        System.out.println("hey hey");
        // MySQL database URL
        String url = "jdbc:mysql://localhost:3306/quackstagram_db"; // Replace with your DB detail
        String username = "root"; // Your MySQL username
        String password = "1234"; // Your MySQL password

        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");
            
            // You can now interact with the DB using this connection
            
            // Close the connection when done
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void query(String sql) {
        System.out.println("Executing: " + sql);
    }

}
