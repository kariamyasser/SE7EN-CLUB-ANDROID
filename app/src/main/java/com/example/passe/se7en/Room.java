package com.example.passe.se7en;


public class Room {
    private String state,name,image;
    public Room() {
    }

    public Room( String s,String n ,String i) {

        state=s;
        name=n;
        image=i;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }
}

