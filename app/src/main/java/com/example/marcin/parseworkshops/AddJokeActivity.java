package com.example.marcin.parseworkshops;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Marcin on 19.01.2016.
 */
public class AddJokeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addjoke_activity);

        final EditText jokeET = (EditText) findViewById(R.id.joke);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Joke joke = new Joke();
                joke.setUser(ParseUser.getCurrentUser());
                joke.setJoke(jokeET.getText().toString());
                joke.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        finish();
                    }
                });
            }
        });
    }
}
