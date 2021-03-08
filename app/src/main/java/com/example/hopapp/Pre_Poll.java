package com.example.hopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Pre_Poll extends AppCompatActivity {
    private EditText TextName, TextAge;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre__poll);
    }

    //Ad User information to Settings
    protected void onPause() {
        super.onPause();
        SharedPreferences settings_prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        TextName = findViewById(R.id.textFullName);
        TextAge = findViewById(R.id.textBirhtDate);


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();



        String name = TextName.getText().toString();
        String age = TextAge.getText().toString();

        SharedPreferences.Editor settings_editor = settings_prefs.edit();
        settings_editor.putString("full_name", name).commit();
        settings_editor.putString("age", age).commit();
    }


    public void onButtonCLicked(View view) {
        startActivity(new Intent(Pre_Poll.this, MainActivity.class));
        finish();
    }
}