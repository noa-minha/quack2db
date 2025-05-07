package Logic.Panels;

import java.util.ArrayList;
import java.util.List;

import ParameterClasses.Post;
import ParameterClasses.User;
import SQLManaging.DBManager;

/**
 * Class that handles the logic of GridPanelUI
 */
public class GridPanelLogic {

    /**
     * Recieve all the posts from the given user
     *
     * @param user
     * @return a List of the object posts. If user has no posts - returns empty ArrayList
     */
    public static List<Post> getUserPosts(User user) {
        int user_id = user.getUserID();
        List<Post> posts = DBManager.postTable.fetchRows("user_id = " + user_id);
        return posts;
    }


    /**
     * Returns a list of all posts not from current user
     */
    public static List<Post> getAllOtherUserPosts(){
        //TODO - MAKE SURE ATTRIBUTE NAME IS RIGHT
        List<User> user = DBManager.userTable.fetchRows("curr_user = " + 1);
        int currUser_id = 0;
        if (user != null) {
            currUser_id = user.get(0).getUserID();
        } else {
            // throw new Exception("no user is connected");
            System.out.println("no user is connected");
        }

        //TODO - DECIDE WHAT POSTS WE WANT TO SHOW. ATM ALL POSTS FROM ALL USERS
        List<User> users = DBManager.userTable.fetchRows("user_id != " + currUser_id);

        List<Post> posts = new ArrayList<>();

        for (User u : users) {
            List<Post> otherUserPosts = getUserPosts(u);
            posts.addAll(otherUserPosts);
        }

        return posts;
    }

    /**
     * Fetches all relevant posts for the grid
     * If this is a post grid for a user profile - fetches all user posts
     * If this is the explore page post grid - fetches all posts that are not the current users
     *
     * @param user - the given user (in case of profile), null if explore page
     * @return List of the type Post
     */
    public static List<Post> fetchAllPosts(User user){
        if (user !=null) {
            return getUserPosts(user);
        }
        return getAllOtherUserPosts();
    }

}
