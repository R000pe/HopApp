package com.example.hopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class routinesPage extends AppCompatActivity implements RoutinePageAdapter.OnClickListener {

    //we need to make new main, because main's list is nonstatic, so you cant reference its methods without this
    MainActivity main = new MainActivity();
    private RecyclerView recyclerViewAll;
    ArrayList<Routine> routineList = new ArrayList<>();

    //make buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remembers the last instance state
        super.onCreate(savedInstanceState);
        //we're using the main page activity
        setContentView(R.layout.activity_routines_page);

        //set the recycle view into a parameter
        recyclerViewAll = findViewById(R.id.recyclerViewAll);

        //this is the list
        routineList.add( new Routine(R.drawable.default_logo,"Line 1", "Line 2"));
        routineList.add(new Routine(R.drawable.default_logo,"Line 3", "Line 4"));
        routineList.add(new Routine(R.drawable.default_logo,"Line 5", "Line 6"));

        //new adapter for recycle view
        RoutinePageAdapter reeAdapter = new RoutinePageAdapter(routineList, this);
        recyclerViewAll.setAdapter(reeAdapter);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(this));
    }

    //what happens, when u click on one of the cards on the recycle view list
    @Override
    public void onClick(int position) {
        //make new intent for mainclass
        Intent intent = new Intent(this, MainActivity.class);
        //get the position of the clicked item (index), and then get the title, desc and image of it
        intent.putExtra("title", routineList.get(position).getText1());
        intent.putExtra("desc", routineList.get(position).getText2());
        intent.putExtra("image", routineList.get(position).getmImageResource());
        //do the add to list method in main (it fetches the last intent)
        main.addRoutineToList();
        //start main activity. only for test purposes, to be removed later
        startActivity(intent);


    }
}