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
/**
 * Ainokaisen listaan listataan kayttajan valitsemat aktiviteetit. Lista esiintyy etusivulla ja aikataululuokassa
 * @author sanku
 * @version 1.1 03/2021
 */
public class SelectedRoutinesSingleton {

    private static SelectedRoutinesSingleton ourInstance;
    public List<Routine> selectedRoutines;
    Context context;

    public static SelectedRoutinesSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new SelectedRoutinesSingleton();
            // instanssi luodaan vain silloin, kun instassia ei ole luotu jo.
        }
        return ourInstance;
    }

    private SelectedRoutinesSingleton() { //constructor
        selectedRoutines = new ArrayList<Routine>();
    }

/**
 * @return selectedRoutines -lista
 * */
    public List<Routine> getSelectedRoutines() // method, that returns the list
    {
        return this.selectedRoutines;
    }
}
