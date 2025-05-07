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
        // add the new user
        DBManager.userTable.insert(newUser);

        // make it the curr user
        String setClause = "curr_user = 1 WHERE username = '" + username + "'";
        DBManager.userTable.update(setClause);
    }
}
