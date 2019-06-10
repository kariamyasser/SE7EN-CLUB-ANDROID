package com.example.passe.se7en;

/**
 * Created by passe on 6/6/2018.
 */

public class InProgress {
    String millis,starttime,type;


    public InProgress() {
    }

    public InProgress(String itemname, String quantity, String userEmail) {

        this.starttime = itemname;
        this.millis = quantity;
        this.type = userEmail;


    }

    public void setMillis(String millis) {
        this.millis = millis;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMillis() {
        return millis;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getType() {
        return type;
    }
}
