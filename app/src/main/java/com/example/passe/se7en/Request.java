package com.example.passe.se7en;

/**
 * Created by passe on 4/27/2018.
 */

public class Request {

    String username, useremail, userphone, date, type, time,room ,number,game;


    public Request() {
    }

    public Request(String date, String type, String userEmail, String userName, String userPhone, String time,String r,String n,String game) {

        this.date = date;
        this.type = type;
        this.useremail = userEmail;
        this.username = userName;
        this.userphone = userPhone;
        this.time = time;
        room=r;
        number=n;
this.game=game;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRoom() {
        return room;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getGame() {
        return game;
    }

    public String getNumber() {
        return number;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getUserphone() {
        return userphone;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

}

