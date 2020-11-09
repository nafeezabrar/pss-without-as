package com.bitbucket.nafeez.pssclient.views;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitbucket.nafeez.pssclient.R;
import com.bitbucket.nafeez.pssclient.data.Query;

import java.util.List;


public class QueryListAdapter extends ArrayAdapter<Query> {
    private Context mContext;
    int layoutResourceId;
    private List<Query> queries;

    public QueryListAdapter(Context context, int layoutResourceId, List<Query> queries) {
        super(context, layoutResourceId, queries);
        this.mContext = context;
        this.layoutResourceId = layoutResourceId;
        this.queries = queries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }
        Query query = queries.get(position);
        TextView tv = (TextView) convertView.findViewById(R.id.query_name);
        tv.setText(query.getName());
        return convertView;
    }
}
