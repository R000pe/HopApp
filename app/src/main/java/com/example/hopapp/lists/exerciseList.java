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

public class exerciseList extends AppCompatActivity implements RoutinePageAdapter.OnClickListener {
    MainActivity main = new MainActivity();
    private RecyclerView recyclerViewAll;
    ArrayList<Routine> routineList = new ArrayList<>();
    public SelectedRoutinesSingleton s = SelectedRoutinesSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        recyclerViewAll = findViewById(R.id.exerciseRecycleView);

        //this is the list
        routineList.add( new Routine(R.drawable.rabbit_stretch,"Stretch", "Stretch for 10 minutes"));
        routineList.add(new Routine(R.drawable.z_routine_run,"Go for a run", "Run for 10 minutes"));
        routineList.add(new Routine(R.drawable.z_routine_pushup,"Pushups", "Do 20 pushups"));
        routineList.add(new Routine(R.drawable.z_routine_walk,"Walk in a park", "Go for a walk in the middle of nature"));
        routineList.add(new Routine(R.drawable.z_routine_swim,"Fish like trish", "Go for a swim"));

        //new adapter for recycle view
        RoutinePageAdapter reeAdapter = new RoutinePageAdapter(routineList, this);
        recyclerViewAll.setAdapter(reeAdapter);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(this));
    }

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