package com.example.passe.se7en;


public class Offer {


    String startdate,enddate,description;


    public Offer(){}
    public Offer(String s,String e,String d){

      this.startdate=s;
        this.enddate=e;
        this.description=d;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getDescription() {
        return description;
    }

    public String getStartdate() {
        return startdate;
    }

}
