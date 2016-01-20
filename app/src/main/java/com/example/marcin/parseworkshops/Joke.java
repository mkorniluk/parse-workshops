package com.example.marcin.parseworkshops;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Marcin on 19.01.2016.
 */
@ParseClassName("Joke")
public class Joke extends ParseObject {
    public void setJoke(String text) {
        put("text", text);
    }

    public String getJoke() {
        return getString("text");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void incrementScore() {
        increment("score");
    }

    public int getScore() {
        return getInt("score");
    }
}
