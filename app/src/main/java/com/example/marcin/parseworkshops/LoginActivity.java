package com.example.marcin.parseworkshops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Parse.initialize(this);

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        ParseObject.registerSubclass(Joke.class);
        ParseObject.registerSubclass(Vote.class);

        final EditText email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
                query.whereEqualTo("username", email.getText().toString());
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (objects == null || objects.isEmpty()) {
                            register(email.getText().toString(), password.getText().toString());
                        } else {
                            login(email.getText().toString(), password.getText().toString());
                        }
                    }
                });
            }
        });

        if (ParseUser.getCurrentUser() != null)
            launchMainActivity();
    }

    private void login(String email, String password) {
        ParseUser.logInInBackground(email, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null)
                    return;
                launchMainActivity();
            }
        });
    }

    private void register(final String email, final String password) {
        ParseUser user = new ParseUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(email);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null)
                    return;
                launchMainActivity();
            }
        });
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
