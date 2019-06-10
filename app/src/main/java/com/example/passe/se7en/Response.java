package com.example.passe.se7en;

/**
 * Created by passe on 4/28/2018.
 */

public class Response {


   private String respond,time,date,type,note,number,game,room;


    public Response() {
    }

    public Response( String r,String t, String d , String ty,String n,String nn,String rr,String g) {


       respond=r;
        time=t;
        type=ty;
        date=d;
        room=rr;
        number=n;
        note=nn;
        game=g;

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

    public void setNote(String note) {
        this.note = note;
    }

    public String getGame() {
        return game;
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

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getRespond() {
        return respond;
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

    public void setRespond(String respond) {
        this.respond = respond;
    }
}
