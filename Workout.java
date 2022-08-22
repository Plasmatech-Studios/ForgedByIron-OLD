import java.util.ArrayList;

public class Workout implements Config, Saveable {
    public UniqueID userID;
    public User user;
    public UniqueID workoutID;
    public ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    public ActivityState state;
    public boolean modified;

    public Workout(UniqueID userID) {
        this.userID = userID;
        this.user = UniqueID.getUserByID(this.userID);
        this.workoutID = new UniqueID(IDType.WORKOUT, user, this);
        this.state = ActivityState.NOT_STARTED;
        this.modified = false;
    }

    public Workout(User user) {
        this.userID = user.getID();
        this.user = user;
        this.workoutID = new UniqueID(IDType.WORKOUT, user, this);
        this.state = ActivityState.NOT_STARTED;
        this.modified = false;
    }

    public UniqueID getID() {
        return this.workoutID;
    }

    public Exercise addExercise() {
        if (this.state == ActivityState.COMPLETED) {
            // Send message to ensure user knows workout is completed - this will flag as modified
            this.modified = true;
            this.state = ActivityState.IN_PROGRESS;
        }
        if (this.state == ActivityState.NOT_STARTED) {
            this.state = ActivityState.IN_PROGRESS;
        }

        this.exercises.add(new Exercise(this));
        System.out.println("Exercise added");
        return this.exercises.get(this.exercises.size() - 1);
    }

    public void editExercise() {
        if (this.state == ActivityState.COMPLETED) {
            // Send message to ensure user knows workout is completed - this will flag as modified
            this.modified = true;
            this.state = ActivityState.IN_PROGRESS;
        }
        if (this.state == ActivityState.NOT_STARTED) {
            this.state = ActivityState.IN_PROGRESS;
            System.out.println("Exercise editted from NOT_STARTED workout");

        }
        // TODO Edit code here - may need to pass data
    }

    public void deleteExercise() {
        if (this.state == ActivityState.COMPLETED) {
            // Send message to ensure user knows workout is completed - this will flag as modified
            this.modified = true;
            this.state = ActivityState.IN_PROGRESS;
        }
        if (this.state == ActivityState.NOT_STARTED) { // Should not be in this state
            this.state = ActivityState.IN_PROGRESS;
            System.out.println("Exercise deleted from NOT_STARTED workout");
        }
        // TODO Edit code here - how to we handle this
    }

    public User getUser() {
        return UniqueID.getUserByID(this.userID);
    }

    @Override
    public void save() {
        if (User.mainUser == this.user) {
            for (Exercise exercise : this.exercises) {
                exercise.save();
            }
        }
        
    }

    public boolean completeWorkout() {
        if (this.state == ActivityState.IN_PROGRESS) {
            if (this.exercises.size() > 0) {
                for (Exercise exercise : this.exercises) {
                    if (exercise.state != ActivityState.COMPLETED) {
                        // remove exercise from workout
                        
                        deleteExercise(exercise); // deleting unfinished exercises before completing workout
                        // TODO prompt user first?
                        // iterate over the next exercise
                        if (this.exercises.size() > 0) {
                            continue;
                        } else {
                            this.state = ActivityState.COMPLETED;
                            return true;
                        }
                    }
                }
                this.state = ActivityState.COMPLETED;
                //this.modified = true;
                return true;
            } else {
                System.out.println("Error: Workout has no exercises");
                return false;
            }
        } else {
            System.out.println("Error: Workout cannot be completed");
            return false;
        }
    }

    public boolean deleteExercise(Exercise exercise) {
        if (this.state == ActivityState.COMPLETED) {
            // Send message to ensure user knows workout is completed - this will flag as modified
            this.modified = true;
            this.state = ActivityState.IN_PROGRESS;
        }
        if (this.state == ActivityState.NOT_STARTED) { // Should not be in this state
            this.state = ActivityState.IN_PROGRESS;
            System.out.println("Exercise deleted from NOT_STARTED workout");
        }
        // if exercise is in workout, remove it
        if (this.exercises.contains(exercise)) {
            this.exercises.remove(exercise);
            System.out.println("Exercise deleted");
            return true;
        } else {
            return false;
        }
    }

}