package com.example.fbifitness;

import java.util.Date;
import java.util.ArrayList;

public class Report implements Config {

    // UniqueID reportID;
    // ReportType type;
    private String workoutID;
    private String userID;
    private String date;
    private String totalTime;
    private String workoutName;
    private ArrayList<String> exerciseIDs;
    private ArrayList<String> exerciseNames;
    private ArrayList<Double> exerciseWeightTotals;


    public Report(String workoutID) {
        this.workoutID = workoutID;
        loadReportData();
    }

    public void loadReportData() {
        this.exerciseIDs = DataManager.getExerciseIDByWorkoutID(workoutID);
        String wDate = DataManager.getWorkoutDate(workoutID);
        Date date = new Date(Long.parseLong(wDate));
        //Date.valueOf(wDate).toString();
        // get the shortened version of the date
        //this.date = date.toString().substring(0, 10);
        // Format the date to be more readable
        this.date = date.toString().substring(4, 10) + ", " + date.toString().substring(30, 34);
        //this.date = date.toString();

        this.exerciseNames = new ArrayList<>();
        this.exerciseWeightTotals = new ArrayList<>();



        // Get Workout Data
        this.workoutName = DataManager.getWorkoutName(this.workoutID);
        for (int i = 0; i < exerciseIDs.size(); i++) {
            this.exerciseNames.add(DataManager.getExerciseNameByID(this.exerciseIDs.get(i)));
            this.exerciseWeightTotals.add(DataManager.getExerciseWeightTotalByID(this.exerciseIDs.get(i)));
        }
    }

    public String getWorkoutID() {
        return workoutID;
    }

    public String getUserID() {
        return userID;
    }

    public String getDate() {
        return date;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public ArrayList<String> getExerciseIDs() {
        return exerciseIDs;
    }

    public ArrayList<String> getExerciseNames() {
        return exerciseNames;
    }

    public ArrayList<Double> getExerciseWeightTotals() {
        return exerciseWeightTotals;
    }

    public String getExerciseID(int index) {
        return exerciseIDs.get(index);
    }

    public String getExerciseName(int index) {
        return exerciseNames.get(index);
    }

    public double getExerciseWeightTotal(int index) {
        try {
            return exerciseWeightTotals.get(index);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getWorkoutWeightTotals() {
        double total = 0;
        for (int i = 0; i < exerciseWeightTotals.size(); i++) {
            total += exerciseWeightTotals.get(i);
        }
        return total;
    }

    public int getExerciseCount() {
        return exerciseIDs.size();
    }

    public String getReportTitle() {
        return workoutName + "\n" + date;
    }

    public double getTotalWeight() {
        double total = 0;
        for (int i = 0; i < exerciseWeightTotals.size(); i++) {
            total += exerciseWeightTotals.get(i);
        }
        return total;
    }
}