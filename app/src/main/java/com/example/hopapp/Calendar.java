package com.example.hopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

public class Calendar extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener dateSet; // listener used to indicate the user has finished selecting a date

    String m;   // used later as month's string value
    Button b;   // button
    ImageButton backB;
    CalendarView cv;    // calendarview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //hide the bar above this activity
        getSupportActionBar().hide();

        b = findViewById(R.id.scheduleBtn);
        backB = findViewById(R.id.rabbitBackBtn);
        cv = findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onSelectedDayChange(
                    CalendarView view,
                    int year,
                    int month,
                    int day) {

                switch (month){     // changing int month to string m (jan == 0, dec == 11 because java counts from 0)
                    case 0:
                        m = "Jan";
                        break;
                    case 1:
                        m = "Feb";
                        break;
                    case 2:
                        m = "Mar";
                        break;
                    case 3:
                        m = "Apr";
                        break;
                    case 4:
                        m = "May";
                        break;
                    case 5:
                        m = "Jun";
                        break;
                    case 6:
                        m = "Jul";
                        break;
                    case 7:
                        m = "Aug";
                        break;
                    case 8:
                        m = "Sep";
                        break;
                    case 9:
                        m = "Oct";
                        break;
                    case 10:
                        m = "Nov";
                        break;
                    case 11:
                        m = "Dec";
                        break;
                }

                Toast.makeText(Calendar.this,
                        "" + day + ". " + m + " " + year, Toast.LENGTH_SHORT).show();   // toast shows the selected date
                // MIKS TOI 0 BUGAA VÄLILLÄ????
                // toastiin myöhemmin aktiviteetin ja aktiviteetin ajankohta
                // jos näyttää liian rumalta niin laitan popup-ikkunan (koodi valmis)
            }
        });

    }

    public void openSchedule(View view){
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


/*
    public void dateSelection(View v){
        // calling for java's in-built calendar
        int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        int day = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dateSet,
                year, month, day); // datePicker asks for contexts, layout/theme, ondatesetlistener & values
        datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // a pop-up window
        datePicker.show();
    } */

}