package com.example.fbifitness;

public interface Config {

    public enum ActivityState {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }

    public enum ExerciseMuscleGroup {
        ARMS,
        BACK,
        CHEST,
        LEGS,
        CORE,
        OTHER
    }

    public enum ExerciseCatagory {
        BODY,
        FREE_WEIGHT,
        MACHINE,
        CABLE,
        CARDIO,
        OTHER
    }

    public enum Gender {
        MALE,
        FEMALE,
        OTHER,
        NA
    }

    public enum Age {
        _0_17,
        _18_34,
        _35_50,
        _50_PLUS       
    }

    public enum IDType {
        USER,
        WORKOUT,
        EXERCISE,
        EXERCISE_TYPE,
        REPORT,
        BADGE,
        SET,
        SUMMARY
        // QR
    }

    public enum ReportType {
        USER_ACTIVITY_REPORT,
        USER_SUMMARY_REPORT,
        WORKOUT_REPORT,
        EXERCISE_REPORT
    }

    public enum SetType {
        WEIGHT,
        TIME
    }
}
