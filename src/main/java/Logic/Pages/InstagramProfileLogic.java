package Logic.Pages;

import java.util.List;

import ParameterClasses.Follow;
import ParameterClasses.User;
import SQLManaging.DBManager;


/**
 * Class that handles the logic of InstagramProfileUI
 */
public class InstagramProfileLogic {

    /**
     * Recieves a user and checks if it is the current user
     *
     * @return true if same user, false otherwise
     */
    public static boolean isLoggedInUser(User profileUser) {
        List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);
        User currUser = user.get(0);
        return currUser.equals(profileUser);
    }

    /**
     * Checks if the current user if following the given user
     *
     * @return true if current user is following, false otherwise
     */
    public static boolean isCurrUsersFollowing(User profileUser) {
        List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);

        User loggedInUser = user.get(0);
        List<Follow> following = DBManager.followTable.fetchRows("follower_id= " + loggedInUser.getUserID() + " , followee_id= " + profileUser.getUserID());
        if (following != null) {
            return true;
        }
        return false;
    }


    /**
     * Adds the given user to the folllowing list of the current user
     * updates all necessary tables
     */
    public static void addFollower(User profileUser) {
        List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);
        User currUser = user.get(0);
        //add follower
        Follow follow = new Follow(currUser.getUserID(), profileUser.getUserID());
        DBManager.followTable.insert(follow);
    }

    /**
     * Stops following the given user and updates all relevant tables accordingly
     */
    public static void removeFollower(User profileUser) {
        List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);
        User currUser = user.get(0);
        List<Follow> following = DBManager.followTable.fetchRows("follower_id= " + currUser.getUserID() + " , followee_id= " + profileUser.getUserID());
        if (following != null) {
            DBManager.followTable.delete(following.get(0));
        }
    }

    public static int getFollowersCount(int userID) {
        String func = "count_followers(" + userID + ")";
        return DBManager.runFunc(func);
    }

    public static int getFollowingCount(int userID) {
        String func = "count_following(" + userID + ")";
        return DBManager.runFunc(func);
    }

    public static int getPostsCount(int userID) {
        String func = "count_posts(" + userID + ")";
        return DBManager.runFunc(func);
    }
}
