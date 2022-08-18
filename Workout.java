import java.util.ArrayList;

public class Workout implements Config {
    public UniqueID userID;
    public UniqueID workoutID;
    public ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    public ActivityState state;
    public boolean modified;
}
