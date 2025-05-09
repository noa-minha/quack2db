package Logic.Pages;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    public static String getTimeSince(LocalDateTime pastTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(pastTime, now);

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days >= 7) return pastTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (days > 0) return days + " day" + (days > 1 ? "s" : "") + " ago";
        if (hours > 0) return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        if (minutes > 0) return minutes + " minute" + (minutes > 1 ? "s" : "") + " ago";
        return "Just now";
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
