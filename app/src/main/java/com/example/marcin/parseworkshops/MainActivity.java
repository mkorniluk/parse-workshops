package com.example.marcin.parseworkshops;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 19.01.2016.
 */
public class MainActivity extends Activity {
    private ArrayList<Joke> items = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MyAdapter(items);
        recycler.setAdapter(adapter);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddJokeActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        items.clear();
        ParseQuery<Joke> query = ParseQuery.getQuery(Joke.class);
        query.findInBackground(new FindCallback<Joke>() {
            @Override
            public void done(List<Joke> objects, ParseException e) {
                if (e != null)
                    return;

                items.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
