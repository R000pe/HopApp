package com.example.hopapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//this is almost identical adapter to the recyclerviewadapter, but this one works a little different compared to the other one for now
//so its better to have different adapters for now to avoid crashes

public class RoutinePageAdapter extends RecyclerView.Adapter<RoutinePageAdapter.MyViewHolder> {
    private ArrayList<Routine> mRoutineList;
    private OnClickListener listener;

    public RoutinePageAdapter(ArrayList<Routine> routineList, OnClickListener listener) {
        this.listener = listener;
        mRoutineList = routineList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mAddRoutine;
        OnClickListener onClickListener;
        public ImageView mAddImage;

        public MyViewHolder(View itemView, OnClickListener onClickListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.routineImageView_plus);
            mTextView1 = itemView.findViewById(R.id.routine_title_plus);
            //mTextView2 = itemView.findViewById(R.id.routine_desc);
            this.onClickListener = onClickListener;
            mAddImage = itemView.findViewById(R.id.image_add);

            itemView.setOnClickListener(this);

            mAddImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onAddClick(position);
                        }
                    }
                }
            });
        }



        @Override
        public void onClick(View v) {
            onClickListener.onClick(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_with_plus, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v, listener);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Routine currentRoutine = mRoutineList.get(position);

        holder.mImageView.setImageResource(currentRoutine.getmImageResource());
        holder.mTextView1.setText(currentRoutine.getTitle());
        //holder.mTextView2.setText(currentRoutine.getDesc());
    }

    @Override
    public int getItemCount() {
        return mRoutineList.size();
    }

    //make a onclicklistener
    public interface OnClickListener {
        void onClick(int position);
        void onAddClick(int position);
    }


}
