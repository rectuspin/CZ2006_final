package com.example.athletica.data.facility;

public class Comments {

    private String userID;
    //private String facilityID;
    private String commentContent;

    public Comments() {

    }

    public Comments(String userID, String commentContent) {
        this.userID = userID;
        // this.facilityID=facilityID;
        this.commentContent = commentContent;
    }

    public String getUserID() {
        return userID;
    }

    public String getCommentContent() {
        return commentContent;
    }
}
