package com.example.fbifitness;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewWorkoutFragment extends Fragment {

    private TextView strengthWorkoutBox;
    private TextView runWalkBox;
    private TextView custom1Box;
    private TextView custom2Box;
    private TextView newWorkoutBox;

    View view;


    public NewWorkoutFragment() {
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
        view = inflater.inflate(R.layout.fragment_new_workout, container, false);
        getActiveBoxes();
        setTextListeners();

        return view;
    }

    private void getActiveBoxes() {
        strengthWorkoutBox = view.findViewById(R.id.strengthWorkoutText);
        runWalkBox = view.findViewById(R.id.runWalkText);
        custom1Box = view.findViewById(R.id.customWorkout1Text);
        custom2Box = view.findViewById(R.id.customWorkout2Text);
        newWorkoutBox = view.findViewById(R.id.newWorkoutText);
    }

    private void setTextListeners() {
        strengthWorkoutBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NewWorkoutFragment", "Strength Workout Box Clicked");

            }
        });

        runWalkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NewWorkoutFragment", "Run/Walk Box Clicked");

            }
        });

        custom1Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NewWorkoutFragment", "Custom Workout 1 Box Clicked");

            }
        });

        custom2Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NewWorkoutFragment", "Custom Workout 2 Box Clicked");

            }
        });

        newWorkoutBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mainActivity.replaceFragment(new CurrentWorkoutFragment());
                Log.d("NewWorkoutFragment", "New Workout Box Clicked");

            }
        });
    }
}