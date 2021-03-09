package com.example.hopapp;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;

/**
 * Luokka sisältää adapaterin Main Activityn listaa varten
 * @author Wilma Paloranta
 * @version 1.1 3/2021
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private final ArrayList<Routine> mRoutineList;
    private final RecyclerViewClickListener listener;
    Context mcontext;

    /**
     * Uusi RecyclerViewAdapter
     * @param mcontext hanki aktiviteetin contexti
     * @param routineList aseta lista
     * @param listener aseta onclick listener
     */
    public RecyclerViewAdapter(Context mcontext, ArrayList<Routine> routineList, RecyclerViewClickListener listener){
        this.mcontext = mcontext;
        mRoutineList = routineList;
        this.listener = listener;
    }

    /**
     * MyViewHolder asettaa kaikki listalla näkyvät ja sille tulevat osat
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView mImageView;
        public TextView mTextView1;
        ConstraintLayout container;


        /**
         * @param itemView Luo uusi View, jolle asetetaan id:n avulla teksti, kuvat,
         *                 contraintlayout sekä onclick Listener
         */
        public MyViewHolder (View itemView){
            super(itemView);
            container = itemView.findViewById(R.id.container);
            mImageView = itemView.findViewById(R.id.routineImageView);
            mTextView1 = itemView.findViewById(R.id.routine_title);
            itemView.setOnClickListener(this);
        }

        /**
         * OnClick method, jota activity main kutsuu
         * @param v hankkii Viewin tarvitsemat parametrit
         */
        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }

    /**
     * Asettaa My_Row kortin listaan ja palauttaa sen arvon
     * @param parent hankkii layoutille kortin
     * @param viewType palauttaa rowin view tyypin
     * @return palauttaa kortin
     */
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        MyViewHolder evh = new MyViewHolder(v);
        return evh;
    }

    /**
     * Aseta animaatio listalle/sen containerille (contraintLayout)
     * Aseta data listalle
     * @param holder luo uusi myViewHolder
     * @param position hanki kortin indeksi
     */
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //bind data here
        //apply animation to views here
        holder.container.setAnimation(AnimationUtils.loadAnimation(mcontext,R.anim.itemanimation));

        Routine currentRoutine = mRoutineList.get(position);
        holder.mImageView.setImageResource(currentRoutine.getmImageResource());
        holder.mTextView1.setText(currentRoutine.getTitle());
        //holder.mTextView2.setText(currentRoutine.getDesc());
    }

    /**
     * Luo listan kokonainen RecyclerView Lista
     * @return palauta lista
     */
    public int getItemCount() {
        return mRoutineList.size();
    }

    /**
     * Interface OnClickListenerille
     * hankkii kortin tiedot ja sen indeksin
     */
    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

}
