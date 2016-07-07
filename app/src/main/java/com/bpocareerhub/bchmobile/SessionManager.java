package com.bpocareerhub.bchmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by BCH_OJT on 6/28/2016.
 */
public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "BCH";
    Context _context;

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    public void checkSessionInApp(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }
    public void checkSessionInLogin(){
        if(this.isLoggedIn()){
            Intent i = new Intent(_context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }
    private boolean isLoggedIn(){

        return pref.getBoolean("isLoggedIn", false);
    }
    public void createLoginSession(String email, String password) {
        editor.putBoolean("isLoggedIn", true);
        editor.putString("email", email);
        editor.putString("password", password);

        editor.commit();
    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put("email", pref.getString("email",null));
        user.put("password", pref.getString("password",null));

        return user;
    }
    public void logoutSession(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }
}
