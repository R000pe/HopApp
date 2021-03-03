package com.example.hopapp;

import java.util.ArrayList;
import java.util.List;

public class SelectedRoutinesSingleton {

    private static SelectedRoutinesSingleton ourInstance;
    private List<Routine> selectedRoutines;

    public static SelectedRoutinesSingleton getInstance(){
        if(ourInstance == null){
            ourInstance = new SelectedRoutinesSingleton();
            // create an instance only if there is no previous instance
        }
        return ourInstance;
    }

    private SelectedRoutinesSingleton(){ //constructor
        selectedRoutines = new ArrayList<Routine>();
    }

    public List<Routine> getSelectedRoutines() // method, that returns the list
    {
        return this.selectedRoutines;
    }
}
