package ParameterClasses;
/**
 * An object that represents a like 
 */
public class Like {
    
    private int postID;
    private int userID;

    // Constructor
    public Like(int postID, int userID) {
        this.postID = postID;
        this.userID = userID;
    }

    // Getters
    public int getPostID() {
        return postID;
    }

    public int getUserID() {
        return userID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Follow){
            Like like = (Like) obj;
            return this.postID == like.getPostID() && this.userID == like.getUserID();
        }
        return false;
    }
}
