package com.example.hopapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SelectedRoutinesSingleton {

    private static SelectedRoutinesSingleton ourInstance;
    public List<Routine> selectedRoutines;
    Context context;


    public static SelectedRoutinesSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new SelectedRoutinesSingleton();
            // create an instance only if there is no previous instance
        }
        return ourInstance;
    }

    private SelectedRoutinesSingleton() { //constructor
        selectedRoutines = new ArrayList<Routine>();
    }

    public List<Routine> getSelectedRoutines() // method, that returns the list
    {
        return this.selectedRoutines;
    }

    /*public static void saveData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(selectedRoutines);
        editor.putString("task list", json);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Routine>>() {}.getType();
        selectedRoutines = gson.fromJson(json, type);

        if(selectedRoutines == null){
            selectedRoutines = new ArrayList<>();
        }
    }*/


}
