package com.example.fbifitness;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;


// Controller for the recycler view in the CurrentWorkoutFragment
public class CurrentWorkoutFragment extends Fragment implements Exercise_Interface, Config {

    private static View myView;
    private static Exercise_Adaptor adaptor;

    static TextView addExerciseButton;
    static ArrayList<TextView> addSetButtons;
    static Workout_Adaptor workout_adaptor;

    private static String weightTime = "0";
    private static String repsDistance = "0";
    private static String prompt = "Prompt";
    static TextView endWorkoutButton;




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
        endWorkoutButton = view.findViewById(R.id.EndWorkoutText);
        endWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // End Workout Button
                Log.d("CurrentWorkoutFragment", "End Workout Button Clicked");
                SessionController.getInstance().endWorkout();
                //(view);
            }
        });
        Chronometer mChronometer= view.findViewById(R.id.chronometer);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
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
        input.setHint(prompt);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
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
                input.setHint(prompt);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
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

    public static void editSet(int position, Exercise exercise, Exercise_Adaptor exercise_adaptor, int setIndex) {
        adaptor = exercise_adaptor;
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
        // only allow numbers
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        // focus on the input
        input.requestFocus();
        // set the text hint to the current weight
        input.setHint(exercise.getSets().get(setIndex).weight);
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
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.requestFocus();
                //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                input.setHint(exercise.getSets().get(setIndex).reps);
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
                        Log.d(exercise.getSets().get(setIndex).reps, set.toString());
                        exercise.replaceSet(setIndex, set);
                        Log.d(exercise.getSets().get(setIndex).reps, set.toString());

                        adaptor.setList.get(setIndex).setSetWeight(weightTime);
                        adaptor.setList.get(setIndex).setSetReps(repsDistance);
                        adaptor.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });
        builder.setNegativeButton("back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public static void editSetStatus(boolean status, Exercise_Adaptor exercise_adaptor, int setIndex) {
        adaptor = exercise_adaptor;
        //hide the edit button
        if (status) {
            adaptor.editSetButtons.get(setIndex).setVisibility(View.GONE);
            adaptor.weightLayout.get(setIndex).setVisibility(View.GONE);
            adaptor.repsLayout.get(setIndex).setVisibility(View.GONE);
            adaptor.exercise.getSets().get(setIndex).state = ActivityState.COMPLETED;
        } else {
            adaptor.editSetButtons.get(setIndex).setVisibility(View.VISIBLE);
            adaptor.weightLayout.get(setIndex).setVisibility(View.VISIBLE);
            adaptor.repsLayout.get(setIndex).setVisibility(View.VISIBLE);
            adaptor.exercise.getSets().get(setIndex).state = ActivityState.IN_PROGRESS;
        }

        adaptor.exercise.save();

        adaptor.notifyDataSetChanged();

    }

    public static void editExerciseStatus(boolean status, Workout_Adaptor workout_adaptor, int setIndex, int sIndex) {
        //hide the exercise
        Log.d("Position", String.valueOf(setIndex));
        Log.d("Exercise Index", String.valueOf(sIndex));
        workout_adaptor.exercise_adaptors.get(setIndex).hideExercise(status);

        if (status) {
            workout_adaptor.addSetButtons.get(setIndex).setVisibility(View.GONE);
            workout_adaptor.exercise_adaptors.get(setIndex).exercise.complete();
        } else {
            workout_adaptor.addSetButtons.get(setIndex).setVisibility(View.VISIBLE);
            workout_adaptor.exercise_adaptors.get(setIndex).exercise.unComplete();
        }

        User user = SessionController.currentUser;
        Workout workout = Workout.workouts.get(user.getActiveWorkout().toString());
        // Save each exercise
        for (Exercise exercise : Exercise.exercises.values()) {
            if (exercise.getWorkout().equals(workout)) {
                exercise.save();
            }
        }

        workout_adaptor.notifyDataSetChanged();





    }


    private void addNewExercise(View view) {
        showExerciseNameDialog(view);
    }

    private void showExerciseNameDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Enter Exercise Name");

        // Set up the input
        final EditText input = new EditText(view.getContext());
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        input.setHint("Exercise Name");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Add Weight Exercise", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String exerciseName = input.getText().toString();
                if (exerciseName.equals("")) {
                    exerciseName = "New Exercise";
                }
                User user = SessionController.currentUser;
                Workout workout = Workout.workouts.get(user.getActiveWorkout().toString());
                UniqueID exerciseID = workout.addExercise(Config.ExerciseType.WEIGHT, exerciseName);
                Exercise exercise = Exercise.exercises.get(exerciseID.toString());
                SessionController.exerciseList.add(new ExerciseListView(exercise));
                exercise.save();
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
                exercise.save();
                workout_adaptor.notifyItemInserted(SessionController.exerciseList.size() - 1);

            }
        });

        builder.show();
    }
}