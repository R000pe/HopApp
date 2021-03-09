package com.example.hopapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Luokka sisältää adapaterin kaikille muille, paitsi main sivun listoille
 * @author Wilma Paloranta
 * @version 1.1 3/2021
 */

public class RoutinePageAdapter extends RecyclerView.Adapter<RoutinePageAdapter.MyViewHolder> {
    private final ArrayList<Routine> mRoutineList;
    private final OnClickListener listener;

    /**
     * Uusi RecyclerViewAdapter
     * @param routineList aseta lista
     * @param listener aseta onclick listener
     */

    public RoutinePageAdapter(ArrayList<Routine> routineList, OnClickListener listener) {
        this.listener = listener;
        mRoutineList = routineList;
    }

    /**
     * MyViewHolder asettaa kaikki listalla näkyvät ja sille tulevat osat
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;
        public TextView mTextView1;
        OnClickListener onClickListener;
        public ImageView mAddImage;

        /**
         * @param itemView Luo uusi View, jolle asetetaan id:n avulla teksti, kuvat,
         *                 contraintlayout sekä onclick Listener
         */
        public MyViewHolder(View itemView, OnClickListener onClickListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.routineImageView_plus);
            mTextView1 = itemView.findViewById(R.id.routine_title_plus);
            this.onClickListener = onClickListener;
            mAddImage = itemView.findViewById(R.id.image_add);
            itemView.setOnClickListener(this);

            /**
             * Uusi onclicListener plus kuvakkeelle
             * Jos listener ei ole null ja kortin indeksi on olemassa
             * tee onAddClick methodi, joka lisää sen listaan
             */
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
        /**
         * OnClick method, listat kutsuu
         * @param v hankkii Viewin tarvitsemat parametrit
         */
        @Override
        public void onClick(View v) {
            onClickListener.onClick(getAdapterPosition());
        }
    }

    /**
     * Asettaa My_Row kortin listaan ja palauttaa sen arvon
     * @param parent hankkii layoutille kortin
     * @param viewType palauttaa rowin view tyypin
     * @return palauttaa kortin
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_with_plus, parent, false);
        return new MyViewHolder(v, listener);

    }

    /**
     * Aseta data listalle
     * @param holder luo uusi myViewHolder
     * @param position hanki kortin indeksi
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Routine currentRoutine = mRoutineList.get(position);

        holder.mImageView.setImageResource(currentRoutine.getmImageResource());
        holder.mTextView1.setText(currentRoutine.getTitle());
    }

    /**
     * Luo listan kokonainen RecyclerView Lista
     * @return palauta lista
     */
    @Override
    public int getItemCount() {
        return mRoutineList.size();
    }

    /**
     * Interface OnClickListenerille
     * hankkii kortin tiedot ja sen indeksin
     */
    public interface OnClickListener {
        void onClick(int position);
        void onAddClick(int position);
    }
}
