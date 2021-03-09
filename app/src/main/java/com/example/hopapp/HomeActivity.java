package com.example.hopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.TextView;

import java.util.Objects;

/**
 * Luokka sisältää tervetuloa sivun
 * @author Wilma Paloranta
 * @version 1.1 3/2021
 */

public class HomeActivity extends AppCompatActivity {
    private TextView err;
    boolean isFirstRun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*
          Luo isFirstRun arvo, jonka avulla käyttäjän tietoja kysytään vain kerran
          defValue = true
          Jos on isFirstRun on true avaa Pre_Poll aktiviteetti, josta
          sovellus kysyy käyttäjän tiedot
         */
        isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        if (isFirstRun) {
            //Open pre poll information page
            Intent Pre_PollIntent = new Intent(HomeActivity.this, Pre_Poll.class);
            startActivity(Pre_PollIntent);
        }

        Objects.requireNonNull(getSupportActionBar()).hide();

        setName();

        //Jos appia ei käynnistetä ensimmäistä kertaa,
        //avaa Main activity 4s jälkeen
        Handler handler =new Handler();
        if (!isFirstRun) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                    finish();
                }
            }, 4000);
        }
    }

    /**
     * Aseta käyttäjän pre pollissa antama nimi Welcome sivulle
     */
    @SuppressLint("SetTextI18n")
    private void setName (){
        err = findViewById(R.id.textWelcome);

        SharedPreferences name_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = name_pref.getString("full_name", "");

        err.setText("Welcome back " + name);
    }
}