package Logic.Pages;

import ParameterClasses.*;
import SQLManaging.DBManager;

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
     * Fetches all the posts of all the users that the current user is following
     */
    public static List<Post> getAllFollowingPosts() {
        return DBManager.homeTable.fetchRows(null);
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
        if (likedPost.size()==0) return false;
        else return true;
    }

    /**
     * @param postID uniquely identifies a post
     * @return the number of likes for this post
     */
    public static int getLikesCount(int postID) {
        String func = "count_likes(" + postID + ")";
        return DBManager.runFunc(func);
    }
}
