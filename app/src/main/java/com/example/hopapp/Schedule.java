package com.example.hopapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Schedule extends AppCompatActivity {
ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide the bar above this activity
        getSupportActionBar().hide();

        setContentView(R.layout.activity_schedule);

        lv = findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<Routine>(this,
                android.R.layout.simple_list_item_1,
                SelectedRoutinesSingleton.getInstance().getSelectedRoutines()));

    }

}
