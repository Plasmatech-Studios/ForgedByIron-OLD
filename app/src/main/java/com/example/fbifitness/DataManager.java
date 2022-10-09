package com.example.fbifitness;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Date;

public class DataManager implements Config {
    private DatabaseHelper dbHelper;
    private Context context;
    protected static SQLiteDatabase database;

    public DataManager(Context ctx) {
        context = ctx;
    }

    public DataManager open() throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        //dropAllTables();
        createTables();
        //database.execSQL("DROP TABLE IF EXISTS USER;"); // TODO Remove before production);
        //database.execSQL(DatabaseHelper.CREATE_LOCAL_USER_TABLE_QUERY);

        //database.execSQL(DatabaseHelper.CREATE_LOCAL_USER_TABLE_QUERY);

        return this;

    }

    public void close() {
        dbHelper.close();
    }

    private void dropAllTables() {
        database.execSQL(DatabaseHelper.DROP_AUTH_TABLE);
        database.execSQL(DatabaseHelper.DROP_LOGIN_TABLE);
        database.execSQL(DatabaseHelper.DROP_USER_TABLE);
        database.execSQL(DatabaseHelper.DROP_WORKOUT_TABLE);
        database.execSQL(DatabaseHelper.DROP_EXERCISE_TABLE);
        database.execSQL(DatabaseHelper.DROP_SET_TABLE);
        database.execSQL(DatabaseHelper.DROP_EXERCISE_TYPE_TABLE);
        database.execSQL(DatabaseHelper.DROP_SUMMARY_TABLE);

        Log.d("WARNING", "ALL TABLES DROPPED");
    }

    private void createTables() {
        //database.execSQL(DatabaseHelper.CREATE_LOCAL_AUTH_TABLE_QUERY);
        //database.execSQL(DatabaseHelper.CREATE_LOCAL_LOGIN_TABLE_QUERY);
        database.execSQL(DatabaseHelper.CREATE_LOCAL_USER_TABLE_QUERY);
        database.execSQL(DatabaseHelper.CREATE_LOCAL_WORKOUT_TABLE_QUERY);
        database.execSQL(DatabaseHelper.CREATE_LOCAL_EXERCISE_TABLE_QUERY);
        database.execSQL(DatabaseHelper.CREATE_LOCAL_SUMMARY_TABLE_QUERY);
        //database.execSQL(DatabaseHelper.CREATE_LOCAL_SET_TABLE_QUERY);
        //database.execSQL(DatabaseHelper.CREATE_LOCAL_EXERCISE_TYPE_TABLE_QUERY);
        Log.d("WARNING", "ALL TABLES CREATED");
    }

    public static void saveUser(String inUserID, String inUsername, String inSecretKey, String activeWorkoutID) {
        //Ensure a user with the same ID does not already exist using select count
        String countQuery = "SELECT * FROM " + DatabaseHelper.USER_TABLE + " WHERE " + DatabaseHelper.USER_USER_ID + " = '" + inUserID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", inUserID);
        contentValues.put("username", inUsername);
        contentValues.put("secretKey", inSecretKey);
        if (activeWorkoutID != null) {
            contentValues.put("activeWorkout", activeWorkoutID);
        } else {
            contentValues.putNull("activeWorkout");
        }
        if (cursor.getCount() > 0) {
            database.update(DatabaseHelper.USER_TABLE, contentValues, DatabaseHelper.USER_USER_ID + " = '" + inUserID + "';", null);
        } else {
            database.insert(DatabaseHelper.USER_TABLE, null, contentValues);
        }
    }

    public static void saveNewUser(String inUserID, String inUsername, String inSecretKey) {
        saveUser(inUserID, inUsername, inSecretKey, null);
        //insertAuth(inUserID, inSecretKey);
        //insertLogin(inUsername, inUserID);
    }

    // Check to see if a username exists in the local database
    public static boolean findUsername(String username) {
        String countQuery = "SELECT * FROM " + DatabaseHelper.USER_TABLE + " WHERE " + DatabaseHelper.USER_USERNAME + " = '" + username + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    // Get the userID of a user with a given username
    public static String findUserIDFromUsername(String username) {
        String countQuery = "SELECT userID FROM " + DatabaseHelper.USER_TABLE + " WHERE " + DatabaseHelper.USER_USERNAME + " = '" + username + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        try {
            cursor.moveToFirst();
            return cursor.getString(0);
        } catch (Exception e) {
            Log.d("WARNING", "Error finding user ID from username");
            return null;
        }
    }

    // Get the secretKey of a user with a given username and userID
    public static String getAuthSecretKey(String username, String userID) {
        String countQuery = "SELECT secretKey FROM " + DatabaseHelper.USER_TABLE + " WHERE " + DatabaseHelper.USER_USERNAME + " = '" + username + "' AND " + DatabaseHelper.USER_USER_ID + " = '" + userID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        try {
            cursor.moveToFirst();
            return cursor.getString(0);
        } catch (Exception e) {
            Log.d("WARNING", "Error finding user ID from username");
            return null;
        }
    }

    public static String getActiveWorkoutID(String inUserID) {
        String countQuery = "SELECT activeWorkout FROM " + DatabaseHelper.USER_TABLE + " WHERE " + DatabaseHelper.USER_USER_ID + " = '" + inUserID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        try {
            cursor.moveToFirst();
            return cursor.getString(0);
        } catch (Exception e) {
            Log.d("WARNING", "Error finding user ID from username");
            return null;
        }
    }

    public static boolean workoutExists(String workoutID) {
        String countQuery = "SELECT * FROM " + DatabaseHelper.WORKOUT_TABLE + " WHERE " + DatabaseHelper.WORKOUT_WORKOUT_ID + " = '" + workoutID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    public static String getUserIDFromWorkout(String workoutID) {
        String countQuery = "SELECT userID FROM " + DatabaseHelper.WORKOUT_TABLE + " WHERE " + DatabaseHelper.WORKOUT_WORKOUT_ID + " = '" + workoutID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        try {
            cursor.moveToFirst();
            return cursor.getString(0);
        } catch (Exception e) {
            Log.d("WARNING", "Error finding user ID from workoutID");
            return null;
        }
    }

    public static String getWorkoutName(String workoutID) {
        String countQuery = "SELECT workoutName FROM " + DatabaseHelper.WORKOUT_TABLE + " WHERE " + DatabaseHelper.WORKOUT_WORKOUT_ID + " = '" + workoutID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        try {
            cursor.moveToFirst();
            return cursor.getString(0);
        } catch (Exception e) {
            Log.d("WARNING", "Error finding workoutName from workoutID");
            return null;
        }
    }

    public static String getWorkoutState(String workoutID) {
        String countQuery = "SELECT state FROM " + DatabaseHelper.WORKOUT_TABLE + " WHERE " + DatabaseHelper.WORKOUT_WORKOUT_ID + " = '" + workoutID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        try {
            cursor.moveToFirst();
            return cursor.getString(0);
        } catch (Exception e) {
            Log.d("WARNING", "Error finding state from workoutID");
            return null;
        }
    }

    public static String getWorkoutTimeStarted(String workoutID) {
        String countQuery = "SELECT timeStarted FROM " + DatabaseHelper.WORKOUT_TABLE + " WHERE " + DatabaseHelper.WORKOUT_WORKOUT_ID + " = '" + workoutID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        try {
            cursor.moveToFirst();
            return cursor.getString(0);
        } catch (Exception e) {
            Log.d("WARNING", "Error finding timeStarted from workoutID");
            return null;
        }
    }

    public static String getWorkoutTimeCompleted(String workoutID) {
        String countQuery = "SELECT timeCompleted FROM " + DatabaseHelper.WORKOUT_TABLE + " WHERE " + DatabaseHelper.WORKOUT_WORKOUT_ID + " = '" + workoutID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        try {
            cursor.moveToFirst();
            return cursor.getString(0);
        } catch (Exception e) {
            Log.d("WARNING", "Error finding timeCompleted from workoutID");
            return null;
        }
    }

    public static String getWorkoutTotalTime(String workoutID) {
        String countQuery = "SELECT totalTime FROM " + DatabaseHelper.WORKOUT_TABLE + " WHERE " + DatabaseHelper.WORKOUT_WORKOUT_ID + " = '" + workoutID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        try {
            cursor.moveToFirst();
            return cursor.getString(0);
        } catch (Exception e) {
            Log.d("WARNING", "Error finding totalTime from workoutID");
            return null;
        }
    }

    public static void fillExerciseList(String workoutID) {
        SessionController controller = SessionController.getInstance();
        String countQuery = "SELECT * FROM " + DatabaseHelper.EXERCISE_TABLE + " WHERE " + DatabaseHelper.EXERCISE_WORKOUT_ID + " = '" + workoutID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            return;
        }
        //try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String exerciseID = cursor.getString(0);
                // Workout ID
                String exerciseTypeID = cursor.getString(2);
                String exerciseName = cursor.getString(3);
                String exerciseState = cursor.getString(4);
                String exerciseTimeStarted = cursor.getString(5);
                String exerciseTimeCompleted = cursor.getString(6);
                String exerciseTotalTime = cursor.getString(7);
                String setData = cursor.getString(8);
                Exercise exercise = Exercise.newExerciseFromLoad(workoutID, exerciseID, exerciseTypeID, exerciseName, exerciseState, exerciseTimeStarted, exerciseTimeCompleted, exerciseTotalTime, setData);
                ExerciseListView view = new ExerciseListView(exercise);
                controller.exerciseList.add(view);
                cursor.moveToNext();
            }
//        } catch (Exception e) {
//            Log.e("WARNING", "Error filling exercise list " + e.getMessage());
//        }
    }




