package com.example.fbifitness;

import java.sql.Date;

public class Set implements Config, Saveable {

    public String weight;
    public String reps;
    public String timeSeconds;
    public ExerciseType setType;
    public ActivityState state;

    public Set(ExerciseType setType, String weightTime, String reps) {
        this.setType = setType;
        this.state = ActivityState.NOT_STARTED;
        this.reps = reps;

        if (setType == ExerciseType.WEIGHT) {
            this.weight = weightTime;
        } else if (setType == ExerciseType.TIME) {
            this.timeSeconds = weightTime;
        }
    }

    public void complete() {
        this.state = ActivityState.COMPLETED;
    }




    @Override
    public void save() {
        // TODO Auto-generated method stub

    }
    // public UniqueID exerciseID;
    // //public Exercise exercise;
    // public UniqueID setID; // not on class diagram
    // public float weight;
    // public int reps;
    // public int setNumber; // not in class diagram
    // public float timeSeconds;
    // public SetType setType;
    // public ActivityState state;

    // public Date timeStarted;
    // public Date timeCompleted;
    // public Date totalTime;

    // public Set(UniqueID exerciseID, SetType setType) {
    //     this.timeStarted = new Date(System.currentTimeMillis());
    //     this.exerciseID = exerciseID;
    //     Exercise exercise = (Exercise)UniqueID.getLinked(this.exerciseID);
    //     this.setID = new UniqueID(IDType.SET, exercise.getWorkout().getUser(), this);
    //     this.setType = setType;
    //     this.state = ActivityState.NOT_STARTED;
    // }

    // public Set(UniqueID exerciseID, SetType type, float weightTime, int reps) {
    //     this.timeStarted = new Date(System.currentTimeMillis());
    //     this.exerciseID = exerciseID;
    //     Exercise exercise = (Exercise)UniqueID.getLinked(this.exerciseID);
    //     this.setID = new UniqueID(IDType.SET, exercise.getWorkout().getUser(), this);
    //     this.state = ActivityState.NOT_STARTED;
    //     this.reps = reps;
    //     this.setType = type;

    //     if (type == SetType.WEIGHT) {
    //         this.weight = weightTime;
    //         this.timeSeconds = 0;
    //     } else {
    //         this.timeSeconds = weightTime;
    //         this.weight = 0;
    //     }
    // }

    // // Constructor for loading in data
    // public Set(UniqueID exerciseID, UniqueID setID) {
    //     this.exerciseID = exerciseID;
    //     this.setID = setID;
    //     Exercise exercise = (Exercise)UniqueID.getLinked(exerciseID);
    //     exercise.importSetFromDB(this);
    //     this.getSetValuesFromDB();
    //     // TODO - Some function to put in a queue to populate
    // }

    // public void getSetValuesFromDB() {
    //     // TODO - Get values from DB
    // }


    // @Override
    // public void save() {
    //     if (User.mainUser == this.getExercise().getWorkout().getUser()) {
    //         // TODO - Save to database
    //     }
        
    // }

    // public void setReps(int reps) {
    //     this.reps = reps;
    // }

    // public void setWeight(float weight) {
    //     this.weight = weight;
    // }

    // public void setTime(float timeSeconds) {
    //     this.timeSeconds = timeSeconds;
    // }

    // public void setSetType(SetType setType) {
    //     this.setType = setType;
    // }

    // public void setState(ActivityState state) {
    //     this.state = state;
    // }

    // public void completeSet() {
    //     this.timeCompleted = new Date(System.currentTimeMillis());
    //     this.totalTime = new Date(this.timeCompleted.getTime() - this.timeStarted.getTime());
    //     this.state = ActivityState.COMPLETED;
    // }

    // public void deleteSet() {
    //     this.getExercise().deleteSet(this.setID);
    // }

    // public Exercise getExercise() {
    //     return (Exercise)UniqueID.getLinked(this.exerciseID);
    // }
}
