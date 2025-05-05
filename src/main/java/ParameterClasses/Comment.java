package ParameterClasses;

public class Comment{

    private int commentID;
    private int postID;
    private int userID;
    private String text;

    // Constructor
    public Comment(int commentID, int postID, int userID, String text) {
        this.commentID = commentID;
        this.postID = postID;
        this.userID = userID;
        this.text = text;
    }

    // Getters
    public int getCommentID() {
        return commentID;
    }

    public int getPostID() {
        return postID;
    }

    public int getUserID() {
        return userID;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User){
            Comment comment = (Comment) obj;
            return this.commentID == comment.getCommentID();
        }
        return false;
    }   

}

