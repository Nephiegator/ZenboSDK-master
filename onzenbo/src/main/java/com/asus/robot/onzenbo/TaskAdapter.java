package com.asus.robot.onzenbo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.remtaskViewHolder> {

    private Context mCtx;
    private List<dbReminder> remtasklist;

    public TaskAdapter (Context mCtx, List<dbReminder> remtasklist) {
        this.mCtx = mCtx;
        this.remtasklist = remtasklist;
    }


    @NonNull
    @Override
    public remtaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new remtaskViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.list_itemtask,parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull remtaskViewHolder holder, int position) {
        dbReminder reminder = remtasklist.get(position);


        holder.textViewTitle.setText(reminder.getTitle());
        holder.textViewDecs.setText("   "+reminder.getDesc());
        holder.textViewLoc.setText("At room: " + reminder.getInLocation());
        holder.textViewPer.setText("To: " + reminder.getObjPerson());
        holder.textViewTime.setText("At Time: " + reminder.getTime() );


    }

    @Override
    public int getItemCount() {
        return remtasklist.size();
    }

    class remtaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewTitle, textViewDecs, textViewLoc, textViewPer, textViewTime;


        public remtaskViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textView_title);
            textViewDecs = itemView.findViewById(R.id.textView_desc);
            textViewLoc = itemView.findViewById(R.id.textView_loc);
            textViewPer = itemView.findViewById(R.id.textView_per);
            textViewTime = itemView.findViewById(R.id.textView_time);


            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            dbReminder tt = remtasklist.get(getAdapterPosition());
            //Intent intent = new Intent(mCtx, UpdateTask.class);
            //intent.putExtra("Reminder", tt);
            //mCtx.startActivity(intent);
        }
    }


}