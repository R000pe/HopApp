package com.example.hopapp;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<Routine> mRoutineList;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public RecyclerViewAdapter(ArrayList<Routine> routineList){
        mRoutineList = routineList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mAddRoutine;

        //viewholder makes the actual view, so here we set the text and image id
        public MyViewHolder (View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.routineImageView);
            mTextView1 = itemView.findViewById(R.id.routine_title);
            mTextView2 = itemView.findViewById(R.id.routine_desc);
            //this is the plus sign, needs to be removed later from this adapter
            mAddRoutine = itemView.findViewById(R.id.imageAdd);

            //itemView.setOnClickListener(this);
        }

    }


    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        MyViewHolder evh = new MyViewHolder(v);
        return evh;
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Routine currentRoutine = mRoutineList.get(position);

        holder.mImageView.setImageResource(currentRoutine.getmImageResource());
        holder.mTextView1.setText(currentRoutine.getText1());
        holder.mTextView2.setText(currentRoutine.getText2());
    }

    //makes the right size list
    public int getItemCount() {
        return mRoutineList.size();
    }


}
