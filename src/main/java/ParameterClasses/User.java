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
    private boolean currentUser;


    /**
     * Constructor for User object
     */

    public User(int userID, String username, String password, String bio, String profilePicPath, boolean currUser){
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
        this.currentUser = currUser;
    }

    /**
     * getters for user attributes
     */
    public String getUsername() {return username;}
    public String getBio() { return bio; }
    public String getProfilePicPath() { return profilePicPath; }
    public String getPassword() { return password; }
    public int getUserID() {return userID; }
    public boolean isCurrUser() {return currentUser; }


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


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User){
            User user = (User) obj;
            return this.userID == user.getUserID();
        }
        return false;
    }

}