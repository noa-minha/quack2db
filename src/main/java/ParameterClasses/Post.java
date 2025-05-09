package ParameterClasses;

import java.time.LocalDateTime;

/**
 * An object that represents a post
 */
public class Post {

    private int postID;
    private int userID;
    private String imgPath;
    private String caption;
    private LocalDateTime createdAt;


    /**
     * Constructor for post object
     */
    public Post(int postID, int userID, String imgPath, String caption, LocalDateTime createdAt) {
        this.postID = postID;
        this.userID = userID;
        this.imgPath = imgPath;
        this.caption = caption;
        this.createdAt = createdAt;
    }

    // Getters
    public int getPostID() {
        return postID;
    }

    public int getUserID() {
        return userID;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getCaption() {
        return caption;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Post){
            Post post = (Post) obj;
            return this.postID == post.getPostID();
        }
        return false;
    }
}
