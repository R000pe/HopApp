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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        detailTitleTextView = findViewById(R.id.detailTitleTextView);
        detailDescTextView = findViewById(R.id.detailDescTextView);
        detailImageView = findViewById(R.id.detailImageView);

        String title = "Title not set";
        String desc = "Desc not set";
        int image = -1;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            title = extras.getString("title");
            desc = extras.getString("desc");
            image = extras.getInt("image");

        }

        detailTitleTextView.setText(title);
        detailDescTextView.setText(desc);
        detailImageView.setImageResource(image);

    }
}
