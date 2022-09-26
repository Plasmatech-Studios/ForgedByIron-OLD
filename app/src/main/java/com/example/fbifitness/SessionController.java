package com.example.fbifitness;

import android.util.Log;

import java.util.ArrayList;

public class SessionController implements Config {



    //Singleton 
    private static SessionController instance = null;
    private static SecurityManager securityManager = null;
    public static User currentUser = null;
    public static ArrayList<ExerciseListView> exerciseList;


    private SessionController() {
        // Exists only to defeat instantiation.
    }

    public static SessionController getInstance() {
        if(instance == null) {
            instance = new SessionController();

        }
        return instance;
    }

    public void startSession() {
        currentUser = User.newUser("test", "test");
        System.out.println(currentUser.getUniqueID());
        // securityManager = SecurityManager.getInstance();
        // securityManager.init();
        // // if already logged in, do other things

        // try {
        //     SecurityManager.createUser("admin", "password");
        // } catch (Exception e) {
        //     Log.d("SessionController", "Error creating user");
        // }

        // User.mainUser.createWorkout();
        // User.mainUser.getCurrentWorkout().addExercise();
        // ((Exercise)(UniqueID.getLinked(User.mainUser.getCurrentWorkout().exercises.get(0)))).addSet(SetType.WEIGHT, 100.0f, 5);
        // ((Exercise)(UniqueID.getLinked(User.mainUser.getCurrentWorkout().exercises.get(0)))).addSet(SetType.WEIGHT, 100.0f, 5);
        // ((Exercise)(UniqueID.getLinked(User.mainUser.getCurrentWorkout().exercises.get(0)))).addSet(SetType.WEIGHT, 100.0f, 5);
    }

    public void requestNewWorkout(String workoutName) {
        if (currentUser != null) {
            UniqueID workoutID = currentUser.startNewWorkout();
            Log.d("SessionController", "New workout created: " + workoutID.toString());
            Workout workout = Workout.workouts.get(workoutID.toString());
            workout.setName(workoutName);
            exerciseList = new ArrayList<ExerciseListView>();
        } else {
            Log.d("SessionController", "No user logged in");
        }
    }

    // // Login - username and password
    // // Username used to compare password with Auth
    // // If Auth is true, returns userID.

    // // Load data from the database

    // // Load Users (UniqueID)

    // // Load Workouts (UniqueID) -> Linked to User

    // // Load Exercises (UniqueID) -> Linked to Workout

    // // Load Sets (UniqueID) -> Linked to Exercise


}
