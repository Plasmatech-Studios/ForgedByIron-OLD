package com.example.fbifitness;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Exercise_Adaptor extends RecyclerView.Adapter<Exercise_Adaptor.MyViewHolder> {
    Context context;
    ArrayList<SetListView> setList; // Dummy data, will be replaced with workout objects
    ArrayList<TextView> editSetButtons;
    ArrayList<Switch> completeSetSwitches;
    ArrayList<LinearLayout> weightLayout;
    ArrayList<LinearLayout> repsLayout;
    ArrayList<TextView> setNumbers;
    Exercise exercise;
    int adaptorPosition;
    Exercise_Adaptor exercise_adaptor;


    public Exercise_Adaptor(Context context, ArrayList<SetListView> setList, Exercise exercise, int adaptorPosition) {
        this.context = context;
        this.setList = setList;
        this.exercise = exercise;
        this.adaptorPosition = adaptorPosition;
        editSetButtons = new ArrayList<TextView>();
        weightLayout = new ArrayList<LinearLayout>();
        repsLayout = new ArrayList<LinearLayout>();
        setNumbers = new ArrayList<TextView>();
        completeSetSwitches = new ArrayList<Switch>();
        this.exercise_adaptor = this;
    }


    @NonNull
    @Override
    public Exercise_Adaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where the layout is inflated (and giving a look to each row)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exercise_set_entry, parent, false);

        int setPosition = parent.getChildCount();
        TextView editSetButton = view.findViewById(R.id.EditSetText);
        editSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int exercisePosition = parent.getChildCount();
                CurrentWorkoutFragment.editSet(exercisePosition - 1, exercise, exercise_adaptor, setPosition);
            }});
        editSetButtons.add(editSetButton);

        Switch completeSetSwitch = view.findViewById(R.id.setStatusSwitch);
        completeSetSwitches.add(completeSetSwitch);
        completeSetSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int exercisePosition = parent.getChildCount();
                CurrentWorkoutFragment.editSetStatus(completeSetSwitch.isChecked(), exercise_adaptor, setPosition); // TODO
            }
        });

        LinearLayout weightLayout = view.findViewById(R.id.weightLayout);
        this.weightLayout.add(weightLayout);
        LinearLayout repsLayout = view.findViewById(R.id.repsLayout);
        this.repsLayout.add(repsLayout);
        TextView setNumber = view.findViewById(R.id.setNumberText);
        setNumbers.add(setNumber);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Exercise_Adaptor.MyViewHolder holder, int position) {
        // Assigning values to each row in the workout_exercise_entry layout
        // Based on the position of the row
        holder.setNumber.setText("Set " + (position + 1));
        holder.setReps.setText(setList.get(position).getSetReps());
        holder.setWeight.setText(setList.get(position).getSetWeight() + " kg");
    }

    @Override
    public int getItemCount() {
        // Returns the number of rows in the recycler view
        return setList.size();
    }

    public void hideExercise(boolean hide) {
        if (hide) {
            for (int i = 0; i < editSetButtons.size(); i++) {
                editSetButtons.get(i).setVisibility(View.GONE);
                completeSetSwitches.get(i).setVisibility(View.GONE);
                weightLayout.get(i).setVisibility(View.GONE);
                repsLayout.get(i).setVisibility(View.GONE);
                setNumbers.get(i).setVisibility(View.GONE);
                setNumbers.get(0).setVisibility(View.VISIBLE);
                setNumbers.get(0).setText("Sets: " + setList.size());
                exercise.getSets().get(i).state = Config.ActivityState.COMPLETED;
            }
        } else {
            for (int i = 0; i < editSetButtons.size(); i++) {
                editSetButtons.get(i).setVisibility(View.VISIBLE);
                completeSetSwitches.get(i).setVisibility(View.VISIBLE);
                weightLayout.get(i).setVisibility(View.VISIBLE);
                repsLayout.get(i).setVisibility(View.VISIBLE);
                setNumbers.get(i).setVisibility(View.VISIBLE);
                setNumbers.get(0).setText("Set 1");
                completeSetSwitches.get(i).setChecked(false);
                exercise.getSets().get(i).state = Config.ActivityState.IN_PROGRESS;
            }
        }

    }

    // This is the view holder class, kind of like onCreate
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView setNumber;
        TextView setWeight;
        TextView setReps;
        TextView newSetButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            setNumber = itemView.findViewById(R.id.setNumberText);
            setWeight = itemView.findViewById(R.id.setWeightText);
            setReps = itemView.findViewById(R.id.setRepsText);
        }
    }
}
