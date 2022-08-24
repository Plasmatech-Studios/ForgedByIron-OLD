public class DataManager implements Config {

    // SQLite database
    //private static SQLiteDatabase db;

    // User Table    



    public static void initObjectsFromDB() {
        initUniqueIDsFromDB();
        buildObjects();
        linkObjectsOnLoad(); // Link all objects to necessary objects
        populateObjectsOnLoad(); // populate the remaining data inside each object.
    }

    public static void initUniqueIDsFromDB() {
        // TODO - Get all unique IDs from DB and add to UniqueID.uniqueIDs
        new UniqueID("uniqueID", IDType.USER); // TODO placeholder
    }

    private static void buildObjects() {
        for (UniqueID uniqueID : UniqueID.allUniqueIDsByID.values()) {
            if (uniqueID.getType() == UniqueID.IDType.USER) {
                createLinkedUser(uniqueID);
            }
        }
        for (UniqueID uniqueID : UniqueID.allUniqueIDsByID.values()) {
            if (uniqueID.getType() == IDType.WORKOUT) {
                createLinkedWorkout(uniqueID);
            }
        }
        for (UniqueID uniqueID : UniqueID.allUniqueIDsByID.values()) {
            if (uniqueID.getType() == IDType.EXERCISE) {
                createLinkedExercise(uniqueID);
            }
        }
        for (UniqueID uniqueID : UniqueID.allUniqueIDsByID.values()) {
            if (uniqueID.getType() == IDType.SET) {
                createLinkedSet(uniqueID);
            }
        }
    }

    private static void linkObjectsOnLoad() {
        for (Object workoutID : UniqueID.linkedObjectMap.values()) {
            if (workoutID.getClass() == Workout.class) { // if the object is a workout
                Workout workout = (Workout) workoutID; // cast the object to a workout
                User user = workout.getUser(); // get the user from the workout
                user.workoutHistory.add(workout.workoutID); // add the workout to the user's workout history
            }
        }

        for (Object exerciseID : UniqueID.linkedObjectMap.values()) {
            if (exerciseID.getClass() == Exercise.class) { // if the object is a exercise
                Exercise exercise = (Exercise) exerciseID; // cast the object to a exercise
                Workout workout = exercise.getWorkout(); // get the workout from the exercise
                workout.exercises.add(exercise.exerciseID); // add the exercise to the workout's exercise list
            }
        }

        for (Object setID : UniqueID.linkedObjectMap.values()) {
            if (setID.getClass() == Set.class) { // if the object is a set
                Set set = (Set) setID; // cast the object to a set
                Exercise exercise = set.getExercise(); // get the exercise from the set
                exercise.sets.add(set.setID); // add the set to the exercise's set list
            }
        }
    }

    private static void populateObjectsOnLoad() {
        for (Object workoutID : UniqueID.linkedObjectMap.values()) {
            if (workoutID.getClass() == Workout.class) { // if the object is a workout
                Workout workout = (Workout) workoutID; // cast the object to a workout
                // populate the workout from the DB
            }
        }
        for (Object exerciseID : UniqueID.linkedObjectMap.values()) {
            if (exerciseID.getClass() == Exercise.class) { // if the object is a exercise
                Exercise exercise = (Exercise) exerciseID; // cast the object to a exercise
                // populate the exercise from the DB
            }
        }
        for (Object setID : UniqueID.linkedObjectMap.values()) {
            if (setID.getClass() == Set.class) { // if the object is a set
                Set set = (Set) setID; // cast the object to a set
                // populate the set from the DB
            }
        }
    }

    private static void createLinkedUser(UniqueID userID) {
        User user = new User(userID);
        UniqueID.userMap.put(userID, user);
    }

    private static void createLinkedWorkout(UniqueID workoutID) {
        // TODO - get user id from Database
        UniqueID userID = new UniqueID("U" + workoutID.getUniqueID(), IDType.USER); // TODO - TEMP SOLUTION - REPLACE WITH LINKED USER FROM DB
        Workout workout = new Workout(userID, workoutID);
        UniqueID.linkedObjectMap.put(workoutID, workout);
    }

    private static void createLinkedExercise(UniqueID exerciseID) {
        // TODO - get user id from Database
        UniqueID createdByID = new UniqueID("CB" + exerciseID.getUniqueID(), IDType.USER); // TODO - TEMP SOLUTION - REPLACE WITH LINKED USER FROM DB
        UniqueID workoutID = new UniqueID("W" + exerciseID.getUniqueID(), IDType.WORKOUT); // TODO - TEMP SOLUTION - REPLACE WITH LINKED WORKOUT FROM DB
        Exercise exercise = new Exercise(createdByID, workoutID, exerciseID);
        UniqueID.linkedObjectMap.put(exerciseID, exercise);
    }

    private static void createLinkedSet(UniqueID setID) {
        // TODO - get user id from Database
        UniqueID exerciseID = new UniqueID("E" + setID.getUniqueID(), IDType.WORKOUT); // TODO - TEMP SOLUTION - REPLACE WITH LINKED EXERCISE FROM DB
        Set set = new Set(exerciseID, setID);
        UniqueID.linkedObjectMap.put(setID, set);
    }

    private static void getTableFromDB() {
        // TODO - get table from DB
    }

    private static void getColumnFromDB() {
        // TODO - get column from DB
    }

    private static void getRowFromDB() {
        // TODO - get row from DB
    }

    private static void getValueFromDB() {
        // TODO - get value from DB
    }
}
