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
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

//This is the main page with recycleView.
// RecycleView is similar to list view, but more flexible (animations, etc).


public class MainActivity extends AppCompatActivity {
    //Make a new recycle view
    public RecyclerView recyclerView;
    ArrayList<Routine> routineList = new ArrayList<>();
    //make buttons
    public Button mainMenuButton;
    public Button taskButton;
    public Button calendarButton;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remembers the last instance state
        super.onCreate(savedInstanceState);
        //we're using the main page activity
        setContentView(R.layout.activity_main);

        //set the recycle view into a parameter
        recyclerView = findViewById(R.id.recyclerView);

        //1. makes the adapter for this recycle view
        //2. update the null message on main page
        //3. assign the buttons, and what they do
        adapterMethod();
        updateMessage();
        assignButtons();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Boolean isFirstRun =  getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //
            Intent Pre_PollIntent = new Intent(MainActivity.this, Pre_Poll.class);
            startActivity(Pre_PollIntent);
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();


    }

    private void adapterMethod(){
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(routineList);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateMessage() {
        TextView textNull = (TextView) findViewById(R.id.nullText);
        if(routineList.size() <= 0){
            textNull.setText("No routines set yet");
        }else {
            textNull.setText("");
        }
    }

    private void assignButtons(){
        mainMenuButton = findViewById(R.id.mainMenuButton);
        taskButton = findViewById(R.id.taskButton);
        calendarButton = findViewById(R.id.calendarButton);

        mainMenuButton.setOnClickListener(v -> openActivityMainMenu());
        taskButton.setOnClickListener(v -> openRoutinesPage());
        calendarButton.setOnClickListener(v -> openCalendar());
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

    @Override
    protected void onResume() {
        super.onResume();
        extras = getIntent().getExtras();
        addRoutineToList();
    }

    public void addRoutineToList(){

        //if extras, aka intent isn't empty
        if(extras != null){

            String title;
            String desc;
            int image;
            int index;

            //get intent and assign them to parameters
            title = extras.getString("title");
            desc = extras.getString("desc");
            image = extras.getInt("image");
            index = extras.getInt("index");

            //add to the main page list
            routineList.add(index,  new Routine(image, title, desc));

            //update the message so it dissappears
            updateMessage();
        }
    }

    public void onTestiButtonClicked(View view) {
        SharedPreferences score_prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int score = Integer.parseInt(score_prefs.getString("score", "0"));
        score++;
        String saveScore = Integer.toString(score);
        SharedPreferences.Editor settings_editor = score_prefs.edit();
        settings_editor.putString("score", saveScore).commit();

    }


}