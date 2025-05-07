package Logic.Pages;

import ParameterClasses.User;
import SQLManaging.DBManager;

import java.util.List;

/**
 * Class that handles the logic of ExploreUI
 */
public class ExploreUILogic {

    /**
     * Searches for a user given a String username
     * @return the user if found or throws an exception saying a user was not found.
     */
    public static User performSearch(String searchedUsername) throws Exception{
        List<User> user = DBManager.userTable.fetchRows("user_id = " + searchedUsername);
        if(user.size() == 0){
            throw new Exception("No user found.");
        }
        return user.get(0);
    }
}
