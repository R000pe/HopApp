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
 * Luo anxiety luokan lista ja siihen liittyvät methodit
 * @author sanku, Wilma Paloranta
 * @version 1.1 03/2021
 * */
public class challengesList extends AppCompatActivity implements RoutinePageAdapter.OnClickListener {
    MainActivity main = new MainActivity();
    private RecyclerView recyclerViewAll;
    ArrayList<Routine> routineList = new ArrayList<>();
    public SelectedRoutinesSingleton s = SelectedRoutinesSingleton.getInstance();
    //sanna
    private int year, month, day, routinePosition; // these will be later used in datepickerdialog
    private DatePickerDialog datePicker;
    private Intent intent;

    private DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {

            routineList.get(routinePosition).setYear(year);    // setting the date into the routine
            routineList.get(routinePosition).setMonth(month);
            routineList.get(routinePosition).setDayOfMonth(day);

            System.out.println(routineList.get(routinePosition).getDateFull());

            System.out.println(routineList.get(routinePosition));

            intent.putExtra("YEAR", routineList.get(routinePosition).getYear());
            intent.putExtra("MONTH", routineList.get(routinePosition).getMonth());
            intent.putExtra("DAY", routineList.get(routinePosition).getDayOfMonth());

            Toast.makeText(challengesList.this, "Routine added to the list!", Toast.LENGTH_SHORT).show();

            startActivity(intent);
        }
    }; // listener used to indicate the user has finished selecting a date (SANNA)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_list);
        //piilota aktiviteetin yläpuolella oleva palkko
        getSupportActionBar().hide();

        recyclerViewAll = findViewById(R.id.challengesRecyclerView);

        //Lisää nämä listalle
        routineList.add( new Routine(R.drawable.x_routine_nosugar,"Sugar break", "Don't eat excess sugar for a week"));
        routineList.add( new Routine(R.drawable.x_routine_noscreen,"Maybe impossible", "Lessen your screen time to 2h a day"));
        routineList.add( new Routine(R.drawable.x_routine_posture,"Bad back", "Mind your posture"));
        routineList.add( new Routine(R.drawable.x_routine_poet,"A poet", "Write a poem"));


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
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaPlayer.start();
        Intent intent = new Intent(this, TaskViewActivity.class);
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
     * ja addToRoutineList methodin kautta lisää ne etusivun listalle
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