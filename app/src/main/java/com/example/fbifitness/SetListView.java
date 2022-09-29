package com.example.fbifitness;

public class SetListView {
    public String setReps;
    public String setWeight;

    public SetListView(String setReps, String setWeight) {
        this.setReps = setReps;
        this.setWeight = setWeight;
    }

    public String getSetReps() {
        return setReps;
    }

    public void setSetReps(String setReps) {
        this.setReps = setReps;
    }

    public String getSetWeight() {
        return setWeight;
    }

    public void setSetWeight(String setWeight) {
        this.setWeight = setWeight;
    }
}

