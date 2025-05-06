package Logic.Pages;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import Logic.LogicClass;
import Logic.TimeUtil;
import ParameterClasses.User;
import SQLManaging.DBManager;

/**
 * Class that handles the logic of ShowPostUI
 */
public class ShowPostLogic extends LogicClass{

    /**
     * Calculates the time that passed since posting
     * @return a stylized time string
     */
    public static String calculateTimeSincePosting(LocalDateTime postTime) {
        return TimeUtil.getTimeSince(postTime);
    }

    /**
     * Checks if the owner of the viewed post is the current user
     * @param profileUser - owner of the current post
     * @return true if the post belongs to the current user, false otherwise
     */
    public static boolean isLoggedInUser(String profileUser){
        List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);
        String currUser = user.get(0).getUsername();
        return currUser.equals(profileUser);
    }

}
