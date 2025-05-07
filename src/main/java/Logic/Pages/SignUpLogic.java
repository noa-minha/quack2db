package Logic.Pages;

import ParameterClasses.User;
import SQLManaging.DBManager;

import java.util.List;

/**
 * Class that handles the logic of SignUpUI
 */
public class SignUpLogic {

    /**
     * Signs up the new user
     * @param newUser
     * @throws Exception if username already exists
     */
    public static void signUpUser(User newUser) throws Exception{
        String username = newUser.getUsername();
        List<User> user = DBManager.userTable.fetchRows("username = "+username);
        if (user.size()>0){
            throw new Exception("Username already exists, choose a different one.");
        }
        addUser(newUser);
        saveCurrUserInformation(newUser);
    }



    /**
     * Adds the new user to all relevant tables
     */
    private static void addUser(User user) {
        // adds user to USERS table
        DBManager.userTable.insert(user);
    }

}
