package com.example.fbifitness;

import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class SessionController implements Config {



    //Singleton 
    private static SessionController instance = null;
    private static SecurityManager securityManager = null;
    public static User currentUser = null;
    public static ArrayList<ExerciseListView> exerciseList;
    public static boolean isNewUser = false;


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
        securityManager = SecurityManager.getInstance();
        Badge.initBadges();

        MainActivity.mainActivity.replaceFragment(new LoginFragment());
    }

    private void login(String username, String password) {
        User user;
        try {
            user = securityManager.login(username, password);
            if (user == null) { // Invalid password
                Log.e("SessionController", "Error: Login failed");
                return;
            } else {
                Log.e("SessionController", "Logged in as " + user.getUsername());
                currentUser = user;
                exerciseList = new ArrayList<ExerciseListView>();
                Badge.initBadges();
                Badge.loadBadges(user.getUniqueID().toString());
                Badge.isLoading = false;
                Badge.unlockBadge("FBI");
//                Badge.unlockBadge("B60");
//                Badge.unlockBadge("B80");
//                Badge.unlockBadge("B100");
                if (currentUser.getActiveWorkout() != null) {
                    DataManager.fillExerciseList(currentUser.getActiveWorkout().toString());
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean processLoginRequest(String username, String password) {
        login(username, password);
        if (currentUser == null) { // Invalid password
            Log.d("SessionController", "Error: Login failed");
//            try { // TODO DO NOT DEFAULT TO THIS - MOVE TO LOGIN ACTIVITY
//                Log.e("SessionController", "Error: Login failed - Creating new user");
//                securityManager.newUser("admin", "password");
//            } catch (NoSuchAlgorithmException e) {
//                Log.d("SessionController", "Error: NoSuchAlgorithmException");
//            }
            return false;
        } else {
            Log.d("SessionController", "Logged in as " + currentUser.getUsername());
            return true;
        }
    }

    public void logout() {
        currentUser = null;
        exerciseList = null;
        isNewUser = false;
        Badge.resetData();
        MainActivity.mainActivity.replaceFragment(new LoginFragment());
    }

    public String getActiveUserName() {
        return currentUser.getUsername();
    }

    public int getActiveUserWorkoutCount() {
        return currentUser.getWorkoutCount();
    }




        //currentUser = User.newUser(username, secretKey);
        //DataManager.saveNewUser(currentUser.getUniqueID().toString(), currentUser.getUsername(), currentUser.getSecretKey());
        //Log.d("Starting Sessions with User: ", currentUser.getUniqueID().toString());
        //requestNewWorkout("Test Workout");

    //}

    public void requestNewWorkout(String workoutName) {
        if (currentUser != null) {
            UniqueID workoutID = currentUser.startNewWorkout();
            //Log.d activityState
            Log.d("SessionController", "New workout created: " + workoutID.toString());
            Workout workout = Workout.workouts.get(workoutID.toString());
            Log.d("SessionController", "Workout state: " + workout.getState().toString());
            workout.setName(workoutName);
            workout.startWorkout();
            exerciseList = new ArrayList<>();
            workout.save();
        } else {
            Log.d("SessionController", "No user logged in");
        }
    }

    public void endWorkout() {
        if (currentUser != null) {
            currentUser.endWorkout();
            //Display the reports fragment
            MainActivity.bottomNavigationView.setSelectedItemId(R.id.reports);

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
