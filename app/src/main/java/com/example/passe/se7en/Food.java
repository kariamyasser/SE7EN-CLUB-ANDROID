package com.example.passe.se7en;



public class Food {


    private String name,price,image;
    public Food(){}
    public Food(String n,String p,String i ){
        name=n;
        price=p;
        image=i;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;

    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
