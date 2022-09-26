package com.example.fbifitness;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


// Controller for the recycler view in the CurrentWorkoutFragment
public class CurrentWorkoutFragment extends Fragment implements Exercise_Interface {

    TextView addExerciseButton;
    Workout_Adaptor workout_adaptor;

    public CurrentWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_current_workout, container, false);
        //addNewExercise(view);
        RecyclerView recyclerView = view.findViewById(R.id.currentWorkoutExerciseList);
        workout_adaptor = new Workout_Adaptor(getContext(), MainActivity.exerciseList, this);
        recyclerView.setAdapter(workout_adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addExerciseButton = view.findViewById(R.id.NewExerciseText);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CurrentWorkoutFragment", "Add Exercise Button Clicked");
                addNewExercise(view);
                workout_adaptor.notifyItemInserted(MainActivity.exerciseList.size() - 1);
            }
        });

        return view;
    }

    @Override
    public void onItemClicked(int position) {
        // TODO add custom dialog box
        // get the ID of the object that was clicked

        // Open a dialog box to enter the number of reps and weight
        Log.d("CurrentWorkoutFragment", "Item Clicked: " + position);
        MainActivity.exerciseList.get(position).addSet(String.valueOf(position), "10");
        workout_adaptor.exercise_adaptors.get(position).notifyDataSetChanged();

    }

    private void addNewExercise(View view) {
        MainActivity.exerciseList.add(new ExerciseListView("ExerciseName"));

    }
}