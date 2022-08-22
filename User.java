import java.util.ArrayList;

public class User implements Config, Saveable {
    public static User mainUser; // Holds the signed in User.
    public UniqueID userID;
    public String username;
    protected static String secretKey;
    public Summary summary;
    public Goal goal;
    public String profilePicture;

    public ArrayList<Workout> workoutHistory = new ArrayList<Workout>();
    public ArrayList<Badge> badges = new ArrayList<Badge>();
    public ArrayList<UniqueID> followers = new ArrayList<UniqueID>();
    public ArrayList<UniqueID> following = new ArrayList<UniqueID>();
    public ArrayList<Summary> summaryHistory = new ArrayList<Summary>();

    public Workout currenWorkout;

    public User(UniqueID userID) { // Constructor of a User object, not creating a new user in the System
        this.userID = userID;
        System.out.println("I should not be here");
    }

    /**
     * Constructor for creating a new user
     * 
     * @param username
     * @param password
     */
    public User(String username, String secretKey) { // constructor for creating new users - should only be called by SecurityManager
        this.userID = new UniqueID(IDType.USER, this);
        this.username = username;
        User.secretKey = secretKey;
        User.mainUser = this;
        System.out.println("New user created: " + username);
    }

    public User(String username, UniqueID userID, String secretKey) { // constructor for login new users - should only be called by SecurityManager
        this.userID = userID;
        this.username = username;
        User.secretKey = secretKey;
        User.mainUser = this;
        System.out.println("New user created: " + username);
    }

    public Workout createWorkout() {
        if (this.currenWorkout == null) {
            this.currenWorkout = new Workout(this.userID);
            System.out.println("Workout created");
        } else {
            System.out.println("Error: User already has a workout");
        }
        return this.currenWorkout;
    }

    public Workout importWorkout(Workout workout) {
        if (this.currenWorkout == null) {
            this.currenWorkout = workout;
        } else {
            System.out.println("Error: User already has a workout");
            // This could be added to a list of workouts to be imported later
        }
        return this.currenWorkout;
    }

    public Workout completeWorkout() {
        if (this.currenWorkout != null) {
            if (this.currenWorkout.completeWorkout()) {
                if (this.currenWorkout.exercises.size() > 0) {
                    this.workoutHistory.add(this.currenWorkout);
                    this.currenWorkout = null;
                    System.out.println("Workout completed");
                } else {
                    System.out.println("Error: Completed Workout has no exercises");
                    this.currenWorkout = null;
                }
            } else {
                System.out.println("Error: Workout could not be completed");
                return null;
            }
        } else {
            System.out.println("Error: User does not have a workout");
            return null;
        }
        if (this.workoutHistory.size() > 0) {
            return this.workoutHistory.get(this.workoutHistory.size() - 1);
        }
        return null;
    }

    public UniqueID getID() {
        return this.userID;
    }

    public Workout getCurrentWorkout() {
        return this.currenWorkout;
    }

    @Override
    public void save() {
        if (User.mainUser == this) {
            for (Workout workout : this.workoutHistory) {
                workout.save();
            }
        }
    }
}