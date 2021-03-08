package com.example.hopapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmSetting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide the bar above this activity
        getSupportActionBar().hide();

        setContentView(R.layout.activity_alarmsetting);


    }
}
