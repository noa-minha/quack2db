package ParameterClasses;

/**
 * An object that represents a user and all other users it is following
 */
public class Follow {

    private int followerID;
    private int followeeID;

    /**
     * Constructor for following object
     * @param followerID - the user that follows
     * @param followeeID - the user who is being followed
     */
    public Follow(int followerID, int followeeID) {
        this.followerID = followerID;
        this.followeeID = followeeID;
    }


    public int getFollowerID() {
        return followerID;
    }
    
    public int getFolloweeID() {
        return followeeID;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Follow){
            Follow f = (Follow) obj;
            return this.followeeID == f.getFolloweeID() && this.followerID == f.getFollowerID();
        }
        return false;
    }

}
