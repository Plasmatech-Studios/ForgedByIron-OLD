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

    public Set addSet(SetType setType) {
        if (this.state == ActivityState.COMPLETED) {
            // Send message to ensure user knows workout is completed - this will flag as modified
            // TODO - Check if workout is completed and if so, unlock (inside workout)
            //this.workout.modified = true;
            this.state = ActivityState.IN_PROGRESS;
        }
        if (this.state == ActivityState.NOT_STARTED) {
            this.state = ActivityState.IN_PROGRESS;
        }
        this.sets.add(new Set(this, setType));
        return this.sets.get(this.sets.size() - 1);
    }

    public Set addSet(SetType type, float weight, int reps) {
        if (this.state == ActivityState.COMPLETED) {
            // Send message to ensure user knows workout is completed - this will flag as modified
            // TODO - Check if workout is completed and if so, unlock (inside workout)
            //this.workout.modified = true;
            this.state = ActivityState.IN_PROGRESS;
        }
        if (this.state == ActivityState.NOT_STARTED) {
            this.state = ActivityState.IN_PROGRESS;
        }
        this.sets.add(new Set(this, type, weight, reps));
        System.out.println("Empty Set of type: " + type + " added to Exercise");
        return this.sets.get(this.sets.size() - 1);
    }

    public void completeSets() {
        if (this.state == ActivityState.IN_PROGRESS) {
            for (Set set : this.sets) {
                set.state = ActivityState.COMPLETED;
            }
            this.state = ActivityState.COMPLETED;
        } else {
            System.out.println("Exercise not in progress");
        }

    }

    public boolean completeExercise() {
        if (this.state == ActivityState.IN_PROGRESS) { // if exercise is in progress, check if all sets are completed
            // if exercise contains unfinshed sets, remove them
            for (int i = 0; i < this.sets.size(); i++) {
                if (this.sets.get(i).state == ActivityState.IN_PROGRESS) {
                    // TODO prompt the user
                    this.sets.remove(i);
                    i--; // TODO is this correct?
                }
                i++;
            }
            // if exercise contains no sets, delete exercise
            if (this.sets.size() == 0) {
                this.workout.deleteExercise(this);
                return false;
            }
        }
        if (this.state == ActivityState.NOT_STARTED) {
            System.out.println("Attempting to complete Exercise is in state NOT_STARTED");
            return false;
        }
        if (this.state == ActivityState.COMPLETED) {
            System.out.println("Attempting to complete Exercise already completed");
            return false;
        }
        this.state = ActivityState.COMPLETED;
        return true;
    }
    

    public Set addSetWithValues(SetType type, float weight, int reps) {
        if (this.state == ActivityState.COMPLETED) {
            // Send message to ensure user knows workout is completed - this will flag as modified
            // TODO - Check if workout is completed and if so, unlock (inside workout)
            //this.workout.modified = true;
            this.state = ActivityState.IN_PROGRESS;
        }
        if (this.state == ActivityState.NOT_STARTED) {
            this.state = ActivityState.IN_PROGRESS;
        }
        this.sets.add(new Set(this, type, weight, reps));
        System.out.println("Added set with values, of type: " + type + " to exercise");
        return this.sets.get(this.sets.size() - 1);
    }

    public boolean deleteSet(Set set) {
        if (this.sets.contains(set)) {
            this.sets.remove(set);
            return true;
        }
        System.out.println("Attempting to delete Set that does not exist in this exercise");
        return false;
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
