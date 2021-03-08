package com.example.hopapp.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.hopapp.MainActivity;
import com.example.hopapp.PreConfig;
import com.example.hopapp.R;
import com.example.hopapp.Routine;
import com.example.hopapp.RoutinePageAdapter;
import com.example.hopapp.SelectedRoutinesSingleton;

import java.util.ArrayList;

public class anxietyList extends AppCompatActivity implements RoutinePageAdapter.OnClickListener {
    MainActivity main = new MainActivity();
    private RecyclerView recyclerViewAll;

    //sna

    private DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            System.out.println("olemme täällä");
            System.out.println("day " + day + " month " + month);


            routineList.get(routinePosition).setYear(year);    // setting the date
            routineList.get(routinePosition).setMonth(month);
            routineList.get(routinePosition).setDay(day);

            startActivity(intent);
        }
    }; // listener used to indicate the user has finished selecting a date (SANNA)

    private DatePickerDialog datePicker;
    ArrayList<Routine> routineList = new ArrayList<>();
    public SelectedRoutinesSingleton s = SelectedRoutinesSingleton.getInstance();
    private int year, month, day, routinePosition = 0;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anxiety_list);

        recyclerViewAll = findViewById(R.id.anxietyRecycleView);

        //this is the list
        routineList.add(new Routine(R.drawable.default_logo, "Breath", "Breath slowly in and out for 3 to 5 minutes"));
        routineList.add(new Routine(R.drawable.default_logo, "Talk about your feelings", "Try talking about your feelings to a friend, family or professional"));
        routineList.add(new Routine(R.drawable.default_logo, "Exercise", "Activities such as running, yoga and swimming can help you relax"));
        routineList.add(new Routine(R.drawable.default_logo, "Take a rest", "Try taking a nap"));
        routineList.add(new Routine(R.drawable.default_logo, "Regular diet", "Try to keep up a regular and healthy diet"));
        routineList.add(new Routine(R.drawable.default_logo, "Write it down", "Write down your emotions for better understanding"));
        routineList.add(new Routine(R.drawable.default_logo, "Mindfulness", "Practice mindfulness"));

        //new adapter for recycle view
        RoutinePageAdapter reeAdapter = new RoutinePageAdapter(routineList, this);
        recyclerViewAll.setAdapter(reeAdapter);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(int position) {

        routinePosition = position;

        //make new intent for mainclass
        intent = new Intent(this, MainActivity.class);
        //get the position of the clicked item (index), and then get the title, desc and image of it
        intent.putExtra("title", routineList.get(position).getText1());
        intent.putExtra("desc", routineList.get(position).getText2());
        intent.putExtra("image", routineList.get(position).getmImageResource());
        //do the add to list method in main (it fetches the last intent)
        main.addRoutineToList();

        PreConfig.writeListInPref(getApplicationContext(), s.selectedRoutines);

        //start main activity. only for test purposes, to be removed later (voidaanko jättää pois? plees?)
        //startActivity(intent);
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

        datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // a pop-up window
        datePicker.show();


    }
}