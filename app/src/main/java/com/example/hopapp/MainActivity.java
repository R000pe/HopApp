package com.example.hopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

//This is the main page with recycleView.
// RecycleView is similar to list view, but more flexible (animations, etc).


public class MainActivity extends AppCompatActivity {
    //Make a new recycle view
    public RecyclerView recyclerView;
    RecyclerViewAdapter myAdapter;
    public SelectedRoutinesSingleton s = SelectedRoutinesSingleton.getInstance();

    //make buttons
    public ImageButton mainMenuButton;
    public ImageButton taskButton;
    public ImageButton calendarButton;
    public Button buttonSave;
    List<String> archivedRoutines = new ArrayList<>();


    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        popupMessage();
        //remembers the last instance state
        super.onCreate(savedInstanceState);
        //we're using the main page activity
        setContentView(R.layout.activity_main);

        //set the recycle view into a parameter
        recyclerView = findViewById(R.id.recyclerView);
        //s.getSelectedRoutines() = PreConfig.readListFromPref(this);
        s.selectedRoutines = PreConfig.readListFromPref(this);

        if (s.selectedRoutines == null)
            s.selectedRoutines = new ArrayList<>();

        //1. makes the adapter for this recycle view
        //2. update the null message on main page
        //3. assign the buttons, and what they do
        adapterMethod();
        updateMessage();
        assignButtons();

        //PreConfig.writeListInPref(getApplicationContext(), s.getSelectedRoutines());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //
            Intent Pre_PollIntent = new Intent(MainActivity.this, Pre_Poll.class);
            startActivity(Pre_PollIntent);
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();

        PreConfig.writeListInPref(getApplicationContext(), s.selectedRoutines);
    }

    private void adapterMethod() {
        myAdapter = new RecyclerViewAdapter((ArrayList<Routine>) s.getSelectedRoutines()); //täällä luki routineList
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateMessage() {
        TextView textNull = (TextView) findViewById(R.id.nullText);
        if (s.getSelectedRoutines().size() <= 0) { //täällä luki routineList.size()
            textNull.setText("No routines set yet");
        } else {
            textNull.setText("");
        }
    }

    private void assignButtons() {
        mainMenuButton = findViewById(R.id.mainMenuButton);
        taskButton = findViewById(R.id.taskButton);
        calendarButton = findViewById(R.id.calendarButton);

        mainMenuButton.setOnClickListener(v -> openActivityMainMenu());
        taskButton.setOnClickListener(v -> openCategoryPage());
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
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    //Alert dialog confirming deletion of routine on the list
                    new AlertDialog.Builder(viewHolder.itemView.getContext())
                            .setTitle("Delete Routine")
                            .setMessage("Do you really want to delete this routine?")
                            .setIcon(android.R.drawable.ic_dialog_alert)

                            .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                                /*User selects yes in alert dialog,
                                Task gets deleted from list and main menu.*/
                                s.selectedRoutines.remove(position);
                                myAdapter.notifyItemRemoved(position);
                                PreConfig.writeListInPref(getApplicationContext(), s.selectedRoutines);
                                updateMessage();

                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Routine Deleted", Snackbar.LENGTH_LONG);
                                snackbar.show();

                            })
                            .setNegativeButton("CANCEL", (dialog, id) -> {
                                /* User cancelled the dialog,
                                 so we will refresh the adapter to prevent hiding the item from UI*/
                                myAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            })
                            .create()
                            .show();
                    break;

                case ItemTouchHelper.RIGHT:
                    s.selectedRoutines.remove(position);
                    myAdapter.notifyItemRemoved(position);
                    PreConfig.writeListInPref(getApplicationContext(), s.selectedRoutines);
                    //Get score from sharedprferences ad 1 point and save score
                    SharedPreferences score_prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    int score = Integer.parseInt(score_prefs.getString("score", "0"));
                    score++;
                    String saveScore = Integer.toString(score);
                    SharedPreferences.Editor settings_editor = score_prefs.edit();
                    settings_editor.putString("score", saveScore).commit();
                    updateMessage();
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
    public void openCategoryPage() {
        Intent categoryPage = new Intent(this, CategoryPage.class);
        startActivity(categoryPage);
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

    public void addRoutineToList() {

        //if extras, aka intent isn't empty
        if (extras != null) {

            String title;
            String desc;
            int image;
            int index;

            //get intent and assign them to parameters
            title = extras.getString("title");
            desc = extras.getString("desc");
            image = extras.getInt("image");
            index = extras.getInt("index");

            int y = 0;

            while (y == 0) {
                Routine r = new Routine(image, title, desc);
                //after current while loop object r wont have a reference & will be deleted by garbage collector

                for (int i = 0; i < s.getSelectedRoutines().size(); i++) { // a for loop through singleton's selectedroutines-list
                    if (s.getSelectedRoutines().get(i).getText1().equals(r.getText1())) { // once a similar object is found ..
                        y = 2;
                        break; // .. for loop will be abrupted
                    }
                }

                if (y == 2) { // if the for loop turned y = 2 ..
                    break; // .. while loop will be abrupted
                }

                y = 1; // if no similar object is found in singleton's list, y = 1
            } // end of while loop

            if (y == 1) { // if y = 1 after previous while loop ..
                //add to the main page list
                //s.getSelectedRoutines().add(index, new Routine(image, title, desc)); // .. the object will be added to the list
                s.getInstance().getSelectedRoutines().add(new Routine(image, title, desc));
                PreConfig.writeListInPref(getApplicationContext(), s.getSelectedRoutines());
            }

            System.out.println(s.getSelectedRoutines().size()); // should be deleted later

            //update the message so it disappears
            updateMessage();
        }
    }

    //Welcome message with user name
    public void popupMessage(){
        SharedPreferences name_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = name_pref.getString("full_name", "");
        Toast.makeText(MainActivity.this, "Hello "+name, Toast.LENGTH_SHORT).show();
    }
}