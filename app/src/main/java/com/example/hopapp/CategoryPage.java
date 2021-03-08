package com.example.hopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hopapp.lists.anxietyList;
import com.example.hopapp.lists.challengesList;
import com.example.hopapp.lists.exerciseList;
import com.example.hopapp.lists.improvementsList;

public class CategoryPage extends AppCompatActivity {

    public Button exerciseButton;
    public Button improvementsButton;
    public Button anxietyButton;
    public Button challengesButton;
    private Button rewardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        //hide the bar above this activity
        getSupportActionBar().hide();
        assignButtons();
    }

    private void assignButtons() {
        exerciseButton = findViewById(R.id.buttonExercise);
        improvementsButton = findViewById(R.id.buttonImprovements);
        anxietyButton = findViewById(R.id.buttonAnxiety);
        challengesButton = findViewById(R.id.buttonChallenges);
        rewardButton = findViewById(R.id.buttonReward);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        exerciseButton.setOnClickListener(v -> {
            mediaPlayer.start();
            openExercisePage();
        });
        improvementsButton.setOnClickListener(v -> {
            mediaPlayer.start();
            openImprovementsPage();
        });
        anxietyButton.setOnClickListener(v -> {
            mediaPlayer.start();
            openAnxietyPage();
        });
        challengesButton.setOnClickListener(v -> {
            mediaPlayer.start();
            openChallengesPage();
        });
        rewardButton.setOnClickListener(v -> {
            mediaPlayer.start();
            claimReward();
        });

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

    public void claimReward() {
        Intent rewardIntent = new Intent(this, Reward.class);
        startActivity(rewardIntent);

    }
}
