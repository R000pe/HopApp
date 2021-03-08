package com.example.hopapp;

import android.content.Intent;
import android.os.Bundle;
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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Schedule.this, AlarmSetting.class);
                intent.putExtra("EXTRA_POSITION", position);
                startActivity(intent);
            }   // view = adapterissa painettu view, position = viewn sijainti, id = rivin id
        });
    }

}
