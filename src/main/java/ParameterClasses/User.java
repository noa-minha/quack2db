package ParameterClasses;


/**
 * An object that represents a user on quackstagram
 */
public class User{

    private static final String DEFAULT_PROFILE_PIC = "img/logos/DACS.png"; 

    private int userID;
    private String username;
    private String password;
    private String bio;
    private String profilePicPath;
    // TODO : do we need these?
    // TODO:change comments
    // private boolean currentUser;
    // private DateTime createdAt;


    /**
     * Constructor for User object
     * @param username
     * @param password
     * @param bio
     * @param profilePicPath
     */

    public User(int userID, String username, String password, String bio, String profilePicPath){
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.bio = bio;
        if (profilePicPath == null){
            this.profilePicPath = DEFAULT_PROFILE_PIC;
        }
        else{
            this.profilePicPath = profilePicPath;
        }
    }

    /**
     * getters for user attributes
     */
    public String getUsername() {return username;}
    public String getBio() { return bio; }
    public String getProfilePicPath() { return profilePicPath; }
    public String getPassword() { return password; }
    public int getUserID() {return userID; }


    // TODO: is needed? delete?
    /**
     * Checks whether a given password is the user's password
     * @param password
     * @return true if passwords match, false otherwise
     */
    public boolean ValidatePassword(String password){
        if(password.equals(this.getPassword())){
            return true;
        }
        return false;
    }

    /**
     * Returns all relevant information in a csv format
     */
    @Override
    public String toString() {
        return userID + "," +username + "," + password + "," + bio + "," + profilePicPath;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User){
            User user = (User) obj;
            return this.userID == user.getUserID();
        }
        return false;
    }

    //getPrimaryKey? TODO

}