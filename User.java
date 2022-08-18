import java.util.ArrayList;

public class User {
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
}