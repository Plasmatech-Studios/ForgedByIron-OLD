package com.example.fbifitness;

import java.util.ArrayList;

public class ExerciseListView implements Config {
    public String exerciseName;
    public boolean exerciseComplete;
    public ExerciseType exerciseType;
    public ArrayList<SetListView> setList;
    public Exercise exercise;


    public ExerciseListView(Exercise exercise) {
        this.exercise = exercise;
        this.exerciseName = exercise.getExerciseName();
        this.exerciseType = exercise.getExerciseType();
        this.exerciseComplete = false;
        this.setList = new ArrayList<SetListView>();
        for (Set set : exercise.getSets()) {
            this.setList.add(new SetListView(set.reps, set.weight));
        }

    }

    public Exercise getExercise() {
        return exercise;
    }



    public String getExerciseName() {
        return this.exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public boolean isExerciseComplete() {
        return this.exerciseComplete;
    }

    public void setExerciseComplete(boolean exerciseComplete) {
        this.exerciseComplete = exerciseComplete;
    }

    public ArrayList<SetListView> getSetList() {
        return this.setList;
    }

    public int getSetListSize() {
        return this.setList.size();
    }

    public SetListView getSet(int index) {
        return this.setList.get(index);
    }

    public void addSet(String reps, String weight) {
        this.setList.add(new SetListView(reps, weight));
    }

    public void replaceSet(int index, String reps, String weight) {
        this.setList.set(index, new SetListView(reps, weight));
    }

}

