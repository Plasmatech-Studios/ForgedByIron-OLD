package com.example.fbifitness;

import java.util.ArrayList;

public class User implements Config, Saveable {
    public static User mainUser; // Holds the signed in User.
    public UniqueID userID;
    public String username;
    private String bio;
    protected static String secretKey;
    public UniqueID summary;
    public UniqueID goal;
    public String profilePicture;

    public ArrayList<UniqueID> workoutHistory = new ArrayList<UniqueID>();
    public ArrayList<Badge> badges = new ArrayList<Badge>();
    public ArrayList<UniqueID> followers = new ArrayList<UniqueID>();
    public ArrayList<UniqueID> following = new ArrayList<UniqueID>();
    public ArrayList<UniqueID> summaryHistory = new ArrayList<UniqueID>();

    public Workout currenWorkout;

    // Constructor for creating a new user from the database
    public User(UniqueID userID) { 
        this.userID = userID;
        System.out.println("I should only be called if loading from the DB");
    }

    public User(String username, String secretKey) { // constructor for creating new users - should only be called by SecurityManager
        this.userID = new UniqueID(IDType.USER, this);
        this.username = username;
        User.secretKey = secretKey;
        User.mainUser = this;
        System.out.println("New user created: " + username);
    }

    // TODO - DELETE THIS ONCE DB IMPLEMENTED, USE THE OTHER CONSTRUCTOR
    public User(String username, UniqueID userID, String secretKey) { // constructor for login new users - should only be called by SecurityManager
        this.userID = userID;
        this.username = username;
        User.secretKey = secretKey;
        User.mainUser = this;
        System.out.println("New user created: " + username);
    }

    public void getUserValuesFromDB() { 
        // TODO get user values from the database
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

    public void importWorkoutFromDB(UniqueID workoutID) {
        this.workoutHistory.add(workoutID);
        // this data needs to be added correctly
    }

    // public void importSummaryFromDB(Summary summary) {
    //     this.summaryHistory.add(summary);
    // }

    // public void importBadgeFromDB(Badge badge) {
    //     this.badges.add(badge);
    // }

    // public void importGoalFromDB(Goal goal) {
    //     this.goal = goal;
    // }

    // public void importProfilePictureFromDB(String profilePicture) {
    //     this.profilePicture = profilePicture;
    // }

    public void importFollowersFromDB(UniqueID follower) {
        this.followers.add(follower);
    }

    public void importFollowingFromDB(UniqueID following) {
        this.following.add(following);
    }

    // public void importWorkoutHistoryFromDB(ArrayList<Workout> workoutHistory) {
    //     for (Workout workout : workoutHistory) {
    //         this.workoutHistory.add(workout);
    //     }
    // }

    public Workout importWorkoutToCurrent(Workout workout) {
        if (this.currenWorkout == null) {
            this.currenWorkout = workout;
        } else {
            System.out.println("Error: User already has a workout");
            // This could be added to a list of workouts to be imported later
        }
        return this.currenWorkout;
    }

    public UniqueID completeWorkout() {
        if (this.currenWorkout != null) {
            if (this.currenWorkout.completeWorkout()) {
                if (this.currenWorkout.exercises.size() > 0) {
                    this.workoutHistory.add(this.currenWorkout.workoutID);
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
            for (UniqueID workoutID : this.workoutHistory) {
                Workout workout = (Workout)UniqueID.getLinked(workoutID);
                workout.save();
            }
            // TODO need to save more here
        }
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}