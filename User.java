import java.util.ArrayList;

public class User implements Config{
    public UniqueID uniqueID;
    public String username;
    public char[] password;
    public Summary summary;
    public Goal goal;
    public String profilePicture;

    public ArrayList<Workout> workoutHistory = new ArrayList<Workout>();
    public ArrayList<Badge> badges = new ArrayList<Badge>();
    public ArrayList<UniqueID> followers = new ArrayList<UniqueID>();
    public ArrayList<UniqueID> following = new ArrayList<UniqueID>();
    public ArrayList<Summary> summaryHistory = new ArrayList<Summary>();

    public Workout currenWorkout;

    public User() { // default constructor - not for creating new users

    }

    public User(String username, char[] password) { // constructor for creating new users
        // TODO check if user exists before creating new user

        this.uniqueID = new UniqueID(IDType.USER, this);

        this.username = username;
        this.password = password;
        this.summary = new Summary();
        this.goal = new Goal();
        this.profilePicture = "";
    }

    public UniqueID createWorkout() {
        if (this.currenWorkout == null) {
            this.currenWorkout = new Workout(this.uniqueID);
        } else {
            System.out.println("Error: User already has a workout");
        }
        return this.currenWorkout.workoutID;
    }
}