package com.bitbucket.nafeez.pssclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.bitbucket.nafeez.pssclient.data.Query;
import com.bitbucket.nafeez.pssclient.server.ServerDataLoader;

import java.util.List;

import com.bitbucket.nafeez.pssclient.views.QueryListAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServerDataLoader serverDataLoader = new ServerDataLoader();
        List<Query> queryList = serverDataLoader.getQueryList();
        ListView listView = (ListView) findViewById(R.id.listView);
        QueryListAdapter queryListAdapter = new QueryListAdapter(this, R.layout.list_item, queryList);
        listView.setAdapter(queryListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
