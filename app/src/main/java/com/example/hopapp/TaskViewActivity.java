package com.example.hopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//this is a new activity, which opens when a task on the list is clicked
//gives a more detailed description about the task

public class TaskViewActivity extends AppCompatActivity {

    //make parameters for the page's content
    ImageView detailImageView;
    TextView detailTitleTextView, detailDescTextView;

    String data1, data2;
    int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        //set parameters
        detailImageView = findViewById(R.id.detailImageView);
        detailTitleTextView = findViewById(R.id.detailTitleTextView);
        detailDescTextView = findViewById(R.id.detailDescTextView);

        //call functions to get data and then set it
        getData();
        setData();
    }

    private void getData(){
        //if intent has extra with the specific name, set it into parameters
        if(getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2")){

            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            myImage = getIntent().getIntExtra("myImage", 1);
        }else{
            //if data isn't found for some reason, give an error
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    //set the data that we got from intent
    private void setData(){
        detailTitleTextView.setText(data1);
        detailDescTextView.setText(data2);
        detailImageView.setImageResource(myImage);
    }
}