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

public class challengesList extends AppCompatActivity implements RoutinePageAdapter.OnClickListener {
    MainActivity main = new MainActivity();
    private RecyclerView recyclerViewAll;
    ArrayList<Routine> routineList = new ArrayList<>();
    public SelectedRoutinesSingleton s = SelectedRoutinesSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges_list);

        recyclerViewAll = findViewById(R.id.challengesRecyclerView);

        //this is the list
        routineList.add( new Routine(R.drawable.default_logo,"Sugar break", "Don't eat excess sugar for a week"));
        routineList.add( new Routine(R.drawable.default_logo,"Maybe impossible", "Lessen your screen time to 2h a day"));
        routineList.add( new Routine(R.drawable.default_logo,"Bad back", "Mind your posture"));
        routineList.add( new Routine(R.drawable.default_logo,"A poet", "Write a poem"));


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