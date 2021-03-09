package com.example.hopapp.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.hopapp.MainActivity;
import com.example.hopapp.PreConfig;
import com.example.hopapp.R;
import com.example.hopapp.Routine;
import com.example.hopapp.RoutinePageAdapter;
import com.example.hopapp.SelectedRoutinesSingleton;
import com.example.hopapp.TaskViewActivity;

import java.util.ArrayList;
/**
 * Luo improvements luokan listan ja siihen liittyvät methodit
 * @author sanku, Wilma Paloranta
 * @version 1.1 03/2021
 * */
public class improvementsList extends AppCompatActivity implements RoutinePageAdapter.OnClickListener {

    //we need to make new main, because main's list is nonstatic, so you cant reference its methods without this
    MainActivity main = new MainActivity();
    private RecyclerView recyclerViewAll;
    ArrayList<Routine> routineList = new ArrayList<>();
    public SelectedRoutinesSingleton s = SelectedRoutinesSingleton.getInstance();

    //sanna
    private int year, month, day, routinePosition; // these will be later used in datepickerdialog
    private DatePickerDialog datePicker;
    private Intent intent;
    /**
     * DatePickerDialogin OnDateSetListenerin avulla kayttaja valitsee aktiviteetilleen toivomansa pvm:n
     * tupsahtaneen kalenterinakyman avulla. Arvot lisataan aktiviteetille.
     * */
    private DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {

            routineList.get(routinePosition).setYear(year);    // paivamaaran arvot lisätään rutiiniin
            routineList.get(routinePosition).setMonth(month);
            routineList.get(routinePosition).setDayOfMonth(day);

            intent.putExtra("YEAR", routineList.get(routinePosition).getYear());    // arvot lähetetään eteenpäin (mainactivityyn)
            intent.putExtra("MONTH", routineList.get(routinePosition).getMonth());
            intent.putExtra("DAY", routineList.get(routinePosition).getDayOfMonth());

            Toast.makeText(improvementsList.this, "Routine added to the list!", Toast.LENGTH_SHORT).show();

            startActivity(intent);
        }
    }; // listener used to indicate the user has finished selecting a date (SANNA)



    //make buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improvements_list);
        //piilota aktiviteetin yläpuolella olevan palkin
        getSupportActionBar().hide();

        recyclerViewAll = findViewById(R.id.improvementsRecycleView);

        //lisää nämä listalle
        routineList.add( new Routine(R.drawable.z_routine_water,"Hydrohomie", "Drink a glass of water every 2 hours"));
        routineList.add(new Routine(R.drawable.z_routine_todo,"To-Do list", "Write your to-do list for the day"));
        routineList.add(new Routine(R.drawable.z_routine_bedtime,"Bedtime", "Go to sleep at 10"));
        routineList.add(new Routine(R.drawable.z_routine_cooking,"Master Chef", "Make dinner"));
        routineList.add(new Routine(R.drawable.z_routine_cleaning,"Viscera's cleanup crew", "Tidy up your room/house"));
        routineList.add(new Routine(R.drawable.z_routine_study,"Intelligence +1", "Study for 2 hours"));
        routineList.add(new Routine(R.drawable.z_routine_read,"Bookworm", "Read a chapter of a book"));
        routineList.add(new Routine(R.drawable.z_routine_create,"Bob Ross", "Do something creative"));

        //Uusi adapteri
        RoutinePageAdapter reeAdapter = new RoutinePageAdapter(routineList, this);
        recyclerViewAll.setAdapter(reeAdapter);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Aseta parametrit extroihin
     * Avaa uusi intent, joka näyttää tarkemmat tiedot rutiinista
     * @param position hankkii onclick:in alaisena olevan kortin indeksin
     */
    @Override
    public void onClick(int position) {
        //make new intent for mainclass
        Intent intent = new Intent(this, TaskViewActivity.class);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaPlayer.start();
        //get the position of the clicked item (index), and then get the title, desc and image of it
        intent.putExtra("title", routineList.get(position).getTitle());
        intent.putExtra("desc", routineList.get(position).getDesc());
        intent.putExtra("image", routineList.get(position).getmImageResource());
        //do the add to list method in main (it fetches the last intent)

        //start main activity. only for test purposes, to be removed later
        startActivity(intent);
    }

    /**
     * Uusi intent Main Activitylle, joka lisää intentin avulla extroihin parametrit
     * ja addToRoutineList methodin kautta lisää ne etusivun listalle.
     * aktiviteetille valitaan pvm
     * @param position hakee onclick:in alaisena olevan kortin indeksin
     */
    @Override
    public void onAddClick(int position) {
        routinePosition = position;

        //make new intent for mainclass
        intent = new Intent(this, MainActivity.class);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaPlayer.start();
        //get the position of the clicked item (index), and then get the title, desc and image of it
        intent.putExtra("title", routineList.get(position).getTitle());
        intent.putExtra("desc", routineList.get(position).getDesc());
        intent.putExtra("image", routineList.get(position).getmImageResource());
        //do the add to list method in main (it fetches the last intent)
        main.addRoutineToList();
        PreConfig.writeListInPref(getApplicationContext(), s.selectedRoutines);

        // sanna
        // calling for java's in-built calendar's current values
        year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        day = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                android.R.style.Theme_Material_Dialog_MinWidth,
                dateSet,
                year, month, day); // datePicker asks for contexts, layout/theme, ondatesetlistener & values

        datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY)); // a pop-up window
        datePicker.show();
    }
}