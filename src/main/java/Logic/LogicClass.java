package Logic;

import java.util.List;

import ParameterClasses.User;
import SQLManaging.DBManager;

/**
 * Class to group together logic-handling classes and implement most used methods in those classes
 */
public abstract class LogicClass {
    


    
    /**
     * Fetches the current user from DB.CURR_USER 
     * @return the User object of the current user
     */
    public static User getCurrUser(){
        List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);
        return user.get(0);
    }

    /**
     * Recieves a user and returns whether it is not null
     * @param user
     * @return false if the user is null, true otherwise
     */
    public static boolean userExists(User user) {
        return user != null;
    }


    /**
     * Saves the given user as the current user
     */
    public static void saveCurrUserInformation(User user) {
        DB.CURR_USER.insertRow(user, false);
    }
}
