package com.example.passe.se7en;

public class Game {


    private String name,description,image;
    public Game(){
        description=" ";
    }
    public Game(String n,String d,String i ){
        name=n;
        description=d;
        if (description.equals(null))
        {
            description=" ";
        }
        image=i;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
