package Logic.Pages;

import java.time.LocalDateTime;
import java.util.List;

import Logic.TimeUtil;
import ParameterClasses.User;
import SQLManaging.DBManager;

/**
 * Class that handles the logic of ShowPostUI
 */
public class ShowPostLogic {

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

    public static String getPostUsername(int userID) {
        List<User> user = DBManager.userTable.fetchRows("user_id = " + userID);
        return user.get(0).getUsername();
    }

    public static int getLikesCount(int postID) {
        String func = "count_likes(" + postID + ")";
        return DBManager.runFunc(func);
    }

}
