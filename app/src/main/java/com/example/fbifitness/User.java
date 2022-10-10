package com.example.fbifitness;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class User extends UniqueID implements Config, Saveable {

    private String username;
    private String secretKey;
    private UniqueID activeWorkout;
    private Summary summary;

    public static HashMap<String, User> users = new HashMap<String, User>(); // UniqueID, User

    public static User newUser(String username, String secretKey) {
        User user = new User(username, secretKey);
        users.put(user.getUniqueID().toString(), user);
        DataManager.saveNewUser(user.getUniqueID().toString(), username, secretKey);
        return user;
    }

    public static User newUserFromLoad(String uniqueID, String username, String secretKey) {
        if (users.containsKey(uniqueID)) {
            return users.get(uniqueID);
        }
        String activeWorkout = DataManager.getActiveWorkoutID(uniqueID);
        User user = new User(uniqueID, username, secretKey, activeWorkout);
        users.put(user.getUniqueID().toString(), user);
        if (user.getActiveWorkout() != null) {
            Workout workout = Workout.newWorkoutFromLoad(activeWorkout);

        }
        return user;
    }
    private User(String username, String secretKey) { // Create a new user on the device
        super(IDType.USER);
        this.username = username;
        this.secretKey = secretKey;
        this.summary = new Summary(this.getUniqueID().toString());
    }

    private User(String uniqueID, String username, String secretKey, String activeWorkout) { // Create a new user from the Database
        super(uniqueID, IDType.USER);
        this.username = username;
        this.secretKey = secretKey;
        if (activeWorkout != null) {
            this.activeWorkout = new UniqueID(activeWorkout, IDType.WORKOUT);
        }
        this.summary = DataManager.getSummary(uniqueID);
    }

    public String getUsername() {
        return this.username;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setActiveWorkout(UniqueID workoutID) {
        this.activeWorkout = workoutID;
    }

    public UniqueID getActiveWorkout() {
        if (this.activeWorkout != null) {
            Log.d("User", "Active workout: " + this.activeWorkout.toString());
            return this.activeWorkout.getUniqueID();
        } else {
            return null;
        }
    }

    public UniqueID getUniqueID() {
        return super.getUniqueID();
    }

    public UniqueID startNewWorkout() {
        if (this.activeWorkout != null) {
            Log.d("User", "User already has an active workout");
            // End the current workout
            this.endWorkout();

            //return null;
        }
        Log.d("User", "Creating new workout");
        Workout workout = Workout.newWorkout(this.getUniqueID());
        this.setActiveWorkout(workout.getUniqueID());
        save();
        return workout.getUniqueID();
    }

    public void endWorkout() {
        if (this.activeWorkout == null) {
            System.out.println("No active workout to end");
            return;
        }
        for (String workoutID : Workout.workouts.keySet()) {
            if (workoutID.toString().equals(this.activeWorkout.toString())) {
                Workout.workouts.get(workoutID).completeWorkout();
                this.setActiveWorkout(null);
                Workout workout = Workout.workouts.get(workoutID);
                workout.save();
                save();
                Log.d("User", "Ending workout: " + workoutID);
                return;
            }
        }
        save();
    }

    public int getWorkoutCount() {
        int count = 0;
        count = DataManager.getWorkoutCount(this.getUniqueID().toString());
        return count;
    }

    public int getFollowersCount() { // TODO Later when online services are implemented
        int count = 0;
        //count = DataManager.getFollowersCount(this.getUniqueID().toString());
        return count;
    }

    public int getBadgeCount() { // TODO Later when online badges are implemented
        int count = 0;
        //count = DataManager.getBadgeCount(this.getUniqueID().toString());
        return count;
    }
    public String getDisplayName() {
        String displayName = summary.getDisplayName();
        if (displayName == null || displayName.equals("")) {
            return this.getUsername();
        } else {
            return displayName;
        }
    }

    public String getBio() { // TODO Later when login is implemented
        String bio = summary.getBio();
        return bio;
    }

    public String getUserWeight() { // TODO Later when login is implemented
        String weight = summary.getWeight();
        return weight;
    }

    public String getUserFat() { // TODO Later when login is implemented
        String fat = summary.getFat();
        return fat;
    }

    public String getLongestRun() { // TODO Later when login is implemented
        String longestRun = summary.getLongestRun();
        return longestRun;
    }

    public String getBenchPB() { // TODO Later when login is implemented
        String benchPB = summary.getBenchPR();
        return benchPB;
    }

    public String getDeadliftPB() { // TODO Later when login is implemented
        String deadliftPB = summary.getDeadliftPR();
        return deadliftPB;
    }

    public String getSquatPB() { // TODO Later when login is implemented
        String squatPB = summary.getSquatPR();
        return squatPB;
    }

    //setters

    public void setDisplayName(String displayName) {
        summary.setDisplayName(displayName);
    }

    public void setBio(String bio) {
        summary.setBio(bio);
    }

    public void setUserWeight(String weight) {
        summary.setWeight(weight);
    }

    public void setUserFat(String fat) {
        summary.setFat(fat);
    }

    public void setLongestRun(String longestRun) {
        summary.setLongestRun(longestRun);
    }

    public void setBenchPB(String benchPB) {
        summary.setBenchPR(benchPB);
    }

    public void setDeadliftPB(String deadliftPB) {
        summary.setDeadliftPR(deadliftPB);
    }

    public void setSquatPB(String squatPB) {
        summary.setSquatPR(squatPB);
    }





    @Override
    public void save() {
        if (this.activeWorkout != null) {
            DataManager.saveUser(this.getUniqueID().toString(), this.username, this.secretKey, this.activeWorkout.toString());
        } else {
            DataManager.saveUser(this.getUniqueID().toString(), this.username, this.secretKey, null);
        }
        // TODO Auto-generated method stub

    }








    // public static User mainUser; // Holds the signed in User.
    // public UniqueID userID;
    // public String username;
    // private String bio;
    // protected static String secretKey;
    // public UniqueID summary;
    // public UniqueID goal;
    // public String profilePicture;

    // public ArrayList<UniqueID> workoutHistory = new ArrayList<UniqueID>();
    // public ArrayList<Badge> badges = new ArrayList<Badge>();
    // public ArrayList<UniqueID> followers = new ArrayList<UniqueID>();
    // public ArrayList<UniqueID> following = new ArrayList<UniqueID>();
    // public ArrayList<UniqueID> summaryHistory = new ArrayList<UniqueID>();

    // public Workout currenWorkout;

    // // Constructor for creating a new user from the database
    // public User(UniqueID userID) { 
    //     this.userID = userID;
    //     System.out.println("I should only be called if loading from the DB");
    // }

    // public User(String username, String secretKey) { // constructor for creating new users - should only be called by SecurityManager
    //     this.userID = new UniqueID(IDType.USER, this);
    //     this.username = username;
    //     User.secretKey = secretKey;
    //     User.mainUser = this;
    //     System.out.println("New user created: " + username);
    // }

    // // TODO - DELETE THIS ONCE DB IMPLEMENTED, USE THE OTHER CONSTRUCTOR
    // public User(String username, UniqueID userID, String secretKey) { // constructor for login new users - should only be called by SecurityManager
    //     this.userID = userID;
    //     this.username = username;
    //     User.secretKey = secretKey;
    //     User.mainUser = this;
    //     System.out.println("New user created: " + username);
    // }

    // public void getUserValuesFromDB() { 
    //     // TODO get user values from the database
    // }

    // public Workout createWorkout() {
    //     if (this.currenWorkout == null) {
    //         this.currenWorkout = new Workout(this.userID);
    //         System.out.println("Workout created");
    //     } else {
    //         System.out.println("Error: User already has a workout");
    //     }
    //     return this.currenWorkout;
    // }

    // public void importWorkoutFromDB(UniqueID workoutID) {
    //     this.workoutHistory.add(workoutID);
    //     // this data needs to be added correctly
    // }

    // // public void importSummaryFromDB(Summary summary) {
    // //     this.summaryHistory.add(summary);
    // // }

    // // public void importBadgeFromDB(Badge badge) {
    // //     this.badges.add(badge);
    // // }

    // // public void importGoalFromDB(Goal goal) {
    // //     this.goal = goal;
    // // }

    // // public void importProfilePictureFromDB(String profilePicture) {
    // //     this.profilePicture = profilePicture;
    // // }

    // public void importFollowersFromDB(UniqueID follower) {
    //     this.followers.add(follower);
    // }

    // public void importFollowingFromDB(UniqueID following) {
    //     this.following.add(following);
    // }

    // // public void importWorkoutHistoryFromDB(ArrayList<Workout> workoutHistory) {
    // //     for (Workout workout : workoutHistory) {
    // //         this.workoutHistory.add(workout);
    // //     }
    // // }

    // public Workout importWorkoutToCurrent(Workout workout) {
    //     if (this.currenWorkout == null) {
    //         this.currenWorkout = workout;
    //     } else {
    //         System.out.println("Error: User already has a workout");
    //         // This could be added to a list of workouts to be imported later
    //     }
    //     return this.currenWorkout;
    // }

    // public UniqueID completeWorkout() {
    //     if (this.currenWorkout != null) {
    //         if (this.currenWorkout.completeWorkout()) {
    //             if (this.currenWorkout.exercises.size() > 0) {
    //                 this.workoutHistory.add(this.currenWorkout.workoutID);
    //                 this.currenWorkout = null;
    //                 System.out.println("Workout completed");
    //             } else {
    //                 System.out.println("Error: Completed Workout has no exercises");
    //                 this.currenWorkout = null;
    //             }
    //         } else {
    //             System.out.println("Error: Workout could not be completed");
    //             return null;
    //         }
    //     } else {
    //         System.out.println("Error: User does not have a workout");
    //         return null;
    //     }
    //     if (this.workoutHistory.size() > 0) {
    //         return this.workoutHistory.get(this.workoutHistory.size() - 1);
    //     }
    //     return null;
    // }

    // public UniqueID getID() {
    //     return this.userID;
    // }

    // public Workout getCurrentWorkout() {
    //     return this.currenWorkout;
    // }

    // @Override
    // public void save() {
    //     if (User.mainUser == this) {
    //         for (UniqueID workoutID : this.workoutHistory) {
    //             Workout workout = (Workout)UniqueID.getLinked(workoutID);
    //             workout.save();
    //         }
    //         // TODO need to save more here
    //     }
    // }

    // public String getBio() {
    //     return this.bio;
    // }

    // public void setBio(String bio) {
    //     this.bio = bio;
    // }

}