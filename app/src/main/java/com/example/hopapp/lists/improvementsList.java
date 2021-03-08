package com.example.hopapp.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.hopapp.MainActivity;
import com.example.hopapp.PreConfig;
import com.example.hopapp.R;
import com.example.hopapp.Routine;
import com.example.hopapp.RoutinePageAdapter;
import com.example.hopapp.SelectedRoutinesSingleton;

import java.util.ArrayList;

public class improvementsList extends AppCompatActivity implements RoutinePageAdapter.OnClickListener {

    //we need to make new main, because main's list is nonstatic, so you cant reference its methods without this
    MainActivity main = new MainActivity();
    private RecyclerView recyclerViewAll;
    ArrayList<Routine> routineList = new ArrayList<>();
    public SelectedRoutinesSingleton s = SelectedRoutinesSingleton.getInstance();

    //make buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remembers the last instance state
        super.onCreate(savedInstanceState);
        //we're using the main page activity
        setContentView(R.layout.activity_improvements_list);

        //set the recycle view into a parameter
        recyclerViewAll = findViewById(R.id.improvementsRecycleView);

        //this is the list
        routineList.add( new Routine(R.drawable.z_routine_water,"Hydrohomie", "Drink a glass of water every 2 hours"));
        routineList.add(new Routine(R.drawable.z_routine_todo,"To-Do list", "Write your to-do list for the day"));
        routineList.add(new Routine(R.drawable.z_routine_bedtime,"Bedtime", "Go to sleep at 10"));
        routineList.add(new Routine(R.drawable.z_routine_cooking,"Master Chef", "Make dinner"));
        routineList.add(new Routine(R.drawable.z_routine_cleaning,"Viscera's cleanup crew", "Tidy up your room/house"));
        routineList.add(new Routine(R.drawable.z_routine_study,"Intelligence +1", "Study for 2 hours"));
        routineList.add(new Routine(R.drawable.z_routine_read,"Bookworm", "Read a chapter of a book"));
        routineList.add(new Routine(R.drawable.z_routine_create,"Bob Ross", "Do something creative"));

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
        PreConfig.writeListInPref(getApplicationContext(), s.selectedRoutines);

        //start main activity. only for test purposes, to be removed later
        startActivity(intent);


    }
}