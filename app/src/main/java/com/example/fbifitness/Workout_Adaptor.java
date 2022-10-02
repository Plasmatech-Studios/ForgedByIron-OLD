package com.example.fbifitness;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Workout_Adaptor extends RecyclerView.Adapter<Workout_Adaptor.MyViewHolder> {

    private final Exercise_Interface exercise_interface;

    Context context;
    ArrayList<ExerciseListView> exerciseList; // Dummy data, will be replaced with workout objects
    ArrayList<Exercise_Adaptor> exercise_adaptors;
    ArrayList<Switch> completeExerciseSwitches;
    ArrayList<TextView> addSetButtons;


    public Workout_Adaptor(Context context, ArrayList<ExerciseListView> exerciseList, Exercise_Interface exercise_interface) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.exercise_interface = exercise_interface;
        exercise_adaptors = new ArrayList<Exercise_Adaptor>();
        completeExerciseSwitches = new ArrayList<Switch>();
        addSetButtons = new ArrayList<TextView>();
    }

    @NonNull
    @Override
    public Workout_Adaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where the layout is inflated (and giving a look to each row)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.workout_exercise_entry, parent, false);

        // get position
        int position = parent.getChildCount();
        RecyclerView setRecyclerView = view.findViewById(R.id.currentExerciseSetList);
        Exercise_Adaptor exercise_adaptor = new Exercise_Adaptor(context, exerciseList.get(position).getSetList(), exerciseList.get(position).getExercise(), exercise_adaptors.size()); // not passing the correct set list
        setRecyclerView.setAdapter(exercise_adaptor);
        setRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        exercise_adaptors.add(exercise_adaptor);
        Workout_Adaptor adaptor = this;
        Switch completeExerciseSwitch = view.findViewById(R.id.completeExerciseSwitch);
        completeExerciseSwitches.add(completeExerciseSwitch);
        completeExerciseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the position of the switch
                int sPosition = completeExerciseSwitches.indexOf(completeExerciseSwitch);
                CurrentWorkoutFragment.editExerciseStatus(completeExerciseSwitch.isChecked(), adaptor, position, sPosition);
            }
        });

        return new MyViewHolder(view, exercise_interface);
    }

    @Override
    public void onBindViewHolder(@NonNull Workout_Adaptor.MyViewHolder holder, int position) {
        // Assigning values to each row in the workout_exercise_entry layout
        // Based on the position of the row
        holder.exerciseName.setText(exerciseList.get(position).getExerciseName());
        addSetButtons.add(holder.addSetButton);

    }

    @Override
    public int getItemCount() {
        // Returns the number of rows in the recycler view
        return exerciseList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Grabbing the views from the workout_exercise_entry layout
        // Similar to the onCreateViewHolder method

        TextView exerciseName;
        TextView addSetButton;
        RecyclerView exerciseSets;
        Switch exerciseCompleteSwitch;
        //Button addSetButton;
        public MyViewHolder(@NonNull View itemView, Exercise_Interface exercise_interface) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exerciseNameText);
            addSetButton = itemView.findViewById(R.id.AddSetText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (exercise_interface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            exercise_interface.onItemClicked(position);
                        } else {
                            Log.d("Workout_Adaptor", "Position is -1");
                        }
                    }
                }});

        }
    }
}
