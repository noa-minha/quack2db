package ParameterClasses;


/**
 * An object that represents a notification 
 */
public class Notification {

    private String username;
    private int userID;
    private String action;


    // Constructor
    public Notification(String username, int userID, String action) {
        this.username = username;
        this.userID = userID;
        this.action = action;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public String getAction() {
        return action;
    }

    /**
     * Returns notification information in a readable String format
     */
    public String printNotification() {
        if (action.equals("like")){
            return username + " liked your post";
        }
        else if(action.equals("follow")){
            return username + " followed you";
        }
        else if(action.equals("comment")){
            return username + " commented on your post";
        }
        else{
            return "Unknown notification";
        }   
    }


}


