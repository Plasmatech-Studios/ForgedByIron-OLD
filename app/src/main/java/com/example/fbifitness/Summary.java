package com.example.fbifitness;

import java.sql.Date;

public class Summary implements Config, Saveable {

    private String userID;
    private String displayName;
    private String bio;
    private String weight;
    private String fat;
    private String longestRun;
    private String benchPR;
    private String deadliftPR;
    private String squatPR;

//     private Gender gender;
//     private Age ageBrack;
    public Summary() { // ONLY FOR DATABASE LOADING

    }

    public Summary(String userID) {
        this.userID = userID;
        this.displayName = "";
        this.bio = "My Bio";
        this.weight = "0kg";
        this.fat = "0%";
        this.longestRun = "0km";
        this.benchPR = "0kg";
        this.deadliftPR = "0kg";
        this.squatPR = "0kg";
        save();
    }

    public Summary(String userID, String displayName, String bio, String weight, String fat, String longestRun, String benchPR, String deadliftPR, String squatPR) {
        this.userID = userID;
        this.displayName = displayName;
        this.bio = bio;
        this.weight = weight;
        this.fat = fat;
        this.longestRun = longestRun;
        this.benchPR = benchPR;
        this.deadliftPR = deadliftPR;
        this.squatPR = squatPR;
        this.save();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
        save();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        save();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
        save();
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
        save();
    }

    public String getLongestRun() {
        return longestRun;
    }

    public void setLongestRun(String longestRun) {
        this.longestRun = longestRun;
        save();
    }

    public String getBenchPR() {
        return benchPR;
    }

    public void setBenchPR(String benchPR) {
        this.benchPR = benchPR;
        save();
    }

    public String getDeadliftPR() {
        return deadliftPR;
    }

    public void setDeadliftPR(String deadliftPR) {
        this.deadliftPR = deadliftPR;
        save();
    }

    public String getSquatPR() {
        return squatPR;
    }

    public void setSquatPR(String squatPR) {
        this.squatPR = squatPR;
        save();
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
        save();
    }




     @Override
     public void save() {
         // TODO Auto-generated method stub
         DataManager.saveSummary(this);
        
     }



    // //getters and setters
    // public UniqueID getSummaryID() {
    //     return this.summaryID;
    // }

    // public UniqueID getUserID() {
    //     return this.userID;
    // }

    // public User getUser() {
    //     return UniqueID.getUserByID(this.userID);
    // }

    // public float getWeight() {
    //     return this.weight;
    // }

    // public void setWeight(float weight) {
    //     this.weight = weight;
    // }

    // public float getHeight() {
    //     return this.height;
    // }

    // public void setHeight(float height) {
    //     this.height = height;
    // }

    // public Gender getGender() {
    //     return this.gender;
    // }

    // public void setGender(Gender gender) {
    //     this.gender = gender;
    // }

    // public Age getAgeBrack() {
    //     return this.ageBrack;
    // }

    // public void setAgeBrack(Age ageBrack) {
    //     this.ageBrack = ageBrack;
    // }

    // public float getBodyFat() {
    //     return this.bodyFat;
    // }

    // public void setBodyFat(float bodyFat) {
    //     this.bodyFat = bodyFat;
    // }

    // public float getLongestRun() {
    //     return this.longestRun;
    // }

    // public void setLongestRun(float longestRun) {
    //     this.longestRun = longestRun;
    // }

    // public float getBenchPR() {
    //     return this.benchPR;
    // }

    // public void setBenchPR(float benchPR) {
    //     this.benchPR = benchPR;
    // }

    // public float getDeadliftPR() {
    //     return this.deadliftPR;
    // }

    // public void setDeadliftPR(float deadliftPR) {
    //     this.deadliftPR = deadliftPR;
    // }

    // public float getSquatPR() {
    //     return this.squatPR;
    // }

    // public void setSquatPR(float squatPR) {
    //     this.squatPR = squatPR;
    // }

    // public Date getDateUpdated() {
    //     return this.dateUpdated;
    // }

    // public void setDateUpdated(Date dateUpdated) {
    //     this.dateUpdated = dateUpdated;
    // }
}