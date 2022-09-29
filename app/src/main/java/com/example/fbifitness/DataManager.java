package com.example.fbifitness;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLDataException;

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
        dropAllTables();
        createTables();
        database.execSQL("DROP TABLE IF EXISTS USER;"); // TODO Remove before production);
        database.execSQL(DatabaseHelper.CREATE_LOCAL_USER_TABLE_QUERY);

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
        Log.d("WARNING", "ALL TABLES DROPPED");
    }

    private void createTables() {
        database.execSQL(DatabaseHelper.CREATE_LOCAL_AUTH_TABLE_QUERY);
        database.execSQL(DatabaseHelper.CREATE_LOCAL_LOGIN_TABLE_QUERY);
        database.execSQL(DatabaseHelper.CREATE_LOCAL_USER_TABLE_QUERY);
        database.execSQL(DatabaseHelper.CREATE_LOCAL_WORKOUT_TABLE_QUERY);
        database.execSQL(DatabaseHelper.CREATE_LOCAL_EXERCISE_TABLE_QUERY);
        //database.execSQL(DatabaseHelper.CREATE_LOCAL_SET_TABLE_QUERY);
        //database.execSQL(DatabaseHelper.CREATE_LOCAL_EXERCISE_TYPE_TABLE_QUERY);
        Log.d("WARNING", "ALL TABLES CREATED");
    }

    public static void insertLogin(String inUsername, String inUserID) {
        // Ensure a user with the same ID does not already exist using select count
        String countQuery = "SELECT * FROM " + DatabaseHelper.LOGIN_TABLE + " WHERE " + DatabaseHelper.LOGIN_USER_ID + " = '" + inUserID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0) {
            Log.d("WARNING", "User with that ID already exists");
            return;
        }
            
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.LOGIN_USERNAME, inUsername);
        contentValues.put(DatabaseHelper.LOGIN_USER_ID, inUserID);

        database.insert(DatabaseHelper.LOGIN_TABLE, null, contentValues);
    }

   public static void insertAuth(String inUserID, String inSecretKey) {
        // Ensure a user with the same ID does not already exist using select count
        String countQuery = "SELECT * FROM " + DatabaseHelper.AUTH_TABLE + " WHERE " + DatabaseHelper.AUTH_USER_ID + " = '" + inUserID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0) {
            Log.d("WARNING", "User with that ID already exists");
            return;
        }
        
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.AUTH_USER_ID, inUserID);
        contentValues.put(DatabaseHelper.AUTH_SECRET_KEY, inSecretKey);

        database.insert(DatabaseHelper.AUTH_TABLE, null, contentValues);
    }

    public static void insertUser(String inUserID, String inUsername, String inSecretKey) {
        //Ensure a user with the same ID does not already exist using select count
        String countQuery = "SELECT * FROM " + DatabaseHelper.USER_TABLE + " WHERE " + DatabaseHelper.USER_USER_ID + " = '" + inUserID + "';";
        Cursor cursor = database.rawQuery(countQuery, null);
        if (cursor.getCount() > 0) {
            Log.d("WARNING", "User with that ID already exists");
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", inUserID);
        contentValues.put("username", inUsername);
        contentValues.put("secretKey", inSecretKey);

        database.insert(DatabaseHelper.USER_TABLE, null, contentValues);
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
