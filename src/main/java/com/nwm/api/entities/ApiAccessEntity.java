package com.nwm.api.entities;

import java.util.List;

public class ApiAccessEntity {
    private int id;
    private int id_site;
    private int is_active;
    private int is_deleted;
    private int id_user;
    private List<Integer> employee_ids;
    private List<Integer> site_ids;

//    private List<CompanyEntity> list_company;
//    private List<SiteEntity> list_site;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_site() {
        return id_site;
    }

    public void setId_site(int id_site) {
        this.id_site = id_site;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

//    public List<CompanyEntity> getList_company() {
//        return list_company;
//    }
//
//    public void setList_company(List<CompanyEntity> list_company) {
//        this.list_company = list_company;
//    }
//
//    public List<SiteEntity> getList_site() {
//        return list_site;
//    }
//
//    public void setList_site(List<SiteEntity> list_site) {
//        this.list_site = list_site;
//    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public List<Integer> getEmployee_ids() {
        return employee_ids;
    }

    public void setEmployee_ids(List<Integer> employee_ids) {
        this.employee_ids = employee_ids;
    }

    public List<Integer> getSite_ids() {
        return site_ids;
    }

    public void setSite_ids(List<Integer> site_ids) {
        this.site_ids = site_ids;
    }
}
