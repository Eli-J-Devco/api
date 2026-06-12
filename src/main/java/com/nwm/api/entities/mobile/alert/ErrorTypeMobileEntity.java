package com.nwm.api.entities.mobile.alert;

public class ErrorTypeMobileEntity {
    private int id;
    private String name;

    public int getId(){
        return this.id;
    }

    public void setId(int _id){
        this.id = _id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String _name){
        this.name = _name;
    }
}
