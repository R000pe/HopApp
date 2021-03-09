package com.example.hopapp.lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hopapp.MainActivity;
import com.example.hopapp.PreConfig;
import com.example.hopapp.R;
import com.example.hopapp.Routine;
import com.example.hopapp.RoutinePageAdapter;
import com.example.hopapp.SelectedRoutinesSingleton;
import com.example.hopapp.TaskViewActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
/**
 * Luo luokan lista, listan aktiviteeteille asetetaan pvm
 * @author sanku, Wilma Paloranta
 * @version 1.1 03/2021
 * */
public class customList extends AppCompatActivity implements RoutinePageAdapter.OnClickListener {
    MainActivity main = new MainActivity();
    private RecyclerView recyclerViewAll;
    public ArrayList<Routine> routineList = new ArrayList<>();
    private EditText editTitle, editDesc;
    public SelectedRoutinesSingleton s = SelectedRoutinesSingleton.getInstance();
    RoutinePageAdapter reeAdapter;
    //sanna
    private int year, month, day, routinePosition; // these will be later used in datepickerdialog
    private DatePickerDialog datePicker;
    private Intent intent;
/**
* DatePickerDialogin OnDateSetListenerin avulla kayttaja valitsee aktiviteetilleen toivomansa pvm:n
 * tupsahtaneen kalenterinakyman avulla. Arvot lisataan aktiviteetille.
* */
    private DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {

            routineList.get(routinePosition).setYear(year);    // paivamaaran arvot lisätään rutiiniin
            routineList.get(routinePosition).setMonth(month);
            routineList.get(routinePosition).setDayOfMonth(day);

            intent.putExtra("YEAR", routineList.get(routinePosition).getYear());    // arvot lähetetään eteenpäin (mainactivityyn)
            intent.putExtra("MONTH", routineList.get(routinePosition).getMonth());
            intent.putExtra("DAY", routineList.get(routinePosition).getDayOfMonth());


            Toast.makeText(customList.this, "Routine added to the list!", Toast.LENGTH_SHORT).show();

            startActivity(intent);
        }
    }; // listener used to indicate the user has finished selecting a date (SANNA)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        //piilota aktiviteetin yläpuolella oleva palkki
        getSupportActionBar().hide();


        recyclerViewAll = findViewById(R.id.suggestedRecyclerView);

        //uusi adapteri
        reeAdapter = new RoutinePageAdapter(routineList, this);
        recyclerViewAll.setAdapter(reeAdapter);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(this));

        editTitle=(EditText) findViewById(R.id.editTitle);
        editDesc=(EditText) findViewById(R.id.editDesc);

        Button btAdd =(Button) findViewById(R.id.buttonCustom);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = editTitle.getText().toString();
                String newDesc = editDesc.getText().toString();
                routineList.add(new Routine(R.drawable.rabbit_running, newTitle, newDesc));
                reeAdapter.notifyDataSetChanged();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewAll);


    }
    /**
     * Luo uuden ItemTouchHelperi, jonka avulla listan kortteja voi
     * heittää vasemmalle
     */
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
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
         */
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            if (direction == ItemTouchHelper.LEFT) {//Alert dialog confirming deletion of routine on the list
                new AlertDialog.Builder(viewHolder.itemView.getContext())
                        .setTitle("Delete Routine")
                        .setMessage("Do you really want to delete this routine?")
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setPositiveButton("YES", (dialog, whichButton) -> {
                                /*User selects yes in alert dialog,
                                Task gets deleted from list and main menu.*/
                            routineList.remove(position);
                            reeAdapter.notifyItemRemoved(position);
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Routine Deleted", Snackbar.LENGTH_LONG);
                            snackbar.show();

                        })
                        .setNegativeButton("NO", (dialog, id) -> {
                                /* User cancelled the dialog,
                                 so we will refresh the adapter to prevent hiding the item from UI*/
                            reeAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        })
                        .create()
                        .show();
            }
        }
    };

    /**
     * Aseta parametrit extroihin
     * Avaa uusi intent, joka näyttää tarkemmat tiedot rutiinista
     * @param position hankkii onclick:in alaisena olevan kortin indeksin
     */
    @Override
    public void onClick(int position) {
        //make new intent for mainclass

        Intent intent = new Intent(this, TaskViewActivity.class);
        //get the position of the clicked item (index), and then get the title, desc and image of it
        intent.putExtra("title", routineList.get(position).getTitle());
        intent.putExtra("desc", routineList.get(position).getDesc());
        intent.putExtra("image", routineList.get(position).getmImageResource());
        //do the add to list method in main (it fetches the last intent)

        //start main activity. only for test purposes, to be removed later
        startActivity(intent);
    }

    /**
     * aktiviteetille valitaan pvm
     * Uusi intent Main Activitylle, joka lisää intentin avulla extroihin parametrit
     * ja addToRoutineList methodin kautta lisää ne etusivun listalle
     * @param position hakee onclick:in alaisena olevan kortin indeksin
     */

    @Override
    public void onAddClick(int position) {
        //make new intent for mainclass
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaPlayer.start();
        intent = new Intent(this, MainActivity.class);
        //get the position of the clicked item (index), and then get the title, desc and image of it
        intent.putExtra("title", routineList.get(position).getTitle());
        intent.putExtra("desc", routineList.get(position).getDesc());
        intent.putExtra("image", routineList.get(position).getmImageResource());
        //do the add to list method in main (it fetches the last intent)
        main.addRoutineToList();
        PreConfig.writeListInPref(getApplicationContext(), s.selectedRoutines);


        // javan omasta kalenterista poimitaan tämänhetkiset arvot päivälle, kuukaudelle ja vuodelle
        year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        day = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                android.R.style.Theme_Material_Dialog_MinWidth,
                dateSet,
                year, month, day); // datePicker asks for contexts, layout/theme, ondatesetlistener & values

        datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY)); // a pop-up window
        datePicker.show();
    }
}