import java.util.ArrayList;

import org.w3c.dom.events.Event;

public class Exercise implements Config, Saveable {
    public UniqueID createdByID;
    public UniqueID workoutID;
    public Workout workout;
    public UniqueID exerciseID;
    public String exerciseName;
    public ExerciseCatagory exerciseCatagory;
    public ExerciseMuscleGroup exerciseMuscleGroup;
    public ActivityState state;
    public String exerciseDescription; // not on class diagram
    public ArrayList<Event> events = new ArrayList<Event>();
    public ArrayList<Set> sets = new ArrayList<Set>();

    public Exercise(Workout workout) {
        this.workout = workout;
        this.workoutID = workout.workoutID;
        this.exerciseID = new UniqueID(IDType.EXERCISE, workout.getUser(), this);
        this.state = ActivityState.NOT_STARTED;
        this.createdByID = workout.getUser().getID();
    }

    public Exercise(UniqueID workoutID) {
        this.workoutID = workoutID;
        this.workout = (Workout)UniqueID.getLinked(this.workoutID);
        this.exerciseID = new UniqueID(IDType.EXERCISE, workout.getUser(), this);
        this.state = ActivityState.NOT_STARTED;
        this.createdByID = workout.getUser().getID();
    }

    public UniqueID getID() {
        return this.exerciseID;
    }

    public Set addSet() {
        if (this.state == ActivityState.COMPLETED) {
            // Send message to ensure user knows workout is completed - this will flag as modified
            // TODO - Check if workout is completed and if so, unlock (inside workout)
            //this.workout.modified = true;
            this.state = ActivityState.IN_PROGRESS;
        }
        if (this.state == ActivityState.NOT_STARTED) {
            this.state = ActivityState.IN_PROGRESS;
        }
        this.sets.add(new Set(this));
        return this.sets.get(this.sets.size() - 1);
    }

    public Set addSetWithValues(float weight, int reps) {
        if (this.state == ActivityState.COMPLETED) {
            // Send message to ensure user knows workout is completed - this will flag as modified
            // TODO - Check if workout is completed and if so, unlock (inside workout)
            //this.workout.modified = true;
            this.state = ActivityState.IN_PROGRESS;
        }
        if (this.state == ActivityState.NOT_STARTED) {
            this.state = ActivityState.IN_PROGRESS;
        }
        this.sets.add(new Set(this, weight, reps));
        return this.sets.get(this.sets.size() - 1);
    }

    @Override
    public void save() {
        if (User.mainUser == this.workout.getUser()) {
            for (Set set : this.sets) {
                set.save();
            }
        }
                
    }
}
