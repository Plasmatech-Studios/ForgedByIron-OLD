package com.example.fbifitness;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper implements Config {

    static final String LOCAL_DB_NAME = "FBI.db";
    static final int LOCAL_DB_VERSION = 1;


    static final String LOGIN_TABLE = "LOGIN";
    static final String LOGIN_USERNAME = "username";
    static final String LOGIN_USER_ID = "userID";

    static final String AUTH_TABLE = "AUTH";
    static final String AUTH_USER_ID = "userID";
    static final String AUTH_SECRET_KEY = "secretKey";

    static final String USER_TABLE = "USER";
    static final String USER_USER_ID = "userID";
    static final String USER_USERNAME = "username";
    static final String USER_SECRET_KEY = "secretKey";

    static final String WORKOUT_TABLE = "WORKOUT";
    static final String WORKOUT_WORKOUT_ID = "workoutID";
    static final String WORKOUT_USER_ID = "userID";
    static final String WORKOUT_CREATED_BY_ID = "createdByID";
    static final String WORKOUT_WORKOUT_NAME = "workoutName";
    static final String WORKOUT_STATE = "state";
    static final String WORKOUT_MODIFIED = "modified";
    static final String WORKOUT_TIME_STARTED = "timeStarted";
    static final String WORKOUT_TIME_COMPLETED = "timeCompleted";
    static final String WORKOUT_TOTAL_TIME = "totalTime";

    static final String EXERCISE_TABLE = "EXERCISE";
    static final String EXERCISE_EXERCISE_ID = "exerciseID";
    static final String EXERCISE_WORKOUT_ID = "workoutID";
    static final String EXERCISE_EXERCISE_TYPE_ID = "exerciseTypeID";
    static final String EXERCISE_USER_ID = "userID";
    static final String EXERCISE_CREATED_BY_ID = "createdByID";
    static final String EXERCISE_STATE = "state";
    static final String EXERCISE_TIME_STARTED = "timeStarted";
    static final String EXERCISE_TIME_COMPLETED = "timeCompleted";
    static final String EXERCISE_TOTAL_TIME = "totalTime";

    static final String SET_TABLE = "'SET'";
    static final String SET_SET_ID = "setID";
    static final String SET_EXERCISE_ID = "exerciseID";
    static final String SET_WEIGHT = "weight";
    static final String SET_REPS = "reps";
    static final String SET_SET_NUMBER = "setNumber";
    static final String SET_TIME_SECONDS = "timeSeconds";
    static final String SET_TYPE = "setType";
    static final String SET_STATE = "state";
    static final String SET_TIME_STARTED = "timeStarted";
    static final String SET_TIME_COMPLETED = "timeCompleted";
    static final String SET_TOTAL_TIME = "totalTime";

    static final String EXERCISE_TYPE_TABLE = "EXERCISE_TYPE";
    static final String EXERCISE_TYPE_EXERCISE_TYPE_ID = "exerciseTypeID";
    static final String EXERCISE_TYPE_EXERCISE_NAME = "exerciseName";
    static final String EXERCISE_TYPE_EXERCISE_DESCRIPTION = "exerciseDescription";
    static final String EXERCISE_TYPE_EXERCISE_CATEGORY = "exerciseCategory";
    static final String EXERCISE_TYPE_EXERCISE_MUSCLE_GROUP = "exerciseMuscleGroup";

    static final String DROP_LOGIN_TABLE =
            "DROP TABLE IF EXISTS " + LOGIN_TABLE + ";";
    static final String DROP_AUTH_TABLE =
            "DROP TABLE IF EXISTS " + AUTH_TABLE + ";";
    static final String DROP_USER_TABLE =
            "DROP TABLE IF EXISTS " + USER_TABLE + ";";
    static final String DROP_WORKOUT_TABLE =
            "DROP TABLE IF EXISTS " + WORKOUT_TABLE + ";";
    static final String DROP_EXERCISE_TABLE =
            "DROP TABLE IF EXISTS " + EXERCISE_TABLE + ";";
    static final String DROP_SET_TABLE =
            "DROP TABLE IF EXISTS " + SET_TABLE + ";";
    static final String DROP_EXERCISE_TYPE_TABLE =
            "DROP TABLE IF EXISTS " + EXERCISE_TYPE_TABLE + ";";

    static final String CREATE_LOCAL_LOGIN_TABLE_QUERY =
        "CREATE TABLE " + LOGIN_TABLE +" (" + LOGIN_USERNAME + " TEXT PRIMARY KEY NOT NULL, " + LOGIN_USER_ID + " TEXT NOT NULL, " +
        "FOREIGN KEY (" + LOGIN_USER_ID + ") REFERENCES " + AUTH_TABLE + " (" + AUTH_USER_ID + "), " +
        "FOREIGN KEY (" + LOGIN_USERNAME + ") REFERENCES " + USER_TABLE + " (" + USER_USERNAME + "));";

    static final String CREATE_LOCAL_AUTH_TABLE_QUERY =
        "CREATE TABLE " + AUTH_TABLE + " (" + AUTH_USER_ID + " TEXT PRIMARY KEY NOT NULL, " + AUTH_SECRET_KEY + " TEXT NOT NULL, " +
        "FOREIGN KEY (" + AUTH_USER_ID + ") REFERENCES " + USER_TABLE + " (" + USER_USER_ID + "), " +
        "FOREIGN KEY (" + AUTH_SECRET_KEY + ") REFERENCES " + USER_TABLE + " (" + USER_SECRET_KEY + "));";

    static final String CREATE_LOCAL_USER_TABLE_QUERY =
        "CREATE TABLE " + USER_TABLE + " (" + USER_USER_ID + " TEXT PRIMARY KEY NOT NULL, " + USER_USERNAME + " TEXT NOT NULL, " + USER_SECRET_KEY + " TEXT NOT NULL, " +
        "FOREIGN KEY (" + USER_USERNAME + ") REFERENCES " + LOGIN_TABLE + " (" + LOGIN_USERNAME + "), " +
        "FOREIGN KEY (" + USER_USER_ID + ") REFERENCES " + AUTH_TABLE + " (" + AUTH_USER_ID + "), " +
        "FOREIGN KEY (" + USER_SECRET_KEY + ") REFERENCES " + AUTH_TABLE + " (" + AUTH_SECRET_KEY + "));";

    static final String CREATE_LOCAL_WORKOUT_TABLE_QUERY =
        "CREATE TABLE " + WORKOUT_TABLE + " (" + WORKOUT_WORKOUT_ID + " TEXT PRIMARY KEY NOT NULL, " + WORKOUT_USER_ID + " TEXT NOT NULL, " + WORKOUT_CREATED_BY_ID + " TEXT NOT NULL, " +
        "" + WORKOUT_WORKOUT_NAME + " TEXT NOT NULL, " + WORKOUT_STATE + " TEXT NOT NULL, " + WORKOUT_MODIFIED + " INT NOT NULL, " +
        "" + WORKOUT_TIME_STARTED + " REAL NOT NULL, " + WORKOUT_TIME_COMPLETED + " REAL NOT NULL, " + WORKOUT_TOTAL_TIME + " REAL NOT NULL, " +
        "FOREIGN KEY (" + WORKOUT_USER_ID + ") REFERENCES " + USER_TABLE + " (" + USER_USER_ID + "), " +
        "FOREIGN KEY (" + WORKOUT_CREATED_BY_ID + ") REFERENCES " + USER_TABLE + " (" + USER_USER_ID + "));";

    static final String CREATE_LOCAL_EXERCISE_TABLE_QUERY =
        "CREATE TABLE " + EXERCISE_TABLE + " (" + EXERCISE_EXERCISE_ID + " TEXT PRIMARY KEY NOT NULL, " + EXERCISE_WORKOUT_ID + " TEXT NOT NULL, " + EXERCISE_EXERCISE_TYPE_ID + " TEXT NOT NULL, " +
        "" + EXERCISE_USER_ID + " TEXT NOT NULL, " + EXERCISE_CREATED_BY_ID + " TEXT NOT NULL, " + EXERCISE_STATE + " TEXT NOT NULL, " +
        "" + EXERCISE_TIME_STARTED + " REAL NOT NULL, " + EXERCISE_TIME_COMPLETED + " REAL NOT NULL, " + EXERCISE_TOTAL_TIME + " REAL NOT NULL, " +
        "FOREIGN KEY (" + EXERCISE_WORKOUT_ID + ") REFERENCES " + WORKOUT_TABLE + " (" + WORKOUT_WORKOUT_ID + "), " +
        "FOREIGN KEY (" + EXERCISE_EXERCISE_TYPE_ID + ") REFERENCES " + EXERCISE_TYPE_TABLE + " (" + EXERCISE_EXERCISE_TYPE_ID + "), " +
        "FOREIGN KEY (" + EXERCISE_USER_ID + ") REFERENCES " + USER_TABLE + " (" + USER_USER_ID + "), " +
        "FOREIGN KEY (" + EXERCISE_CREATED_BY_ID + ") REFERENCES " + USER_TABLE + " (" + USER_USER_ID + "));";

    static final String CREATE_LOCAL_SET_TABLE_QUERY =
        "CREATE TABLE " + SET_TABLE + " (" + SET_SET_ID + " TEXT PRIMARY KEY NOT NULL, " + SET_EXERCISE_ID + " TEXT NOT NULL, " +
        "" + SET_WEIGHT + " REAL, " + SET_REPS + " INT, " + SET_SET_NUMBER + " INT, " + SET_TIME_SECONDS + " REAL, " +
        "" + SET_TYPE + " TEXT NOT NULL, " + SET_STATE + " TEXT NOT NULL, " +
        "" + SET_TIME_STARTED + " REAL NOT NULL, " + SET_TIME_COMPLETED + " REAL NOT NULL, " + SET_TOTAL_TIME + " REAL NOT NULL, " +
        "FOREIGN KEY (" + SET_EXERCISE_ID + ") REFERENCES " + EXERCISE_TABLE + " (" + EXERCISE_EXERCISE_ID + "));";

    static final String CREATE_LOCAL_EXERCISE_TYPE_TABLE_QUERY =
        "CREATE TABLE " + EXERCISE_TYPE_TABLE + " (" + EXERCISE_TYPE_EXERCISE_TYPE_ID + " TEXT PRIMARY KEY NOT NULL, " + EXERCISE_TYPE_EXERCISE_NAME + " TEXT NOT NULL," +
        "" + EXERCISE_TYPE_EXERCISE_DESCRIPTION + " TEXT NOT NULL, " + EXERCISE_TYPE_EXERCISE_CATEGORY + " TEXT NOT NULL," +
        "" + EXERCISE_TYPE_EXERCISE_MUSCLE_GROUP + " TEXT NOT NULL);";


    public DatabaseHelper(Context context) {

        super(context, LOCAL_DB_NAME , null, LOCAL_DB_VERSION);

//        // drop the database if it exists
//        context.deleteDatabase("FBI.db");
//        context.deleteDatabase("FBI.db-journal");
//
//        context.deleteDatabase("Forged.db");
//        context.deleteDatabase("Forged.db-journal");
//
//        context.deleteDatabase("Test3.db");
//        context.deleteDatabase("Test3.db-journal");
//
//        context.deleteDatabase("Test4.db");
//        context.deleteDatabase("Test4.db-journal");
//
//        context.deleteDatabase("Test5.db");
//        context.deleteDatabase("Test5.db-journal");
//
//        context.deleteDatabase("Test6.db");
//        context.deleteDatabase("Test6.db-journal");
    }



    @Override
    public void onCreate(SQLiteDatabase database) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