//   public static void insertWorkout(String inWorkoutID, String inUserID, String inCreatedByID,
//                             String inWorkoutName, String inState, String inModified,
//                             String inTimeStarted, String inTimeCompleted, String inTotalTime) {
//
//        // Ensure a workout with the same ID does not already exist using select count
//        String countQuery = "SELECT * FROM " + DatabaseHelper.WORKOUT_TABLE + " WHERE " + DatabaseHelper.WORKOUT_WORKOUT_ID + " = '" + inWorkoutID + "';";
//        Cursor cursor = database.rawQuery(countQuery, null);
//        if (cursor.getCount() > 0) {
//            Log.d("WARNING", "Workout with that ID already exists");
//            return;
//        }
//
//       ContentValues contentValues = new ContentValues();
//       contentValues.put(DatabaseHelper.WORKOUT_WORKOUT_ID, inWorkoutID);
//       contentValues.put(DatabaseHelper.WORKOUT_USER_ID, inUserID);
//       contentValues.put(DatabaseHelper.WORKOUT_CREATED_BY_ID, inCreatedByID);
//       contentValues.put(DatabaseHelper.WORKOUT_WORKOUT_NAME, inWorkoutName);
//       contentValues.put(DatabaseHelper.WORKOUT_STATE, inState);
//       contentValues.put(DatabaseHelper.WORKOUT_MODIFIED, inModified);
//       contentValues.put(DatabaseHelper.WORKOUT_TIME_STARTED, inTimeStarted);
//       contentValues.put(DatabaseHelper.WORKOUT_TIME_COMPLETED, inTimeCompleted);
//       contentValues.put(DatabaseHelper.WORKOUT_TOTAL_TIME, inTotalTime);
//
//       database.insert(DatabaseHelper.WORKOUT_TABLE, null, contentValues);
//    }
//
//   public static void insertExercise(String inExerciseID, String inWorkoutID, String inExerciseName,
//                              String inUserID, String inCreatedByID, String inState,
//                              String inTimeStarted, String inTimeCompleted, String inTotalTime) {
//
//        // Ensure an exercise with the same ID does not already exist using select count
//        String countQuery = "SELECT * FROM " + DatabaseHelper.EXERCISE_TABLE + " WHERE " + DatabaseHelper.EXERCISE_EXERCISE_ID + " = '" + inExerciseID + "';";
//        Cursor cursor = database.rawQuery(countQuery, null);
//        if (cursor.getCount() > 0) {
//            Log.d("WARNING", "Exercise with that ID already exists");
//            return;
//        }
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.EXERCISE_EXERCISE_ID, inExerciseID);
//        contentValues.put(DatabaseHelper.WORKOUT_WORKOUT_ID, inWorkoutID);
//        //contentValues.put(DatabaseHelper.EXERCISE_EXERCISE_TYPE_ID, inExerciseTypeID);
//        contentValues.put(DatabaseHelper.EXERCISE_NAME_ID, inExerciseName);
//        contentValues.put(DatabaseHelper.EXERCISE_USER_ID, inUserID);
//        contentValues.put(DatabaseHelper.EXERCISE_CREATED_BY_ID, inCreatedByID);
//        contentValues.put(DatabaseHelper.EXERCISE_STATE, inState);
//        contentValues.put(DatabaseHelper.EXERCISE_TIME_STARTED, inTimeStarted);
//        contentValues.put(DatabaseHelper.EXERCISE_TIME_COMPLETED, inTimeCompleted);
//        contentValues.put(DatabaseHelper.EXERCISE_TOTAL_TIME, inTotalTime);
//
//        database.insert(DatabaseHelper.EXERCISE_TABLE, null, contentValues);
//    }
//
//   public static void insertSet(String inSetID, String inExerciseID, String inWeight, String inReps,
//                              String inSetNumber, String inTimeSeconds, String inSetType, String inState,
//                              String inTimeStarted, String inTimeCompleted, String inTotalTime) {
//
//        // Ensure a set with the same ID does not already exist using select count
//        String countQuery = "SELECT * FROM " + DatabaseHelper.SET_TABLE + " WHERE " + DatabaseHelper.SET_SET_ID + " = '" + inSetID + "';";
//        Cursor cursor = database.rawQuery(countQuery, null);
//        if (cursor.getCount() > 0) {
//            Log.d("WARNING", "Set with that ID already exists");
//            return;
//        }
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.SET_SET_ID, inSetID);
//        contentValues.put(DatabaseHelper.SET_EXERCISE_ID, inExerciseID);
//        contentValues.put(DatabaseHelper.SET_WEIGHT, inWeight);
//        contentValues.put(DatabaseHelper.SET_REPS, inReps);
//        contentValues.put(DatabaseHelper.SET_SET_NUMBER, inSetNumber);
//        contentValues.put(DatabaseHelper.SET_TIME_SECONDS, inTimeSeconds);
//        contentValues.put(DatabaseHelper.SET_TYPE, inSetType);
//        contentValues.put(DatabaseHelper.SET_STATE, inState);
//        contentValues.put(DatabaseHelper.SET_TIME_STARTED, inTimeStarted);
//        contentValues.put(DatabaseHelper.SET_TIME_COMPLETED, inTimeCompleted);
//        contentValues.put(DatabaseHelper.SET_TOTAL_TIME, inTotalTime);
//
//        database.insert(DatabaseHelper.SET_TABLE, null, contentValues);
//    }
//
//   public static void insertExerciseType(String inExerciseTypeID, String inExerciseName, String inExerciseDescription,
//                                  String inExerciseCategory, String inExerciseMuscleGroup) {
//
//        // Ensure an exercise type with the same ID does not already exist using select count
//        String countQuery = "SELECT * FROM " + DatabaseHelper.EXERCISE_TYPE_TABLE + " WHERE " + DatabaseHelper.EXERCISE_TYPE_EXERCISE_TYPE_ID + " = '" + inExerciseTypeID + "';";
//        Cursor cursor = database.rawQuery(countQuery, null);
//        if (cursor.getCount() > 0) {
//            Log.d("WARNING", "Exercise type with that ID already exists");
//            return;
//        }
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.EXERCISE_TYPE_EXERCISE_TYPE_ID, inExerciseTypeID);
//        contentValues.put(DatabaseHelper.EXERCISE_TYPE_EXERCISE_NAME, inExerciseName);
//        contentValues.put(DatabaseHelper.EXERCISE_TYPE_EXERCISE_DESCRIPTION, inExerciseDescription);
//        contentValues.put(DatabaseHelper.EXERCISE_TYPE_EXERCISE_CATEGORY, inExerciseCategory);
//        contentValues.put(DatabaseHelper.EXERCISE_TYPE_EXERCISE_MUSCLE_GROUP, inExerciseMuscleGroup);
//
//        database.insert(DatabaseHelper.EXERCISE_TYPE_TABLE, null, contentValues);
//    }

