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

//all the lists have the same code, apart from different recycler view id and list of course
//so all the comments for lists will be in this one only
public class anxietyList extends AppCompatActivity implements RoutinePageAdapter.OnClickListener {
    //new main activity, to bypass static and nonstatic error
    MainActivity main = new MainActivity();
    private RecyclerView recyclerViewAll;
    ArrayList<Routine> routineList = new ArrayList<>();
    //make a new singleton
    public SelectedRoutinesSingleton s = SelectedRoutinesSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anxiety_list);
        //set the recycler view
        recyclerViewAll = findViewById(R.id.anxietyRecycleView);

        //this is the list
        routineList.add( new Routine(R.drawable.default_logo,"Breath", "Breath slowly in and out for 3 to 5 minutes"));
        routineList.add(new Routine(R.drawable.default_logo,"Talk about your feelings", "Try talking about your feelings to a friend, family or professional"));
        routineList.add(new Routine(R.drawable.default_logo,"Exercise", "Activities such as running, yoga and swimming can help you relax"));
        routineList.add(new Routine(R.drawable.default_logo,"Take a rest", "Try taking a nap"));
        routineList.add(new Routine(R.drawable.default_logo,"Regular diet", "Try to keep up a regular and healthy diet"));
        routineList.add(new Routine(R.drawable.default_logo,"Write it down", "Write down your emotions for better understanding"));
        routineList.add(new Routine(R.drawable.default_logo,"Mindfulness", "Practice mindfulness"));

        //new adapter for recycle view
        RoutinePageAdapter reeAdapter = new RoutinePageAdapter(routineList, this);
        recyclerViewAll.setAdapter(reeAdapter);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    //what happens when a card gets picked (int position gets the index of the item on list)
    public void onClick(int position) {
        //make new intent for mainclass
        Intent intent = new Intent(this, MainActivity.class);
        //get the position of the clicked item (index), and then get the title, desc and image of it
        intent.putExtra("title", routineList.get(position).getText1());
        intent.putExtra("desc", routineList.get(position).getText2());
        intent.putExtra("image", routineList.get(position).getmImageResource());
        //do the add to list method in main (it fetches the last intent)
        main.addRoutineToList();
        //save to prefs
        PreConfig.writeListInPref(getApplicationContext(), s.selectedRoutines);

        //start main activity. only for test purposes, to be removed later
        startActivity(intent);
    }
}