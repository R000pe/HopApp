package com.example.hopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//This is the main page with recycleView.
// RecycleView is similar to list view, but more flexible (animations, etc).


public class MainActivity extends AppCompatActivity {
    //Make a new recycle view
    RecyclerView recyclerView;
    //make buttons
    private Button mainMenuButton;
    private Button taskButton;
    private Button calendarButton;

    //new strings, in which we save data for description, title and image of a task
    String s1[], s2[];
    //R.drawable.default_logo is the image, and for now is the same for them all
    int images[] = {R.drawable.default_logo, R.drawable.default_logo, R.drawable.default_logo, R.drawable.default_logo,
            R.drawable.default_logo, R.drawable.default_logo, R.drawable.default_logo, R.drawable.default_logo,};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remembers the last instance state
        super.onCreate(savedInstanceState);
        //we're using the main page activity
        setContentView(R.layout.activity_main);

        //set the recycle view into a parameter
        recyclerView = findViewById(R.id.recyclerView);

        //set the strings to be main page's text views
        s1 = getResources().getStringArray(R.array.routines);
        s2 = getResources().getStringArray(R.array.description);

        //new adapter for recycle view
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, s1, s2, images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainMenuButton = findViewById(R.id.mainMenuButton);
        taskButton = findViewById(R.id.taskButton);
        calendarButton = findViewById(R.id.calendarButton);

        //wait for button click
        //do this method
        mainMenuButton.setOnClickListener(v -> openActivityMainMenu());
        taskButton.setOnClickListener(v -> openRoutinesPage());
        calendarButton.setOnClickListener(v -> openCalendar());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity
            Intent Pre_PollIntent = new Intent(MainActivity.this, Pre_Poll.class);
            startActivity(Pre_PollIntent);
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();
    }


    //set a simplecallback which reacts to gestures
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
            ItemTouchHelper.RIGHT) {
        @Override
        //this does nothing for now
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        //determines what happens when you swipe something on the list right / left
        //this also does nothing for now
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:

                    break;
                case ItemTouchHelper.RIGHT:
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //
        getMenuInflater().inflate(R.menu.settingsmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent optionsMenuIntent = new Intent(this, Settings.class);
                startActivity(optionsMenuIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //get intent and change activities to main menu
    public void openActivityMainMenu() {
        Intent mainMenuIntent = new Intent(this, MainMenu.class);
        startActivity(mainMenuIntent);
    }

    //get intent and change activities to routines page
    public void openRoutinesPage() {
        Intent routinesPageIntent = new Intent(this, routinesPage.class);
        startActivity(routinesPageIntent);
    }

    //get intent and change activities to calendar
    public void openCalendar() {
        Intent calendarIntent = new Intent(this, Calendar.class);
        startActivity(calendarIntent);
    }
}