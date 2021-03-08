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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<Routine> mRoutineList;
    private RecyclerViewClickListener listener;
    Context mcontext;


    public RecyclerViewAdapter(Context mcontext, ArrayList<Routine> routineList, RecyclerViewClickListener listener){
        this.mcontext = mcontext;
        mRoutineList = routineList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mAddRoutine;
        ConstraintLayout container;


        //viewholder makes the actual view, so here we set the text and image id
        public MyViewHolder (View itemView){
            super(itemView);
            container = itemView.findViewById(R.id.container);
            mImageView = itemView.findViewById(R.id.routineImageView);
            mTextView1 = itemView.findViewById(R.id.routine_title);
            //mTextView2 = itemView.findViewById(R.id.routine_desc);
            //this is the plus sign, needs to be removed later from this adapter
            itemView.setOnClickListener(this);

            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }


    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        MyViewHolder evh = new MyViewHolder(v);
        return evh;
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //bind data here
        //apply animation to views here
        holder.container.setAnimation(AnimationUtils.loadAnimation(mcontext,R.anim.itemanimation));

        Routine currentRoutine = mRoutineList.get(position);
        holder.mImageView.setImageResource(currentRoutine.getmImageResource());
        holder.mTextView1.setText(currentRoutine.getTitle());
        //holder.mTextView2.setText(currentRoutine.getDesc());
    }

    //makes the right size list
    public int getItemCount() {
        return mRoutineList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

}
