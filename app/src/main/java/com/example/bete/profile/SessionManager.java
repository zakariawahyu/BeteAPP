package com.example.bete.profile;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    public static final String NAME = "NAME";
    public static final String ID = "ID";

    private static final String LOGIN = "LOGIN";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LOGIN", PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void creteSession(String name, String id){
        editor.putBoolean("LOGIN", true);
        editor.putString("NAME", name);
        editor.putString("ID", id);
        editor.apply();
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME,null));
        user.put(ID,sharedPreferences.getString(ID,null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
