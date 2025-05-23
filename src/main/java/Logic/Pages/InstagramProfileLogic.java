package Logic.Pages;

import java.util.List;

import ParameterClasses.*;
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
        List<Follow> following = DBManager.followTable.fetchRows("follower_id= " + loggedInUser.getUserID() + " AND following_id= " + profileUser.getUserID());
        if (!following.isEmpty()) {
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
        List<Follow> following = DBManager.followTable.fetchRows("follower_id= " + currUser.getUserID() + " , following_id= " + profileUser.getUserID());
        if (!following.isEmpty()) {
            DBManager.followTable.delete(following.get(0));
        }
    }

    /**
     * returns the number of users following a user
     * @param userID the number uniquely identifies a user
     * @return the number of users following a user
     */
    public static int getFollowersCount(int userID) {
        String func = "count_followers(" + userID + ")";
        return DBManager.runFunc(func);
    }
    /**
     * returns the number of users a user follows
     * @param userID the number uniquely identifies a user
     * @return the number of users a user follows
     */
    public static int getFollowingCount(int userID) {
        String func = "count_following(" + userID + ")";
        return DBManager.runFunc(func);
    }

    /**
     * returns the number of posts a user owns
     * @param userID the number uniquely identifies a user
     * @return the number of posts a user owns
     */
    public static int getPostsCount(int userID) {
        String func = "count_posts(" + userID + ")";
        return DBManager.runFunc(func);
    }

    /**
     * @param profileUser  a user whos posts being fetched
     * @return the posts of a user
     */
    public static List<Post> getAllPosts(User profileUser) {
        if (profileUser == null){
            // if user == current user
            List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);
            profileUser = user.get(0);
        }
        int user_id = profileUser.getUserID();
        List<Post> posts = DBManager.postTable.fetchRows("user_id = " + user_id);
        return posts;
    }

    /**
     * logs out for the current user
     */
    public static void logout() {
        String setClause = "curr_user = 0";
        DBManager.userTable.update(setClause);
    }
}
