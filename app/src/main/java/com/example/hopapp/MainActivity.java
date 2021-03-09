package com.example.hopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//This is the main page with recycleView.
// RecycleView is similar to list view, but more flexible (animations, etc).

/**
 * Luokka sisältää pääsivun listan ja muun sisällön luomiseen liittyvöt osat
 * @author Wilma Paloranta, sanku, Roope Sarasoja
 * @version 1.1 3/2021
 */


public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    RecyclerViewAdapter myAdapter;
    public SelectedRoutinesSingleton s = SelectedRoutinesSingleton.getInstance();
    private RecyclerViewAdapter.RecyclerViewClickListener listener;

    public ImageButton mainMenuButton;
    public ImageButton taskButton;
    public ImageButton calendarButton;
    SwipeRefreshLayout swipeRefreshLayout;

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //piilottaa palikan activityn yläpuolelta
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = findViewById(R.id.recyclerView);

        /**
         * Preference configuration luokan methodin avulla tallennetu lista tuodaan takaisin
         * jos listaa ei ole se luodaan
         */
        s.selectedRoutines = PreConfig.readListFromPref(this);
        if (s.selectedRoutines == null)
            s.selectedRoutines = new ArrayList<>();

        adapterMethod();
        updateMessage();
        assignButtons();


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        PreConfig.writeListInPref(getApplicationContext(), s.selectedRoutines);

        //Notification Volume. Checks settings for true or false
        SharedPreferences scorePrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean notificationsMute = scorePrefs.getBoolean("notification_mute", true);
        AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (notificationsMute) {
            amanager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
        } else {
            amanager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
        }

        /**
         * luo uuden swipe refresh layout
         * ilmoittaa adapteriin datan "muutoksesta"
         * muuttaa refreshing pois päältä
         */

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    /**
     * luo uusi adapteri tälle luokalle ja recycler view listalle
     * adapteri ottaa tämän luokan singletonin listan, ja onclick listenerin
     * aseta layoutmanageri tälle luokalle
     */
    private void adapterMethod() {
        setOnClickListener();
        myAdapter = new RecyclerViewAdapter(this, (ArrayList<Routine>) s.getSelectedRoutines(), listener); //täällä luki routineList
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     *Luo on click listener luokalle
     * Uusi mediaplayer napsahdus ääntä varten
     * Public void int position kertoo klickatun kortin indeksin
     * Kyseisen indeksin kortista etsitään title, desc ja image tiedot, jotka lisätään
     * intentin extraan
     * Aloita uusi activiteetti
     */
    private void setOnClickListener() {
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        listener = new RecyclerViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                mediaPlayer.start();
                Intent intent = new Intent(getApplicationContext(), TaskViewActivity.class);
                intent.putExtra("title", s.selectedRoutines.get(position).getTitle());
                intent.putExtra("desc", s.selectedRoutines.get(position).getDesc());
                intent.putExtra("image", s.selectedRoutines.get(position).getmImageResource());
                startActivity(intent);
            }
        };
    }

    /**
     * Jos Main Activityn listalla ei ole mitään
     * Päivittyy sivulle "No routines set yet" teksti
     * Muuten teksti on tyhjä
     */
    private void updateMessage() {
        TextView textNull = (TextView) findViewById(R.id.nullText);
        if (s.getSelectedRoutines().size() <= 0) { //täällä luki routineList.size()
            textNull.setText("No routines set yet");
        } else {
            textNull.setText("");
        }
    }

    /**
     * Aseta sivun kaikki napit, niille media player
     * sekä uuden aktiviteetin avaamismethodi
     */
    private void assignButtons() {
        mainMenuButton = findViewById(R.id.mainMenuButton);
        taskButton = findViewById(R.id.taskButton);
        calendarButton = findViewById(R.id.calendarButton);

        //aseta ääni napeille
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                openActivityMainMenu();
            }
        });
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                openCalendar();
            }
        });
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                openCategoryPage();
            }
        });

    }

    /**
     * Luo uuden ItemTouchHelperi, jonka avulla listan kortteja voi
     * heittää oikealle tai vasemmalle
     */
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
            ItemTouchHelper.RIGHT) {
        @Override
        //this does nothing for now
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        /**
         *onSwiped viewholder etsii adapterin kautta käytössä olevan kortin indeksin
         *  int Direction valitsee suunnan mihin korttia siirretään
         * @param viewHolder kortin viewholderin avulla saadaan indeksi, int position
         * @param direction int tarkastelee mihin suuntaan korttia vedetään
         * Luo uusi AlertDialog, joka varmistaa haluatko poistaa kyseisen tehtävän
         * Vasemmalle vedettäessä kortti poistetaan, ja kysytään varmistusta
         * Oikealle vedettäessä kortti poistetaan, mutta käyttäjä saa myös pisteen tehtävän
         * suorittamisesta
         * Tee aina updateMessage() methodi sen varalta, että lista tyhjenee
         */
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    //Alert dialog confirming deletion of routine on the list
                    new AlertDialog.Builder(viewHolder.itemView.getContext())
                            .setTitle("Delete Routine")
                            .setMessage("Do you really want to delete this routine?")
                            .setIcon(android.R.drawable.ic_dialog_alert)

                            .setPositiveButton("YES", (dialog, whichButton) -> {
                                /*User selects yes in alert dialog,
                                Task gets deleted from list and main menu.*/
                                s.selectedRoutines.remove(position);
                                myAdapter.notifyItemRemoved(position);
                                PreConfig.writeListInPref(getApplicationContext(), s.selectedRoutines);
                                updateMessage();

                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Routine Deleted", Snackbar.LENGTH_LONG);
                                snackbar.show();

                            })
                            .setNegativeButton("NO", (dialog, id) -> {
                                /* User cancelled the dialog,
                                 so we will refresh the adapter to prevent hiding the item from UI*/
                                myAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            })
                            .create()
                            .show();
                    break;

                case ItemTouchHelper.RIGHT:
                    s.selectedRoutines.remove(position);
                    myAdapter.notifyItemRemoved(position);
                    PreConfig.writeListInPref(getApplicationContext(), s.selectedRoutines);
                    //Get score from sharedprferences ad 1 point and save score
                    SharedPreferences scorePrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    int score = Integer.parseInt(scorePrefs.getString("score", "0"));
                    score++;
                    String saveScore = Integer.toString(score);
                    SharedPreferences.Editor settings_editor = scorePrefs.edit();
                    settings_editor.putString("score", saveScore).commit();
                    updateMessage();
                    break;
            }
        }
    };

    /**
     * Luo methodit uusien activiteettien avaamiseen
     */

    public void openActivityMainMenu() {
        Intent mainMenuIntent = new Intent(this, Settings.class);
        startActivity(mainMenuIntent);
    }

    public void openCategoryPage() {
        Intent categoryPage = new Intent(this, CategoryPage.class);
        startActivity(categoryPage);
    }

    public void openCalendar() {
        Intent calendarIntent = new Intent(this, Calendar.class);
        startActivity(calendarIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        extras = getIntent().getExtras();
        addRoutineToList();
    }

    /**
     * Jos extrat (jotka saadaan muista listoista) ei ole tyhjä
     * Aseta extrasta saadut tiedot uusiin parametreihin
     *
     * Ota singletonin instanssi ja lisää singleton listaan extroista saadut tiedot
     * Tallenna pref Configiin
     * Päivitä tyhjän listan varoitus viesti
     */
    public void addRoutineToList() {

        //if extras, aka intent isn't empty
        if (extras != null) {

            String title;
            String desc;
            int image;
            int year, month, day;

            //get intent and assign them to parameters
            title = extras.getString("title");
            desc = extras.getString("desc");
            image = extras.getInt("image");
            year = extras.getInt("YEAR");
            month = extras.getInt("MONTH");
            day = extras.getInt("DAY");

            int y = 0;

            while (y == 0) {
                Routine r = new Routine(image, title, desc);

                // käynnissäolevan whileloopin jälkeen objektilla r ei ole enää referenssiä & garbage collector siivoaa sen pois
                for (int i = 0; i < s.getSelectedRoutines().size(); i++) {  //  for-silmukan avulla kuljetaan ainokaisen selectedroutines-lista läpi
                    if (s.getSelectedRoutines().get(i).getTitle().equals(r.getTitle())) { // jos samanniminen objekti löytyy listalta ..
                        y = 2;
                        break; // .. for-silmukka lopetetaan
                    }
                }

                if (y == 2) { // mikäli edellinen for-silmukka muunsi y-parametrin arvoksi 2
                    break; // .. whileloop keskeytetään
                }

                y = 1; // mikäli samanlaista objektia singletonin listasta ei löydy, y = 1
            } // while-silmukka päättyy tässä

            if (y == 1) { // jos edellsen while-silmukan jälkeen y:n arvo on 1, uusi objekti luodaan & lisätään singletonin listaan

                s.getInstance().getSelectedRoutines().add(new Routine(image, title, desc, year, month, day));
                PreConfig.writeListInPref(getApplicationContext(), s.getSelectedRoutines());
            }

            //update the message so it disappears
            updateMessage();
        }
    }
}