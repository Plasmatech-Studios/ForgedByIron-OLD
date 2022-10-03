package com.example.fbifitness;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Exercise extends UniqueID implements Config, Saveable {

    public static HashMap<String, Exercise> exercises = new HashMap<String, Exercise>(); // UniqueID, Exercise
    //createdByID;
    private UniqueID workoutID;
    private ActivityState state;
    private ExerciseType exerciseType;
    private String exerciseName;
    private ArrayList<Set> sets = new ArrayList<Set>();
    // //public ArrayList<Event> events = new ArrayList<Event>(); // Should this be UniqueID too???


    private Date timeStarted;
    private Date timeCompleted;
    private Date totalTime;

    public static Exercise newExercise(UniqueID workoutID, ExerciseType exerciseType) {
        Exercise exercise = new Exercise(workoutID, exerciseType);
        exercises.put(exercise.getUniqueID().toString(), exercise);
        return exercise;
    }

    public static Exercise newExercise(UniqueID workoutID, ExerciseType exerciseType, String exerciseName) {
        Exercise exercise = new Exercise(workoutID, exerciseType, exerciseName);
        exercises.put(exercise.getUniqueID().toString(), exercise);
        return exercise;
    }

    public static Exercise newExerciseFromLoad(String workoutID, String exerciseID, ActivityState state, ExerciseType exerciseType, String exerciseName, ArrayList<Set> sets, Date timeStarted, Date timeCompleted, Date totalTime) {
        if (exercises.containsKey(exerciseID.toString())) {
            return exercises.get(exerciseID.toString());
        }
        // TODO Assuming workout and user already exist?
        Exercise exercise = new Exercise(workoutID, exerciseID, state, exerciseType, exerciseName, sets, timeStarted, timeCompleted, totalTime);
        exercises.put(exercise.getUniqueID().toString(), exercise);
        return exercise;
    }

    private Exercise(UniqueID workoutID, ExerciseType exerciseType) { // Create a new exercise on the device
        super(IDType.EXERCISE);
        this.workoutID = workoutID;
        this.exerciseType = exerciseType;
        this.state = ActivityState.NOT_STARTED;
    }

    private Exercise(UniqueID workoutID, ExerciseType exerciseType, String exerciseName) { // Create a new exercise on the device
        super(IDType.EXERCISE);
        this.workoutID = workoutID;
        this.exerciseType = exerciseType;
        this.exerciseName = exerciseName;
        this.state = ActivityState.IN_PROGRESS;
        this.timeStarted = new Date(System.currentTimeMillis());
    }

    private Exercise(String workoutID, String exerciseID, ActivityState state, ExerciseType exerciseType, String exerciseName, ArrayList<Set> sets, Date timeStarted, Date timeCompleted, Date totalTime) { // Create a new exercise from the Database
        super(exerciseID, IDType.EXERCISE);
        this.workoutID = UniqueID.getUniqueIDFromString(workoutID);
        this.state = state;
        this.exerciseType = exerciseType;
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.timeStarted = timeStarted;
        this.timeCompleted = timeCompleted;
        this.totalTime = totalTime;
    }

    public void start() {
        this.state = ActivityState.IN_PROGRESS;
        this.timeStarted = new Date(System.currentTimeMillis());
        save();
    }

    public void complete() {
        this.state = ActivityState.COMPLETED;
        if (this.timeStarted != null) {
            this.timeCompleted = new Date(System.currentTimeMillis());
            this.totalTime = new Date(this.timeCompleted.getTime() - this.timeStarted.getTime());
        }
        for (Set set : this.sets) {
            set.complete();
        }
        save();
    }

    public void unComplete() {
        this.state = ActivityState.IN_PROGRESS;
        this.timeCompleted = null;
        this.totalTime = null;
        save();
    }

    public void addSet(Set set) {
        this.sets.add(set);
        save();
    }

    public void addSet(ExerciseType setType, String reps, String weightTime) {
        this.sets.add(new Set(setType, reps, weightTime));
        this.save();
    }

    public void completeSet(int index) {
        this.sets.get(index).complete();
    }

    public void completeSet(Set set) {
        this.sets.get(this.sets.indexOf(set)).complete();
    }

    public void completeAllSets() {
        for (Set set : this.sets) {
            set.complete();
        }
    }

    public void removeSet(Set set) { // Will this work?
        this.sets.remove(set);
    }

    public void removeSet(int index) {
        this.sets.remove(index);
    }

    public UniqueID getWorkoutID() {
        return this.workoutID;
    }

    public Workout getWorkout() {
        for (Workout workout : Workout.workouts.values()) {
            if (workout.getUniqueID().equals(this.workoutID)) {
                return workout;
            }
        }
        return null;
    }

    public ActivityState getState() {
        return this.state;
    }

    public ExerciseType getExerciseType() {
        return this.exerciseType;
    }

    public String getExerciseName() {
        return this.exerciseName;
    }

    public ArrayList<Set> getSets() {
        return this.sets;
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

    public void replaceSet(int index, Set set) {
        this.sets.set(index, set);
        this.save();
    }




    @Override
    public void save() {
        DataManager.saveExercise(this.getUniqueID().toString());
    }
    // public UniqueID createdByID;
    // public UniqueID workoutID;
    // public UniqueID exerciseID;
    // public UniqueID exerciseItemID; // implement this w/ DB and type/name/description

    // public ActivityState state;
    // //public ArrayList<Event> events = new ArrayList<Event>(); // Should this be UniqueID too???
    // public ArrayList<UniqueID> sets = new ArrayList<UniqueID>();

    // public Date timeStarted;
    // public Date timeCompleted;
    // public Date totalTime;

    // public Exercise(UniqueID workoutID) {
    //     this.timeStarted = new Date(System.currentTimeMillis());
    //     this.workoutID = workoutID;
    //     Workout workout = (Workout)UniqueID.getLinked(this.workoutID);
    //     this.exerciseID = new UniqueID(IDType.EXERCISE, workout.getUser(), this);
    //     this.state = ActivityState.NOT_STARTED;
    //     this.createdByID = workout.getUser().getID();
    // }

    // // Constructor for loading in data
    // public Exercise(UniqueID createdByID, UniqueID workoutID, UniqueID exerciseID) {
    //     this.workoutID = workoutID;
    //     this.createdByID = createdByID;
    //     this.exerciseID = exerciseID;
    //     Workout workout = (Workout)UniqueID.getLinked(workoutID);
    //     workout.importExerciseFromDB(this.exerciseID);
    //     this.getExerciseValuesFromDB();
    //     // TODO - Some function to put in a queue to populate
    // }

    // public void getExerciseValuesFromDB() {
    //     // TODO - Get values from DB
    // }

    // public void importSetFromDB(Set set) {
    //     sets.add(set.setID);
    // }

    // public UniqueID getID() {
    //     return this.exerciseID;
    // }

    // public Set getSet(UniqueID setID) {
    //     return (Set)UniqueID.getLinked(setID);

    // }

    // public UniqueID addSet(SetType setType) {
    //     if (this.state == ActivityState.COMPLETED) {
    //         // Send message to ensure user knows workout is completed - this will flag as modified
    //         // TODO - Check if workout is completed and if so, unlock (inside workout)
    //         //this.workout.modified = true;
    //         this.state = ActivityState.IN_PROGRESS;
    //     }
    //     if (this.state == ActivityState.NOT_STARTED) {
    //         this.state = ActivityState.IN_PROGRESS;
    //     }
    //     this.sets.add(new Set(this.exerciseID, setType).setID);
    //     return this.sets.get(this.sets.size() - 1);
    // }

    // public UniqueID addSet(SetType type, float weight, int reps) {
    //     if (this.state == ActivityState.COMPLETED) {
    //         // Send message to ensure user knows workout is completed - this will flag as modified
    //         // TODO - Check if workout is completed and if so, unlock (inside workout)
    //         //this.workout.modified = true;
    //         this.state = ActivityState.IN_PROGRESS;
    //     }
    //     if (this.state == ActivityState.NOT_STARTED) {
    //         this.state = ActivityState.IN_PROGRESS;
    //     }
    //     this.sets.add(new Set(this.exerciseID, type, weight, reps).setID);
    //     System.out.println("Empty Set of type: " + type + " added to Exercise");
    //     return this.sets.get(this.sets.size() - 1);
    // }

    // public void completeSet(Set set) {
    //     if (this.state == ActivityState.COMPLETED) {
    //         this.state = ActivityState.IN_PROGRESS; // TODO - may need to flag as modified
    //     }
    //     if (this.state == ActivityState.NOT_STARTED) {
    //         this.state = ActivityState.IN_PROGRESS;
    //     }
    //     set.completeSet();
    //     System.out.println("Set of type: " + set.setType + " completed");
    // }

    // public void completeAllSets() {
    //     if (this.state == ActivityState.IN_PROGRESS) {
    //         for (UniqueID setID : this.sets) {
    //             Set set = (Set)UniqueID.getLinked(setID);
    //             set.completeSet();
    //             System.out.println("Set of type: " + set.setType + " completed");
    //         }
    //     } else {
    //         System.out.println("Exercise not in progress");
    //     }

    // }

    // public boolean completeExercise() {
    //     if (this.state == ActivityState.IN_PROGRESS) { // if exercise is in progress, check if all sets are completed
    //         // if exercise contains unfinshed sets, remove them
    //         for (int i = 0; i < this.sets.size(); i++) {
    //             Set set = (Set)UniqueID.getLinked(this.sets.get(i));
    //             if (set.state == ActivityState.IN_PROGRESS) {
    //                 // TODO prompt the user
    //                 this.sets.remove(i);
    //                 i--; // TODO is this correct?
    //             }
    //             i++;
    //         }
    //         // if exercise contains no sets, delete exercise
    //         if (this.sets.size() == 0) {
    //             this.getWorkout().deleteExercise(this.exerciseID);
    //             return false;
    //         }
    //     }
    //     if (this.state == ActivityState.NOT_STARTED) {
    //         System.out.println("Attempting to complete Exercise is in state NOT_STARTED");
    //         return false;
    //     }
    //     if (this.state == ActivityState.COMPLETED) {
    //         System.out.println("Attempting to complete Exercise already completed");
    //         return false;
    //     }
    //     this.state = ActivityState.COMPLETED;
    //     this.timeCompleted = new Date(System.currentTimeMillis());
    //     this.totalTime = new Date(this.timeCompleted.getTime() - this.timeStarted.getTime());
    //     System.out.println("Exercise completed in " + this.totalTime.getTime() + " milliseconds");
    //     return true;
    // }
    

    // public UniqueID addSetWithValues(SetType type, float weight, int reps) {
    //     if (this.state == ActivityState.COMPLETED) {
    //         // Send message to ensure user knows workout is completed - this will flag as modified
    //         // TODO - Check if workout is completed and if so, unlock (inside workout)
    //         //this.workout.modified = true;
    //         this.state = ActivityState.IN_PROGRESS;
    //     }
    //     if (this.state == ActivityState.NOT_STARTED) {
    //         this.state = ActivityState.IN_PROGRESS;
    //     }
    //     this.sets.add(new Set(this.exerciseID, type, weight, reps).setID);
    //     System.out.println("Added set with values, of type: " + type + " to exercise");
    //     return this.sets.get(this.sets.size() - 1);
    // }

    // public boolean deleteSet(UniqueID setID) {
    //     if (this.sets.contains(setID)) {
    //         this.sets.remove(setID);
    //         return true;
    //     }
    //     System.out.println("Attempting to delete Set that does not exist in this exercise");
    //     return false;
    // }

    // @Override
    // public void save() {
    //     if (User.mainUser == this.getWorkout().getUser()) {
    //         for (UniqueID setID : this.sets) {
    //             Set set = (Set)UniqueID.getLinked(setID);
    //             set.save();
    //         }
    //     }
                
    // }

    // public boolean hasOutstandingSets() {
    //     for (UniqueID setID : this.sets) {
    //         Set set = (Set)UniqueID.getLinked(setID);
    //         if (set.state == ActivityState.IN_PROGRESS) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // public Workout getWorkout() {
    //     // TODO check type before reutrning
    //     return (Workout)UniqueID.getLinked(this.workoutID);
    // }
}
