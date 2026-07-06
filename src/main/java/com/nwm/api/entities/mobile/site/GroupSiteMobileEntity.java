package com.nwm.api.entities.mobile.site;

import java.util.List;

import com.nwm.api.entities.mobile.SiteMobileEntity;

public class GroupSiteMobileEntity {
    private String id;
    private String name;
    private int groupId;
    private List<SiteMobileEntity> sites;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public List<SiteMobileEntity> getSites() {
        return this.sites;
    }

    public void setSites(List<SiteMobileEntity> sites) {
        this.sites = sites;
    }
}
