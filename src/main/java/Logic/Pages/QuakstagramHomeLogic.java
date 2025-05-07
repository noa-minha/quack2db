package Logic.Pages;

import ParameterClasses.*;
import SQLManaging.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles the logic of QuackstagramHomeUI
 */
public class QuakstagramHomeLogic {

    /**
     * Like a given post (by the current user)
     * updates all relevant tables
     */
    public static void likePost(Post likedPost) {
        List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);
        int user_id = user.get(0).getUserID();

        try {
            DBManager.likeTable.insert(new Like(likedPost.getPostID(), user_id));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Unlikes a post and updates POSTS table
     */
    public static void unLikePost(Post unlikedPost) {

        List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);
        int user_id = user.get(0).getUserID();

        try {
            DBManager.likeTable.delete(new Like(unlikedPost.getPostID(), user_id));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    /**
     * Recieves a Following object of all the users the current user is following
     */
    private static List<Follow> getFollowingOfCurrUser() {
        List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);
        int user_id = user.get(0).getUserID();
        //TODO - MAKE SURE THE ATTRIBUTE NAME IS CORRECT
        List<Follow> followingList = DBManager.followTable.fetchRows("followerID = " + user_id);
        return followingList;
    }

    /**
     * Fetches all the posts of all the users that the current user is following
     */
    public static ArrayList<Post> getAllFollowingPosts() {
        List<Follow> followingUsers = getFollowingOfCurrUser();
        ArrayList<Post> posts = new ArrayList<>();

        for (Follow follow : followingUsers) {
            List<Post> followingPosts = DBManager.postTable.fetchRows("user_id = " + follow.getFolloweeID());
            posts.addAll(followingPosts);
        }
        return posts;

    }

    /**
     * Checks if the current user already liked the post
     *
     * @return true if already liked, false otherwise
     */
    public static boolean didUserAlreadyLike(Post post) {
        List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);
        int user_id = user.get(0).getUserID();
        List<Like> likedPost = DBManager.likeTable.fetchRows("post_id = " + post.getPostID() + ",user_id = " + user_id);
        if (likedPost == null) return false;
        else return true;
    }

    public static int getLikesCount(int postID) {
        String func = "count_likes(" + postID + ")";
        return DBManager.runFunc(func);
    }
}
