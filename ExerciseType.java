public record ExerciseType(UniqueID exerciseTypeID, String exerciseName, ExerciseCatagory exerciseCatagory, ExerciseMuscleGroup exerciseMuscleGroup, String exerciseDescription) implements Config{
    public static ExerciseType el_benchpress = new ExerciseType(new UniqueID(), "Bench Press", ExerciseCatagory.FREE_WEIGHT, ExerciseMuscleGroup.CHEST, "This is the bench press exercise.");

    public static ExerciseType createNewExerciseType(UniqueID id){
        return new ExerciseType(id,
                                getNameFromDB(id),
                                getCatagoryFromDB(id),
                                getMuscleGroupFromDB(id),
                                getDescriptionFromDB(id));
    }

    public static String getNameFromDB(UniqueID id){
        // TODO: Get the name from the database
        return "New Exercise";
    }

    public static ExerciseCatagory getCatagoryFromDB(UniqueID id){
        // TODO: Get the catagory from the database
        return ExerciseCatagory.OTHER;
    }

    public static ExerciseMuscleGroup getMuscleGroupFromDB(UniqueID id){
        // TODO: Get the muscle group from the database
        return ExerciseMuscleGroup.OTHER;
    }

    public static String getDescriptionFromDB(UniqueID id){
        // TODO: Get the description from the database
        return "This is a new exercise.";
    }
}



//    public String exerciseName;
//public ExerciseCatagory exerciseCatagory//;
//public ExerciseMuscleGroup exerciseMuscleGroup;
//    public String exerciseDescription; // not on class diagram
