package Logic.Pages;
import java.util.List;

import ParameterClasses.User;
import SQLManaging.DBManager;

/**
 * Class that handles the logic of SignInUI
 */
public class SignInLogic{

    /**
     * Signs in a user
     * @param username
     * @param password
     * @return the new user 
     * @throws Exception - in case the user does not exist / in case the password is incorrect
     */
    public static User signInUser(String username, String password) throws Exception{
        List<User> user = DBManager.userTable.fetchRows("username = " + username);

        if (user.isEmpty()){
            throw new Exception("User does not exist / username is wrong. Please sign up.");
        }
        if (!verifyCredentials(user.get(0), password)){
            throw new Exception("Incorrect password. Please try again.");
        }

        String setClause = "curr_user = 1 WHERE username = '" + username + "'";
        DBManager.userTable.update(setClause);

        System.out.println(username + " logged in");

        return user.get(0);
    }

    /**
     * Verifies user credentials
     * @param user
     * @param password
     * @return true if valid, false otherwise
     */
    private static boolean verifyCredentials(User user, String password) {
        return user.ValidatePassword(password);

    }


}
