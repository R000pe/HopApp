package com.example.hopapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokan avulla voi asettaa ArrayListin preferencihin, josta sen voi käynnistyksen kohdalla hakea uudestaan
 * @author Wilma Paloranta
 * @version 1.1 3/2021
 */

public class PreConfig {

    private static final String LIST_KEY = "list_key";

    /**
     * Gsonin avulla muuta saatu lista stringeiksi,
     * jonka jälkeen se voidaan tallentaa preferenceihin
     * @param context saa kyseisen aktiviteetin contextin, (this)
     * @param list aseta lista, joka halutaan tallentaa
     */
    public static void writeListInPref(Context context, List<Routine> list){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    /**
     * Luo uusi lista Gson avulla tallentamilla stringeillä
     * @param context saa kyseisen aktiviteetin contextin, (this)
     * @return palauta lista
     */
    public static List<Routine> readListFromPref(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Routine>>(){}.getType();
        List<Routine> list = gson.fromJson(jsonString, type);
        return  list;
    }

}
