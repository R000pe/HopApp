package com.example.hopapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    String[] data1;
    String[] data2;
    int[] images;
    Context context;

    public RecyclerViewAdapter(Context ct, String[] s1, String[] s2, int[] img){
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;


    }

    @NonNull
    @Override
    //Instantiates a layout XML file into its corresponding View objects.
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    //bind data here
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        holder.myImage.setImageResource(images[position]);

        //apply animation to views here
        //holder.myImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_anim));

        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_anim));


        //save information from mainlayout into intent
        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, TaskViewActivity.class);
            intent.putExtra("data1", data1[position]);
            intent.putExtra("data2", data2[position]);
            intent.putExtra("myImage", images[position]);
            context.startActivity(intent);
        });
    }

    @Override
    //makes the list as long as how much there is in data1 (in the titles)
    public int getItemCount() {
        return data1.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myText1, myText2;
        ImageView myImage;
        ConstraintLayout mainLayout;
        ConstraintLayout container;

        public MyViewHolder (View itemView){
            super(itemView);
            container = itemView.findViewById(R.id.container);
            myText1 = itemView.findViewById(R.id.routine_title);
            myText2 = itemView.findViewById(R.id.routine_desc);
            myImage = itemView.findViewById(R.id.routineImageView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
