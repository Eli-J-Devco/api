package com.nwm.api.entities;

public class ApiEndPointEntity {
    private int id;
    private String name;
    private String route;
    private String method;
    private int total_call;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getTotal_call() {
        return total_call;
    }

    public void setTotal_call(int total_call) {
        this.total_call = total_call;
    }
}
