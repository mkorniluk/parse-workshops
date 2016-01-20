package com.example.marcin.parseworkshops;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Marcin on 20.01.2016.
 */
@ParseClassName("Vote")
public class Vote extends ParseObject {
    public void setJoke(Joke joke){
        put("joke",joke);
    }

    public Joke getJoke(){
        return (Joke) getParseObject("joke");
    }

    public void setUser(ParseUser user){
        put("user",user);
    }

    public ParseUser getUser(){
        return getParseUser("user");
    }
}