//    public Cursor fetchUser(String uniqueID) {
//        String [] columns = new String[] {DatabaseHelper.USER_USER_ID, DatabaseHelper.USER_USERNAME, DatabaseHelper.USER_SECRET_KEY};
//        Cursor cursor = database.query(DatabaseHelper.USER_TABLE, columns, DatabaseHelper.USER_USER_ID + "=" + uniqueID, null, null, null, null); // Added the selection
//            if (cursor != null) {
//                cursor.moveToFirst();
//            }
//        return cursor;
//    }
//
//    public static int updateUser(String uniqueID, String inUsername, String inSecretKey) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.USER_USERNAME, inUsername);
//        contentValues.put(DatabaseHelper.USER_SECRET_KEY, inSecretKey);
//        int ret = database.update(DatabaseHelper.USER_TABLE, contentValues, DatabaseHelper.USER_USER_ID + "=" + uniqueID, null); // may need '' marks around uniqueID
//        return ret;
//    }

   public static void deleteUser(String uniqueID) {
        // Ensure user exists
        String countQuery = "SELECT * FROM " + DatabaseHelper.USER_TABLE + " WHERE " + DatabaseHelper.USER_USER_ID + " = '" + uniqueID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            Log.d("WARNING", "User does not exist");
            return;
        }
        
        database.delete(DatabaseHelper.USER_TABLE, DatabaseHelper.USER_USER_ID + " = '" + uniqueID + "';", null);
    }

    public static void saveWorkout(String workoutID) {
        // Ensure workout exists
        String countQuery = "SELECT * FROM " + DatabaseHelper.WORKOUT_TABLE + " WHERE " + DatabaseHelper.WORKOUT_WORKOUT_ID + " = '" + workoutID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            Log.d("WARNING", "Workout does not exist");
            //return;
        }

        // get the workout
        Workout workout = Workout.workouts.get(workoutID);
        String userID = workout.getUserID().toString();
        String workoutName = workout.getName();
        String state = workout.getState().toString();
        String timeStarted;
        String timeCompleted;
        String totalTime;

        if (workout.getTimeStarted() != null) {
            timeStarted = String.valueOf(workout.getTimeStarted().getTime());
        } else {
            timeStarted = null;
        }

        if (workout.getTimeCompleted() != null) {
            timeCompleted = String.valueOf(workout.getTimeCompleted().getTime());
        } else {
            timeCompleted = null;
        }

        if (workout.getTotalTime() != null) {
            totalTime = String.valueOf(workout.getTotalTime().getTime());
        } else {
            totalTime = null;
        }


        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.WORKOUT_USER_ID, userID);
        contentValues.put(DatabaseHelper.WORKOUT_WORKOUT_NAME, workoutName);
        contentValues.put(DatabaseHelper.WORKOUT_STATE, state);
        contentValues.put(DatabaseHelper.WORKOUT_TIME_STARTED, timeStarted);
        contentValues.put(DatabaseHelper.WORKOUT_TIME_COMPLETED, timeCompleted);
        contentValues.put(DatabaseHelper.WORKOUT_TOTAL_TIME, totalTime);

        if (cursor.getCount() == 0) {
            contentValues.put(DatabaseHelper.WORKOUT_WORKOUT_ID, workoutID);
            // insert
            database.insert(DatabaseHelper.WORKOUT_TABLE, null, contentValues);
            Log.d("INFO", "Workout inserted");
        } else {
            // update
            database.update(DatabaseHelper.WORKOUT_TABLE, contentValues, DatabaseHelper.WORKOUT_WORKOUT_ID + " = '" + workoutID + "';", null);
        }
        //database.update(DatabaseHelper.WORKOUT_TABLE, contentValues, DatabaseHelper.WORKOUT_WORKOUT_ID + " = '" + workoutID + "';", null);

    }

    public static int getWorkoutCount(String userID) {
        String countQuery = "SELECT * FROM " + DatabaseHelper.WORKOUT_TABLE + " WHERE " + DatabaseHelper.WORKOUT_USER_ID + " = '" + userID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public static void saveExercise(String exerciseID) {
        // Ensure exercise exists
        String countQuery = "SELECT * FROM " + DatabaseHelper.EXERCISE_TABLE + " WHERE " + DatabaseHelper.EXERCISE_EXERCISE_ID + " = '" + exerciseID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            Log.d("WARNING", "Exercise does not exist");
            //return;
        }

        // get the exercise
        Exercise exercise = Exercise.exercises.get(exerciseID);
        String workoutID = exercise.getWorkoutID().toString();
        String name = exercise.getExerciseName();
        String exerciseTypeID = exercise.getExerciseType().toString();
        String state = exercise.getState().toString();
        String timeStarted;
        String timeCompleted;
        String totalTime;

        String setData;
        StringBuilder builder = new StringBuilder();
        for (Set set : exercise.getSets()) {
            builder.append(set.reps);
            builder.append(",");
            if (set.weight != null) {
                builder.append(set.weight);
            } else{
                builder.append(set.timeSeconds);
            }
            builder.append(",");
            if (set.state != null) {
                builder.append(set.state);
            } else {
                builder.append("null");
            }
            builder.append(";");
        }
        // remove last ;
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
            setData = builder.toString();
        } else {
            setData = null;
        }


        if (exercise.getTimeStarted() != null) {
            timeStarted = String.valueOf(exercise.getTimeStarted().getTime());
        } else {
            timeStarted = null;
        }

        if (exercise.getTimeCompleted() != null) {
            timeCompleted = String.valueOf(exercise.getTimeCompleted().getTime());
        } else {
            timeCompleted = null;
        }

        if (exercise.getTotalTime() != null) {
            totalTime = String.valueOf(exercise.getTotalTime().getTime());
        } else {
            totalTime = null;
        }



        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.EXERCISE_WORKOUT_ID, workoutID);
        contentValues.put(DatabaseHelper.EXERCISE_NAME_ID, name);
        contentValues.put(DatabaseHelper.EXERCISE_EXERCISE_TYPE_ID, exerciseTypeID);
        contentValues.put(DatabaseHelper.EXERCISE_STATE, state);
        contentValues.put(DatabaseHelper.EXERCISE_TIME_STARTED, timeStarted);
        contentValues.put(DatabaseHelper.EXERCISE_TIME_COMPLETED, timeCompleted);
        contentValues.put(DatabaseHelper.EXERCISE_TOTAL_TIME, totalTime);
        contentValues.put(DatabaseHelper.EXERCISE_SET_DATA, setData);

        if (cursor.getCount() == 0) {
            contentValues.put(DatabaseHelper.EXERCISE_EXERCISE_ID, exerciseID);
            // insert
            database.insert(DatabaseHelper.EXERCISE_TABLE, null, contentValues);
            Log.d("INFO", "Exercise inserted");
        } else {
            // update
            database.update(DatabaseHelper.EXERCISE_TABLE, contentValues, DatabaseHelper.EXERCISE_EXERCISE_ID + " = '" + exerciseID + "';", null);
        }

    }


    public static Summary getSummary(String userID) {
        Summary summary = new Summary();
        String countQuery = "SELECT * FROM " + DatabaseHelper.SUMMARY_TABLE + " WHERE " + DatabaseHelper.SUMMARY_USER_ID + " = '" + userID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            Log.d("WARNING", "Summary does not exist");
            return null;
        }
        cursor.moveToFirst();
        summary.setUserID(cursor.getString(0));
        summary.setDisplayName(cursor.getString(1));
        summary.setBio(cursor.getString(2));
        summary.setWeight(cursor.getString(3));
        summary.setFat(cursor.getString(4));
        summary.setLongestRun(cursor.getString(5));
        summary.setBenchPR(cursor.getString(6));
        summary.setDeadliftPR(cursor.getString(7));
        summary.setSquatPR(cursor.getString(8));

        return summary;

    }

    public static void saveSummary(Summary summary) {
        // if any values are null, set them to 0
        if (summary.getWeight() == null) {
            summary.setWeight("0");
        }
        if (summary.getFat() == null) {
            summary.setFat("0");
        }
        if (summary.getLongestRun() == null) {
            summary.setLongestRun("0");
        }
        if (summary.getBenchPR() == null) {
            summary.setBenchPR("0");
        }
        if (summary.getDeadliftPR() == null) {
            summary.setDeadliftPR("0");
        }
        if (summary.getSquatPR() == null) {
            summary.setSquatPR("0");
        }
        if (summary.getDisplayName() == null) {
            summary.setDisplayName("null");
        }
        if (summary.getBio() == null) {
            summary.setBio("null");
        }

        String userID = summary.getUserID();
        String displayName = summary.getDisplayName();
        String bio = summary.getBio();
        String weight = summary.getWeight();
        String fat = summary.getFat();
        String longestRun = summary.getLongestRun();
        String benchPR = summary.getBenchPR();
        String deadliftPR = summary.getDeadliftPR();
        String squatPR = summary.getSquatPR();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUMMARY_USER_ID, userID);
        contentValues.put(DatabaseHelper.SUMMARY_DISPLAY_NAME, displayName);
        contentValues.put(DatabaseHelper.SUMMARY_BIO, bio);
        contentValues.put(DatabaseHelper.SUMMARY_WEIGHT, weight);
        contentValues.put(DatabaseHelper.SUMMARY_FAT, fat);
        contentValues.put(DatabaseHelper.SUMMARY_LONGEST_RUN, longestRun);
        contentValues.put(DatabaseHelper.SUMMARY_BENCH_PR, benchPR);
        contentValues.put(DatabaseHelper.SUMMARY_DEADLIFT_PR, deadliftPR);
        contentValues.put(DatabaseHelper.SUMMARY_SQUAT_PR, squatPR);

        // Ensure summary exists
        String countQuery = "SELECT * FROM " + DatabaseHelper.SUMMARY_TABLE + " WHERE " + DatabaseHelper.SUMMARY_USER_ID + " = '" + userID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() == 0) {
            // insert
            database.insert(DatabaseHelper.SUMMARY_TABLE, null, contentValues);
            Log.d("INFO", "Summary inserted");
        } else {
            // update
            database.update(DatabaseHelper.SUMMARY_TABLE, contentValues, DatabaseHelper.SUMMARY_USER_ID + " = '" + userID + "';", null);
        }


    }






















