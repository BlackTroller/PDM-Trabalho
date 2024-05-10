package com.example.projecto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class preferences {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public final static String PREFS_NAME = "F1";

    public preferences(Context context){
        this.context = context;
    }

    public static void setCords(String key, String lat, String _long, String name) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, lat);
        editor.putString(key + 1, _long);
        editor.putString(key + 2, name);
        editor.apply();
    }

    public static String[] getCords(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String lat = prefs.getString(key, "10");
        String _long = prefs.getString(key + 1, "10");
        String name = prefs.getString(key + 2, "name");
        return new String[] {lat, _long, name};
    }

}
