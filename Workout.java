import java.util.ArrayList;
import java.sql.Date;

public class Workout implements Config, Saveable {
    public UniqueID userID;
    private User user; // Use getUser()
    public UniqueID workoutID;
    public ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    public ActivityState state;
    public boolean modified;
    public Date timeStarted;
    public Date timeCompleted;
    public Date totalTime;

    public Workout(UniqueID userID) {
        this.timeStarted = new Date(System.currentTimeMillis());
        this.userID = userID;
        this.user = UniqueID.getUserByID(this.userID);
        this.workoutID = new UniqueID(IDType.WORKOUT, user, this);
        this.state = ActivityState.NOT_STARTED;
        this.modified = false;
    }

    public Workout(User user) {
        this.timeStarted = new Date(System.currentTimeMillis());
        this.userID = user.getID();
        this.user = user;
        this.workoutID = new UniqueID(IDType.WORKOUT, user, this);
        this.state = ActivityState.NOT_STARTED;
        this.modified = false;
    }

    // Constructor for loading in data
    public Workout(UniqueID userID, UniqueID workoutID) {
        this.workoutID = workoutID;
        this.userID = userID;
        this.user = UniqueID.getUserByID(userID);
        this.user.importWorkoutFromDB(this);
        this.getWorkoutValuesFromDB();
        // TODO - Some function to put in a queue to populate
    }

    public void getWorkoutValuesFromDB() {
        // TODO - Get values from DB
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
        if (this.user == null) {
            this.user = UniqueID.getUserByID(this.userID);
        }
        return this.user;
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
                        // if all sets in the exercise are completed, complete the exercise
                        if (exercise.hasOutstandingSets()) {
                            deleteExercise(exercise); // deleting unfinished exercises before completing workout
                        } else {
                            exercise.completeExercise();
                        }

                        if (this.exercises.size() > 0) {
                            continue; // Check needed incase last exercise was removed
                        } else {
                            this.state = ActivityState.COMPLETED;
                            this.timeCompleted = new Date(System.currentTimeMillis());
                            this.totalTime = new Date(this.timeCompleted.getTime() - this.timeStarted.getTime());
                            return true;
                        }
                    }
                }
                this.state = ActivityState.COMPLETED;
                this.timeCompleted = new Date(System.currentTimeMillis());
                this.totalTime = new Date(this.timeCompleted.getTime() - this.timeStarted.getTime());
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

    public long getWorkoutDurationMillis() {
        return this.totalTime.getTime();
    }

    public long getWorkoutDurationSeconds() {
        return this.totalTime.getTime() / 1000;
    }

    public long getWorkoutDurationMinutes() {
        return this.totalTime.getTime() / 1000 / 60;
    }

    public void importExerciseFromDB(Exercise exercise) {
        this.exercises.add(exercise);
    }

}