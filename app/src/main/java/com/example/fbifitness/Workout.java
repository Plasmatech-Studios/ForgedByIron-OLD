package com.example.fbifitness;

import android.util.Log;

import java.util.ArrayList;
//import Date
import java.util.Date;
import java.util.HashMap;

public class Workout extends UniqueID implements Config, Saveable {

    private UniqueID userID;
    // created by
    private String workoutName;
    private ActivityState state;
    // modified
    private Date timeStarted;
    private Date timeCompleted;
    private Date totalTime;
    public Long baseTime = null;

    public static HashMap<String, Workout> workouts = new HashMap<String, Workout>(); // UniqueID, Workout

    public static Workout newWorkout(UniqueID userID) {
        Workout workout = new Workout(userID);
        workout.workoutName = "New Workout";
        workouts.put(workout.getUniqueID().toString(), workout);
        return workout;       
    }

    public static Workout newWorkout(UniqueID userID, String workoutName) {
        Workout workout = new Workout(userID);
        workout.workoutName = workoutName;
        workouts.put(workout.getUniqueID().toString(), workout);
        return workout;
    }

    // Load a workout from the database using the workoutID
    public static Workout newWorkoutFromLoad(String uniqueID) {
        if (workouts.containsKey(uniqueID)) {
            return workouts.get(uniqueID);
        }
        if (DataManager.workoutExists(uniqueID)) {
            String userID = DataManager.getUserIDFromWorkout(uniqueID);
            String workoutName = DataManager.getWorkoutName(uniqueID);
            String stateString = DataManager.getWorkoutState(uniqueID);
            ActivityState state = ActivityState.valueOf(stateString);
            String timeStartedString = DataManager.getWorkoutTimeStarted(uniqueID);
            Long baseTime = DataManager.getWorkoutBaseTime(uniqueID);
            Date timeStarted = new Date();
            if (timeStartedString != null) {
                timeStarted = new Date(Long.parseLong(timeStartedString));
            }
            String timeCompletedString = DataManager.getWorkoutTimeCompleted(uniqueID);
            Date timeCompleted = new Date();
            if (timeCompletedString != null) {
                timeCompleted = new Date(Long.parseLong(timeCompletedString));
            }
            String totalTimeString = DataManager.getWorkoutTotalTime(uniqueID);
            Date totalTime = new Date();
            if (totalTimeString != null) {
                totalTime = new Date(Long.parseLong(totalTimeString));
            }
            Workout workout = new Workout(uniqueID, userID, workoutName, state, timeStarted, timeCompleted, totalTime);
            workout.baseTime = baseTime;
            Log.e("Workout", "Workout Name: " + workout.getName());
            workouts.put(workout.getUniqueID().toString(), workout);
            return workout;
        } else {
            Log.e("Workout", "Workout does not exist");
            return null;
        }
    }

    private Workout(UniqueID userID) { // Constructor for creating new workout
        super(IDType.WORKOUT);
        this.userID = userID;
        this.workoutName = "New Workout";
        this.state = ActivityState.NOT_STARTED;
        this.timeStarted = null;
        this.timeCompleted = null;
        this.totalTime = null;

    }

    private Workout(String uniqueID, String userID, String workoutName, ActivityState state, Date timeStarted, Date timeCompleted, Date totalTime) { // Constructor for loading in data
        super(uniqueID, IDType.WORKOUT);
        Log.e("Workout", "Creating workout from database: " + uniqueID + " with name: " + workoutName);
        this.userID = UniqueID.getUniqueIDFromString(userID);
        this.workoutName = workoutName;
        this.state = state;
        this.timeStarted = timeStarted;
        this.timeCompleted = timeCompleted;
        this.totalTime = totalTime;
        Log.e("Workout", "Hello");
    }

    public void startWorkout() {
        this.state = ActivityState.IN_PROGRESS;
        this.timeStarted = new Date(System.currentTimeMillis());
//        if (User.users.get(this.userID.toString()).getActiveWorkout() != null) { // If the user has an active workout, complete it
//            Workout.workouts.get(User.users.get(this.userID.toString()).getActiveWorkout().toString()).completeWorkout();
//        }
//        User.users.get(this.userID.toString()).setActiveWorkout(this.getUniqueID()); // Set the user's active workout to this workout
    }

