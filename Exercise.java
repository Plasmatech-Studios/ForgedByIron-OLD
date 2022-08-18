import java.util.ArrayList;

import org.w3c.dom.events.Event;

public class Exercise implements Config {
    public UniqueID workoutID;
    public UniqueID exerciseID;
    public String exerciseName;
    public ExerciseCatagory exerciseCatagory;
    public ExerciseMuscleGroup exerciseMuscleGroup;
    public ActivityState state;
    public String exerciseDescription; // not on class diagram
    public ArrayList<Event> events = new ArrayList<Event>();
    public ArrayList<Set> sets = new ArrayList<Set>();
}
