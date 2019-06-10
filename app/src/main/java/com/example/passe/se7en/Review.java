package com.example.passe.se7en;

/**
 * Created by passe on 4/25/2018.
 */

public class Review {



    String username, userphone, comment, rating;


    public Review() {
    }

    public Review( String userName,String userPhone, String comment , String rating) {


        this.username = userName;
        this.userphone = userPhone;
        this.rating = rating;
        this.comment=comment;

    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public String getRating() {
        return rating;
    }

    public String getUsername() {
        return username;
    }

    public String getUserphone() {
        return userphone;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

}
