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
    }

    public Workout createWorkout() {
        if (this.currenWorkout == null) {
            this.currenWorkout = new Workout(this.userID);
        } else {
            System.out.println("Error: User already has a workout");
        }
        return this.currenWorkout;
    }

    public UniqueID getID() {
        return this.userID;
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