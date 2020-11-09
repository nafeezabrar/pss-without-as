package com.bitbucket.nafeez.pssclient.server;

import java.util.ArrayList;
import java.util.List;

import com.bitbucket.nafeez.pssclient.data.Query;

public class ServerDataLoader {
    public List<Query> getQueryList() {
        /* todo */
        ArrayList<Query> queries = new ArrayList<>();
        Query q = new Query(1, "Report Temperature");
        queries.add(q);
        q = new Query(2, "Report Price");
        queries.add(q);
        return queries;
    }
}
