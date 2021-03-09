package com.example.hopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Luokka luo akitiveetin, josta käyttäjä saa tarkempia tietoja rutiinista
 * @author Wilma Paloranta
 * @version 1.1 3/2021
 */

public class TaskViewActivity extends AppCompatActivity {

    //luo parametrit
    ImageView detailImageView;
    TextView detailTitleTextView, detailDescTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        /*
        Aseta parametrit id:n avulla,
        Extran kautta saadaan niille uuden arvot, jotka asetetaan,
        jos extrat eivät ole null
         */
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