//     public static void initObjectsFromDB() {
//         initUniqueIDsFromDB();
//         buildObjects();
//         linkObjectsOnLoad(); // Link all objects to necessary objects
//         populateObjectsOnLoad(); // populate the remaining data inside each object.
//     }

//     public static void initUniqueIDsFromDB() {
//         // TODO - Get all unique IDs from DB and add to UniqueID.uniqueIDs
//         new UniqueID("uniqueID", IDType.USER); // TODO placeholder
//     }

//     private static void buildObjects() {
//         for (UniqueID uniqueID : UniqueID.allUniqueIDsByID.values()) {
//             if (uniqueID.getType() == UniqueID.IDType.USER) {
//                 createLinkedUser(uniqueID);
//             }
//         }
//         for (UniqueID uniqueID : UniqueID.allUniqueIDsByID.values()) {
//             if (uniqueID.getType() == IDType.WORKOUT) {
//                 createLinkedWorkout(uniqueID);
//             }
//         }
//         //exercisetype
//         for (UniqueID uniqueID : UniqueID.allUniqueIDsByID.values()) {
//             if (uniqueID.getType() == IDType.EXERCISE_TYPE) {
//                 createLinkedExerciseType(uniqueID);
//             }
//         }
//         for (UniqueID uniqueID : UniqueID.allUniqueIDsByID.values()) {
//             if (uniqueID.getType() == IDType.EXERCISE) {
//                 createLinkedExercise(uniqueID);
//             }
//         }
//         for (UniqueID uniqueID : UniqueID.allUniqueIDsByID.values()) {
//             if (uniqueID.getType() == IDType.SET) {
//                 createLinkedSet(uniqueID);
//             }
//         }
//     }