    public void completeWorkout() {
        this.state = ActivityState.COMPLETED;
        Log.d("Workout", "Workout " + this.getUniqueID().toString() + " completed");
        this.timeCompleted = new Date(System.currentTimeMillis());
        for (Exercise exercise : Exercise.exercises.values()) {
            if (exercise.getWorkoutID().toString().equals(this.getUniqueID().toString())) {
                exercise.complete();;
            }
        }
        if (this.getTimeStarted() != null && this.getTimeCompleted() != null) {
            this.totalTime = new Date(this.getTimeCompleted().getTime() - this.getTimeStarted().getTime());
        }
        User.users.get(this.userID.toString()).setActiveWorkout(null); // Set the user's active workout to null

        save();
    }

    public UniqueID addExercise(ExerciseType type) {
        Exercise exercise = Exercise.newExercise(this.getUniqueID(), type);
        return exercise.getUniqueID();
    }

    public UniqueID addExercise(ExerciseType type, String name) {
        Exercise exercise = Exercise.newExercise(this.getUniqueID(), type, name);
        return exercise.getUniqueID();
    }

    public void setName(String name) {
        this.workoutName = name;
    }

    public String getName() {
        return this.workoutName;
    }

    public ActivityState getState() {
        return this.state;
    }

    public Date getTimeStarted() {
        return this.timeStarted;
    }

    public Date getTimeCompleted() {
        return this.timeCompleted;
    }

    public Date getTotalTime() {
        return this.totalTime;
    }

    public UniqueID getUserID() {
        return this.userID;
    }

    public User getUser() {
        return User.users.get(this.userID.toString());
    }


    @Override
    public void save() {
        DataManager.saveWorkout(this.getUniqueID().toString());
    }
    // public UniqueID userID;
    // //private User user; // Use getUser()
    // public UniqueID workoutID;
    // public UniqueID createdByID; // TODO handle this
    // public ArrayList<UniqueID> exercises = new ArrayList<UniqueID>();
    // public ActivityState state;
    // public boolean modified;
    // public Date timeStarted;
    // public Date timeCompleted;
    // public Date totalTime;

    // public Workout(UniqueID userID) {
    //     this.timeStarted = new Date(System.currentTimeMillis());
    //     this.userID = userID;
    //     User user = UniqueID.getUserByID(this.userID);
    //     this.workoutID = new UniqueID(IDType.WORKOUT, user, this);
    //     this.state = ActivityState.NOT_STARTED;
    //     this.modified = false;
    // }

    // // public Workout(User user) {
    // //     this.timeStarted = new Date(System.currentTimeMillis());
    // //     this.userID = user.getID();
    // //     this.user = user;
    // //     this.workoutID = new UniqueID(IDType.WORKOUT, user, this);
    // //     this.state = ActivityState.NOT_STARTED;
    // //     this.modified = false;
    // // }

    // // Constructor for loading in data
    // public Workout(UniqueID userID, UniqueID workoutID) {
    //     this.workoutID = workoutID;
    //     this.userID = userID;
    //     User user = UniqueID.getUserByID(userID);
    //     user.importWorkoutFromDB(this.workoutID);
    //     this.getWorkoutValuesFromDB();
    //     // TODO - Some function to put in a queue to populate
    // }

    // public void getWorkoutValuesFromDB() {
    //     // TODO - Get values from DB
    // }

    // public UniqueID getID() {
    //     return this.workoutID;
    // }

    // public UniqueID addExercise() {
    //     if (this.state == ActivityState.COMPLETED) {
    //         // Send message to ensure user knows workout is completed - this will flag as modified
    //         this.modified = true;
    //         this.state = ActivityState.IN_PROGRESS;
    //     }
    //     if (this.state == ActivityState.NOT_STARTED) {
    //         this.state = ActivityState.IN_PROGRESS;
    //     }

    //     this.exercises.add(new Exercise(this.workoutID).exerciseID);
    //     System.out.println("Exercise added");
    //     return this.exercises.get(this.exercises.size() - 1);
    // }

