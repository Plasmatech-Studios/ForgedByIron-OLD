package com.example.fbifitness;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


// Controller for the recycler view in the CurrentWorkoutFragment
public class CurrentWorkoutFragment extends Fragment implements Exercise_Interface, Config {

    private static View myView;

    static TextView addExerciseButton;
    static Workout_Adaptor workout_adaptor;

    private static String weightTime = "0";
    private static String repsDistance = "0";
    private static String prompt = "Prompt";

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
        myView = view;
        TextView textView = view.findViewById(R.id.workoutTitleText);
        Workout currentWorkout = Workout.workouts.get(SessionController.currentUser.getActiveWorkout().toString());
        textView.setText(currentWorkout.getName());
        RecyclerView recyclerView = view.findViewById(R.id.currentWorkoutExerciseList);
        workout_adaptor = new Workout_Adaptor(getContext(), SessionController.exerciseList, this);
        recyclerView.setAdapter(workout_adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addExerciseButton = view.findViewById(R.id.NewExerciseText);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Add Exercise Button
                Log.d("CurrentWorkoutFragment", "Add Exercise Button Clicked");
                //SessionController.getInstance().
                addNewExercise(view);
            }
        });

        return view;
    }

    @Override
    public void onItemClicked(int position) {

        // Open a dialog box to enter the number of reps and weight
        Log.d("CurrentWorkoutFragment", "Item Clicked: " + position);
        addSet(position);
    }

    private static void addSet(int position) {

        User user = SessionController.currentUser;
        Workout workout = Workout.workouts.get(user.getActiveWorkout().toString());
        Exercise exercise = SessionController.exerciseList.get(position).getExercise();


        if (exercise.getExerciseType()  == ExerciseType.WEIGHT) {
            prompt = "Enter weight (KG)";
        } else {
            prompt = "Enter time (seconds)";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());

        builder.setTitle(prompt);

        // Set up the input
        final EditText input = new EditText(myView.getContext());

        input.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
        builder.setView(input);

        // Set up the buttons
            builder.setPositiveButton(prompt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                weightTime = input.getText().toString();
                if (weightTime.equals("")) {
                    weightTime = "0";
                }

                if (exercise.getExerciseType()  == ExerciseType.WEIGHT) {
                    prompt = "Enter reps";
                } else {
                    prompt = "Enter distance (meters)";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());

                builder.setTitle(prompt);

                // Set up the input
                final EditText input = new EditText(myView.getContext());

                input.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
                builder.setView(input);

                // Set up the buttons
                    builder.setPositiveButton(prompt, new DialogInterface.OnClickListener() {
                    @Override
                        public void onClick(DialogInterface dialog, int which) {
                            repsDistance = input.getText().toString();
                            if (repsDistance.equals("")) {
                                repsDistance = "0";
                            }

                            Set set = new Set(exercise.getExerciseType(), weightTime, repsDistance);
                            exercise.addSet(set);

                            SessionController.exerciseList.get(position).addSet(repsDistance, weightTime);
                            workout_adaptor.exercise_adaptors.get(position).notifyDataSetChanged();
                        }
                    });
                    builder.show();
            }
        });
        builder.setNegativeButton("back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        builder.show();
    }

    public static void editSet(int position, Exercise exercise, int adaptorPosition) {

        User user = SessionController.currentUser;
        Workout workout = Workout.workouts.get(user.getActiveWorkout().toString());


        if (exercise.getExerciseType()  == ExerciseType.WEIGHT) {
            prompt = "Enter weight (KG)";
        } else {
            prompt = "Enter time (seconds)";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());

        builder.setTitle(prompt);

        // Set up the input
        final EditText input = new EditText(myView.getContext());

        input.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(prompt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                weightTime = input.getText().toString();
                if (weightTime.equals("")) {
                    weightTime = "0";
                }

                if (exercise.getExerciseType()  == ExerciseType.WEIGHT) {
                    prompt = "Enter reps";
                } else {
                    prompt = "Enter distance (meters)";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());

                builder.setTitle(prompt);

                // Set up the input
                final EditText input = new EditText(myView.getContext());

                input.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton(prompt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repsDistance = input.getText().toString();
                        if (repsDistance.equals("")) {
                            repsDistance = "0";
                        }

                        Set set = new Set(exercise.getExerciseType(), weightTime, repsDistance);
                        exercise.replaceSet(position, set);

                        SessionController.exerciseList.get(adaptorPosition).replaceSet(position, repsDistance, weightTime);
                        workout_adaptor.exercise_adaptors.get(adaptorPosition).notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });
        builder.setNegativeButton("back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        builder.show();
    }


    private void addNewExercise(View view) {
        showWorkoutNameDialog(view);
    }

    private void showWorkoutNameDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Enter Exercise Name");

        // Set up the input
        final EditText input = new EditText(view.getContext());

        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Add Weight Exercise", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String exerciseName = input.getText().toString();
                User user = SessionController.currentUser;
                Workout workout = Workout.workouts.get(user.getActiveWorkout().toString());
                UniqueID exerciseID = workout.addExercise(Config.ExerciseType.WEIGHT, exerciseName);
                Exercise exercise = Exercise.exercises.get(exerciseID.toString());
                SessionController.exerciseList.add(new ExerciseListView(exercise));
                workout_adaptor.notifyItemInserted(SessionController.exerciseList.size() - 1);

            }
        });
        builder.setNegativeButton("Add Time Exercise", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String exerciseName = input.getText().toString();
                User user = SessionController.currentUser;
                Workout workout = Workout.workouts.get(user.getActiveWorkout().toString());
                UniqueID exerciseID = workout.addExercise(Config.ExerciseType.TIME, exerciseName);
                Exercise exercise = Exercise.exercises.get(exerciseID.toString());
                SessionController.exerciseList.add(new ExerciseListView(exercise));
                workout_adaptor.notifyItemInserted(SessionController.exerciseList.size() - 1);

            }
        });

        builder.show();
    }
}