//     private static void linkObjectsOnLoad() {
//         for (Object workoutID : UniqueID.linkedObjectMap.values()) {
//             if (workoutID.getClass() == Workout.class) { // if the object is a workout
//                 Workout workout = (Workout) workoutID; // cast the object to a workout
//                 User user = workout.getUser(); // get the user from the workout
//                 user.workoutHistory.add(workout.workoutID); // add the workout to the user's workout history
//             }
//         }

// //        for (ExerciseType exerciseType : UniqueID.exerciseTypeMap.values()) {
// //            exerciseType.exerciseTypeID().setExerciseType(exerciseType); // Is this correct?
// //        }

//         for (Object exerciseID : UniqueID.linkedObjectMap.values()) {
//             if (exerciseID.getClass() == Exercise.class) { // if the object is a exercise
//                 Exercise exercise = (Exercise) exerciseID; // cast the object to a exercise
//                 Workout workout = exercise.getWorkout(); // get the workout from the exercise
//                 workout.exercises.add(exercise.exerciseID); // add the exercise to the workout's exercise list
//             }
//         }

//         for (Object setID : UniqueID.linkedObjectMap.values()) {
//             if (setID.getClass() == Set.class) { // if the object is a set
//                 Set set = (Set) setID; // cast the object to a set
//                 Exercise exercise = set.getExercise(); // get the exercise from the set
//                 exercise.sets.add(set.setID); // add the set to the exercise's set list
//             }
//         }
//     }

