package com.nwm.api.entities.mobile.site;

public class SiteSimpleMobileEntity {
    private int siteId;
    private String id;
    private String name;

        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }

        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }

        public int getSiteId(){
            return this.siteId;
        }

        public void setSiteId(int siteId){
            this.siteId = siteId;
        }
}
