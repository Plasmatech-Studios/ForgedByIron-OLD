public record ExerciseList(String exerciseName, ExerciseCatagory exerciseCatagory, ExerciseMuscleGroup exerciseMuscleGroup, String exerciseDescription) implements Config{
    public static ExerciseList el_benchpress = new ExerciseList("Bench Press", ExerciseCatagory.FREE_WEIGHT, ExerciseMuscleGroup.CHEST, "This is the bench press exercise.");
}