//     private static void populateObjectsOnLoad() {
//         for (Object workoutID : UniqueID.linkedObjectMap.values()) {
//             if (workoutID.getClass() == Workout.class) { // if the object is a workout
//                 Workout workout = (Workout) workoutID; // cast the object to a workout
//                 // populate the workout from the DB
//             }
//         }
//         for (Object exerciseID : UniqueID.linkedObjectMap.values()) {
//             if (exerciseID.getClass() == Exercise.class) { // if the object is a exercise
//                 Exercise exercise = (Exercise) exerciseID; // cast the object to a exercise
//                 // populate the exercise from the DB
//             }
//         }
//         for (Object setID : UniqueID.linkedObjectMap.values()) {
//             if (setID.getClass() == Set.class) { // if the object is a set
//                 Set set = (Set) setID; // cast the object to a set
//                 // populate the set from the DB
//             }
//         }
//     }

//     private static void createLinkedUser(UniqueID userID) {
//         User user = new User(userID);
//         UniqueID.userMap.put(userID, user);
//     }

//     private static void createLinkedWorkout(UniqueID workoutID) {
//         // TODO - get user id from Database
//         UniqueID userID = new UniqueID("U" + workoutID.getUniqueID(), IDType.USER); // TODO - TEMP SOLUTION - REPLACE WITH LINKED USER FROM DB
//         Workout workout = new Workout(userID, workoutID);
//         UniqueID.linkedObjectMap.put(workoutID, workout);
//     }

