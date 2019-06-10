package com.example.passe.se7en;



public class Order {


    String username, useremail, userphone, quantity, itemname, room;


    public Order() {
    }

    public Order(String itemname, String quantity, String userEmail, String userName, String userPhone, String roomnum) {

        this.itemname = itemname;
        this.quantity = quantity;
        this.useremail = userEmail;
        this.username = userName;
        this.userphone = userPhone;
        this.room = roomnum;

    }

    public String getRoom() {
        return room;
    }

    public void setRoomnum(String roomnum) {
        this.room = roomnum;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getItemname() {
        return itemname;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getUsername() {
        return username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
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


}
