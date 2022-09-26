package com.example.fbifitness;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Exercise_Adaptor extends RecyclerView.Adapter<Exercise_Adaptor.MyViewHolder> {
    Context context;
    ArrayList<SetListView> setList; // Dummy data, will be replaced with workout objects
    ArrayList<TextView> editSetButtons;
    Exercise exercise;
    int adaptorPosition;


    public Exercise_Adaptor(Context context, ArrayList<SetListView> setList, Exercise exercise, int adaptorPosition) {
        this.context = context;
        this.setList = setList;
        this.exercise = exercise;
        this.adaptorPosition = adaptorPosition;
        editSetButtons = new ArrayList<TextView>();
    }


    @NonNull
    @Override
    public Exercise_Adaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where the layout is inflated (and giving a look to each row)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exercise_set_entry, parent, false);


        TextView editSetButton = view.findViewById(R.id.EditSetText);
        editSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = parent.getChildCount();
                CurrentWorkoutFragment.editSet(position- 1, exercise, adaptorPosition); // TODO NOT WORKING - POSITION DATA
//                setList.get(position).setSetWeight("500");
//                setList.get(position).setSetReps("100");
//                Log.d("setList", setList.get(position).getSetWeight());
//                //notify the adapter that the data has changed
//                notifyDataSetChanged();
            }});
        editSetButtons.add(editSetButton);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Exercise_Adaptor.MyViewHolder holder, int position) {
        // Assigning values to each row in the workout_exercise_entry layout
        // Based on the position of the row
        holder.setNumber.setText("Set " + position);
        holder.setReps.setText(setList.get(position).getSetReps());
        holder.setWeight.setText(setList.get(position).getSetWeight() + " kg");
    }

    @Override
    public int getItemCount() {
        // Returns the number of rows in the recycler view
        return setList.size();
    }

    // This is the view holder class, kind of like onCreate
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView setNumber;
        TextView setWeight;
        TextView setReps;
        TextView newSetButton;
        // Add a button for edit set later

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            setNumber = itemView.findViewById(R.id.setNumberText);
            setWeight = itemView.findViewById(R.id.setWeightText);
            setReps = itemView.findViewById(R.id.setRepsText);
        }
    }
}
