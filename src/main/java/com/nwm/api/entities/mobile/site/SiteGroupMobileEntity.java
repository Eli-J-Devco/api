package com.nwm.api.entities.mobile.site;

public class SiteGroupMobileEntity {
    private int id;
    private String name;
    private int sites;
    private int subGroup;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getSites(){
        return this.sites;
    }

    public void setSites(int sites){
        this.sites = sites;
    }

    public int getSubGroup(){
        return this.subGroup;
    }

    public void setSubGroup(int subGroup){
        this.subGroup = subGroup;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
}
