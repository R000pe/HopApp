package com.example.hopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        setName();

        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
            }
        }, 30);
        //later should be put to 4000 (4s)
    }

    @SuppressLint("SetTextI18n")
    private void setName (){
        err = findViewById(R.id.textWelcome);

        SharedPreferences name_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = name_pref.getString("full_name", "");

        err.setText("Welcome back " + name);
    }
}