//     private static void createLinkedExerciseType(UniqueID exerciseTypeID) {
//         //ExerciseType exerciseType = ExerciseType.createNewExerciseType(exerciseTypeID);
//         //UniqueID.exerciseTypeMap.put(exerciseTypeID, exerciseType);
//     }

//     private static void createLinkedExercise(UniqueID exerciseID) {
//         // TODO - get user id from Database
//         UniqueID createdByID = new UniqueID("CB" + exerciseID.getUniqueID(), IDType.USER); // TODO - TEMP SOLUTION - REPLACE WITH LINKED USER FROM DB
//         UniqueID workoutID = new UniqueID("W" + exerciseID.getUniqueID(), IDType.WORKOUT); // TODO - TEMP SOLUTION - REPLACE WITH LINKED WORKOUT FROM DB
//         Exercise exercise = new Exercise(createdByID, workoutID, exerciseID);
//         UniqueID.linkedObjectMap.put(exerciseID, exercise);
//     }

//     private static void createLinkedSet(UniqueID setID) {
//         // TODO - get user id from Database
//         UniqueID exerciseID = new UniqueID("E" + setID.getUniqueID(), IDType.WORKOUT); // TODO - TEMP SOLUTION - REPLACE WITH LINKED EXERCISE FROM DB
//         Set set = new Set(exerciseID, setID);
//         UniqueID.linkedObjectMap.put(setID, set);
//     }

//     private static void getTableFromDB() {
//         // TODO - get table from DB
//     }

//     private static void getColumnFromDB() {
//         // TODO - get column from DB
//     }

//     private static void getRowFromDB() {
//         // TODO - get row from DB
//     }

//     private static void getValueFromDB() {
//         // TODO - get value from DB
//     }
}
