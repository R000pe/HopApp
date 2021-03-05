package com.example.hopapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//this preferences config is used to store an arraylist into preferences
//since you can't do it without turning it into a string first, and then back
public class PreConfig {

    //make a key for the list
    private static final String LIST_KEY = "list_key";

    //this method saves arraylist string into preferences
    public static void writeListInPref(Context context, List<Routine> list){
        //we use gson to make this
        Gson gson = new Gson();
        // turn the list into strings
        String jsonString = gson.toJson(list);

        //save the string list into preferences
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    //this method returns saved arraylist string from preferences
    public static List<Routine> readListFromPref(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        //fetch the string with this key
        String jsonString = pref.getString(LIST_KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Routine>>(){}.getType();
        List<Routine> list = gson.fromJson(jsonString, type);
        return  list;
    }

}
