package com.example.hopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.hopapp.lists.anxietyList;
import com.example.hopapp.lists.challengesList;
import com.example.hopapp.lists.exerciseList;
import com.example.hopapp.lists.improvementsList;

public class CategoryPage extends AppCompatActivity {

    public Button exerciseButton;
    public Button improvementsButton;
    public Button anxietyButton;
    public Button challengesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        assignButtons();
    }

    private void assignButtons(){
        exerciseButton = findViewById(R.id.buttonExercise);
        improvementsButton = findViewById(R.id.buttonImprovements);
        anxietyButton = findViewById(R.id.buttonAnxiety);
        challengesButton = findViewById(R.id.buttonChallenges);

        exerciseButton.setOnClickListener(v -> openExercisePage());
        improvementsButton.setOnClickListener(v -> openImprovementsPage());
        anxietyButton.setOnClickListener(v -> openAnxietyPage());
        challengesButton.setOnClickListener(v -> openChallengesPage());

    }

    public void openExercisePage() {
        Intent exerciseIntent = new Intent(this, exerciseList.class);
        startActivity(exerciseIntent);
    }

    public void openImprovementsPage() {
        Intent improvementIntent = new Intent(this, improvementsList.class);
        startActivity(improvementIntent);
    }

    public void openAnxietyPage() {
        Intent anxietyIntent = new Intent(this, anxietyList.class);
        startActivity(anxietyIntent);
    }

    public void openChallengesPage() {
        Intent challengeIntent = new Intent(this, challengesList.class);
        startActivity(challengeIntent);
    }
}