package com.example.athletica.data.facility;

public class Comments {

    private String userID;
    private String userName;
    private String commentContent;
    private float userRating;


    public Comments() {

    }

    public Comments(String userID, String userName, String commentContent) {
        this.userID = userID;
        this.userName = userName;
        this.commentContent = commentContent;
    }

    public Comments(String userID, String userName, String commentContent,float userRating) {
        this.userID = userID;
        this.userName = userName;
        this.commentContent = commentContent;
        this.userRating=userRating;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public float getUserRating() {
        return userRating;
    }
}
