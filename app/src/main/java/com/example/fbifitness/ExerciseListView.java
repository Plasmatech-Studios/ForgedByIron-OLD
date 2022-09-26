package com.example.fbifitness;

import java.util.ArrayList;

public class ExerciseListView {
    public String exerciseName;
    public boolean exerciseComplete;
    public ArrayList<SetListView> setList;


    public ExerciseListView(String exerciseName) {
        this.exerciseName = exerciseName;
        this.exerciseComplete = false;
        this.setList = new ArrayList<SetListView>();
        this.addSet("10", "100");
        this.addSet("1", "200");

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

}

