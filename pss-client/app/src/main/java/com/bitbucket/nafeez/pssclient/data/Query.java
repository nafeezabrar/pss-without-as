package com.bitbucket.nafeez.pssclient.data;

public class Query {
    private String name;
    private int id;

    public Query(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
