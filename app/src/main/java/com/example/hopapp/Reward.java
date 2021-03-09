package com.example.hopapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * Luokka hallinnoi Palkinto aktiviteettia
 *
 * @author Roope
 * @version 1.1
 */
public class Reward extends Activity {
    private TextView pointText;


    /**
     * Kutusutaan kun luokka luodaan. Luodaan popup ikkuna displaymetrics avulla ja maaritellaan popupikkunan koko.
     * @param savedInstanceState sisaltaa aktiviteetin tallennetun instancen
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        //Setting popup window size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        UpdateUI();
    }

    /**
     * Kun nappia painetaan haetaan sharedpreferenceista pistemaara. Tallennetaan uusi pistemaara jos vähennys tapahtui ja suljetaan aktiviteetti.
     *
     * @param view Palkinto napin view
     */
    public void onRewardButtonClicked(View view) {
        SharedPreferences score_prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int score = Integer.parseInt(score_prefs.getString("score", "0"));
        if (score >= 5) {
            score = score - 5;
            Toast.makeText(Reward.this, "Breakday claimed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Reward.this, "Can't you read. You need 5 points", Toast.LENGTH_SHORT).show();
        }
        String saveScore = Integer.toString(score);
        SharedPreferences.Editor settings_editor = score_prefs.edit();
        settings_editor.putString("score", saveScore).commit();
        UpdateUI();
        finish();
    }

    /**
     * Hakee pisteet sharedpreferenseista ja Päivittää aktiviteetissa olevan pisteview pistemaaralla.
     */
    public void UpdateUI() {
        pointText = findViewById(R.id.pointText);
        SharedPreferences score_prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String score = (score_prefs.getString("score", "0"));
        pointText.setText(score);
    }
}
