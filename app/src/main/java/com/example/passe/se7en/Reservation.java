package com.example.passe.se7en;

/**
 * Created by passe on 4/28/2018.
 */

public class Reservation {

    private String username,userphone,time,date,type,number,room,game,note,id;


    public Reservation() {
    }

    public Reservation( String un,String up,String t, String d , String ty, String n,String r,String g,String nn,String ids) {


        username=un;
        userphone=up;
        time=t;
        type=ty;
        date=d;
        number=n;
        room=r;
        game=g;
        note=nn;
        id=ids;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getNumber() {
        return number;
    }

    public String getRoom() {
        return room;
    }

    public String getNote() {
        return note;
    }

    public String getGame() {
        return game;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