    // public void editExercise() {
    //     if (this.state == ActivityState.COMPLETED) {
    //         // Send message to ensure user knows workout is completed - this will flag as modified
    //         this.modified = true;
    //         this.state = ActivityState.IN_PROGRESS;
    //     }
    //     if (this.state == ActivityState.NOT_STARTED) {
    //         this.state = ActivityState.IN_PROGRESS;
    //         System.out.println("Exercise editted from NOT_STARTED workout");

    //     }
    //     // TODO Edit code here - may need to pass data
    // }

    // public void deleteExercise() {
    //     if (this.state == ActivityState.COMPLETED) {
    //         // Send message to ensure user knows workout is completed - this will flag as modified
    //         this.modified = true;
    //         this.state = ActivityState.IN_PROGRESS;
    //     }
    //     if (this.state == ActivityState.NOT_STARTED) { // Should not be in this state
    //         this.state = ActivityState.IN_PROGRESS;
    //         System.out.println("Exercise deleted from NOT_STARTED workout");
    //     }
    //     // TODO Edit code here - how to we handle this
    // }

    // public User getUser() {
    //     return UniqueID.getUserByID(this.userID);
    // }

    // @Override
    // public void save() {
    //     if (User.mainUser == UniqueID.getUserByID(this.userID)) {
    //         for (UniqueID exerciseID : this.exercises) {
    //             Exercise exercise = (Exercise)UniqueID.getLinked(exerciseID);
    //             exercise.save();
    //         }
    //     }
        
    // }

    // public boolean completeWorkout() {
    //     if (this.state == ActivityState.IN_PROGRESS) {
    //         if (this.exercises.size() > 0) {
    //             for (UniqueID exerciseID : this.exercises) {
    //                 Exercise exercise = (Exercise) UniqueID.getLinked(exerciseID);
    //                 if (exercise.state != ActivityState.COMPLETED) {
    //                     // if all sets in the exercise are completed, complete the exercise
    //                     if (exercise.hasOutstandingSets()) {
    //                         deleteExercise(exerciseID); // deleting unfinished exercises before completing workout
    //                     } else {
    //                         exercise.completeExercise();
    //                     }

    //                     if (this.exercises.size() > 0) {
    //                         continue; // Check needed incase last exercise was removed
    //                     } else {
    //                         this.state = ActivityState.COMPLETED;
    //                         this.timeCompleted = new Date(System.currentTimeMillis());
    //                         this.totalTime = new Date(this.timeCompleted.getTime() - this.timeStarted.getTime());
    //                         return true;
    //                     }
    //                 }
    //             }
    //             this.state = ActivityState.COMPLETED;
    //             this.timeCompleted = new Date(System.currentTimeMillis());
    //             this.totalTime = new Date(this.timeCompleted.getTime() - this.timeStarted.getTime());
    //             //this.modified = true;
    //             return true;
    //         } else {
    //             System.out.println("Error: Workout has no exercises");
    //             return false;
    //         }
    //     } else {
    //         System.out.println("Error: Workout cannot be completed");
    //         return false;
    //     }
    // }

    // public boolean deleteExercise(UniqueID exerciseID) {
    //     //Exercise exercise = (Exercise) UniqueID.getLinked(exerciseID);
    //     if (this.state == ActivityState.COMPLETED) {
    //         // Send message to ensure user knows workout is completed - this will flag as modified
    //         this.modified = true;
    //         this.state = ActivityState.IN_PROGRESS;
    //     }
    //     if (this.state == ActivityState.NOT_STARTED) { // Should not be in this state
    //         this.state = ActivityState.IN_PROGRESS;
    //         System.out.println("Exercise deleted from NOT_STARTED workout");
    //     }
    //     // if exercise is in workout, remove it
    //     if (this.exercises.contains(exerciseID)) {
    //         this.exercises.remove(exerciseID);
    //         System.out.println("Exercise deleted");
    //         return true;
    //     } else {
    //         return false;
    //     }
    // }

    // public long getWorkoutDurationMillis() {
    //     return this.totalTime.getTime();
    // }

    // public long getWorkoutDurationSeconds() {
    //     return this.totalTime.getTime() / 1000;
    // }

    // public long getWorkoutDurationMinutes() {
    //     return this.totalTime.getTime() / 1000 / 60;
    // }

    // public void importExerciseFromDB(UniqueID exerciseID) {
    //     this.exercises.add(exerciseID);
    // }

}