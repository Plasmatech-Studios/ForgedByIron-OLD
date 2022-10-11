package com.example.fbifitness;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class User extends UniqueID implements Config, Saveable {

    private String username;
    private String secretKey;
    private UniqueID activeWorkout;
    private Summary summary;
    private Bitmap profileImage = null;

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
        Bitmap profileImage = DataManager.getProfileImage(uniqueID);
        if (profileImage != null) {
            user.setProfileImage(profileImage);
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
        this.profileImage = DataManager.getProfileImage(uniqueID);
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
            if (workoutID.equals(this.activeWorkout.toString())) {
                Workout workout = Workout.workouts.get(workoutID);
                workout.completeWorkout();
                this.setActiveWorkout(null);
                workout.save();
                save();
                //calculate the total weight lifted.
                double totalWeightLifted = 0;
                Report report = new Report(workout.getUniqueID().toString());
                totalWeightLifted = report.getWorkoutWeightTotals();
                Badge.checkTotalWeightLifted(totalWeightLifted);

                Badge.checkWorkoutTotal(this.getWorkoutCount());

                Log.d("User", "Ending workout: " + workoutID);
                return;
            }
        }
        save();
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
        save();
    }

    public Bitmap getProfileImage() {
        return this.profileImage;
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

    public String getBio() {
        String bio = summary.getBio();
        return bio;
    }

    public String getUserWeight() {
        String weight = summary.getWeight();
        return weight;
    }

    public String getUserFat() {
        String fat = summary.getFat();
        return fat;
    }

    public String getLongestRun() {
        String longestRun = summary.getLongestRun();
        return longestRun;
    }

    public String getBenchPB() {
        String benchPB = summary.getBenchPR();
        return benchPB;
    }

    public String getDeadliftPB() {
        String deadliftPB = summary.getDeadliftPR();
        return deadliftPB;
    }

    public String getSquatPB() {
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

    public void setBenchPB(String benchPB) {
        summary.setBenchPR(benchPB);
        //remove the last 2 characters
        benchPB = benchPB.substring(0, benchPB.length() - 2);
        Double bench;
        try {
            bench = Double.parseDouble(benchPB);
            if (bench >= 20) {
                Badge.unlockBadge("B20");
            }
            if (bench >= 40) {
                Badge.unlockBadge("B40");
            }
            if (bench >= 60) {
                Badge.unlockBadge("B60");
            }
            if (bench >= 80) {
                Badge.unlockBadge("B80");
            }
            if (bench >= 100) {
                Badge.unlockBadge("B100");
            }
            if (bench >= 125) {
                Badge.unlockBadge("B125");
            }
            if (bench >= 150) {
                Badge.unlockBadge("B150");
            }
            if (bench >= 200) {
                Badge.unlockBadge("B200");
            }
            if (bench >= 250) {
                Badge.unlockBadge("B250");
            }
        } catch (NumberFormatException e) {
            Log.e("User", "Bench PR is not a number" + benchPB);
            return;
        }
    }

    public void setDeadliftPB(String deadliftPB) {
        summary.setDeadliftPR(deadliftPB);
        //remove the last 2 characters
        deadliftPB = deadliftPB.substring(0, deadliftPB.length() - 2);
        Double deadlift;

        try {
            deadlift = Double.parseDouble(deadliftPB);
            if (deadlift >= 20) {
                Badge.unlockBadge("D20");
            }
            if (deadlift >= 50) {
                Badge.unlockBadge("D50");
            }
            if (deadlift >= 100) {
                Badge.unlockBadge("D100");
            }
            if (deadlift >= 150) {
                Badge.unlockBadge("D150");
            }
            if (deadlift >= 200) {
                Badge.unlockBadge("D200");
            }
            if (deadlift >= 250) {
                Badge.unlockBadge("D250");
            }
            if (deadlift >= 300) {
                Badge.unlockBadge("D300");
            }
            if (deadlift >= 350) {
                Badge.unlockBadge("D350");
            }
        } catch (NumberFormatException e) {
            Log.e("User", "Deadlift PR is not a number" + deadliftPB);
            return;
        }
    }

    public void setSquatPB(String squatPB) {
        summary.setSquatPR(squatPB);
        //remove the last 2 characters
        squatPB = squatPB.substring(0, squatPB.length() - 2);
        Double squat;
        try {
            squat = Double.parseDouble(squatPB);
            if (squat >= 20) {
                Badge.unlockBadge("S20");
            }
            if (squat >= 50) {
                Badge.unlockBadge("S50");
            }
            if (squat >= 100) {
                Badge.unlockBadge("S100");
            }
            if (squat >= 150) {
                Badge.unlockBadge("S150");
            }
            if (squat >= 200) {
                Badge.unlockBadge("S200");
            }
            if (squat >= 250) {
                Badge.unlockBadge("S250");
            }
            if (squat >= 300) {
                Badge.unlockBadge("S300");
            }
        } catch (NumberFormatException e) {
            Log.e("User", "Squat PR is not a number" + squatPB);
            return;
        }
    }

    public void setLongestRun(String longestRun) {
        summary.setLongestRun(longestRun);
        // remove the last 2 characters
        longestRun = longestRun.substring(0, longestRun.length() - 2);
        Log.e("User", "Longest run is " + longestRun);

        Double run;
        try {
            run = Double.parseDouble(longestRun);
            if (run >= 1) {
                Badge.unlockBadge("R1");
            }
            if (run >= 1.6) {
                Badge.unlockBadge("R1.6");
            }
            if (run >= 2.5) {
                Badge.unlockBadge("R2.5");
            }
            if (run >= 5) {
                Badge.unlockBadge("R5");
            }
            if (run >= 10) {
                Badge.unlockBadge("R10");
            }
            if (run >= 21) {
                Badge.unlockBadge("R21");
            }
            if (run >= 42) {
                Badge.unlockBadge("R42");
            }
            if (run >= 50) {
                Badge.unlockBadge("R50");
            }
            if (run >= 100) {
                Badge.unlockBadge("R100");
            }
        } catch (NumberFormatException e) {
            Log.e("User", "Longest run is not a number" + longestRun);
            return;
        }
    }





    @Override
    public void save() {
        if (this.activeWorkout != null) {
            if (this.profileImage != null) {
                DataManager.saveUser(this.getUniqueID().toString(), this.username, this.secretKey, this.activeWorkout.toString(), this.profileImage);
            } else {
                DataManager.saveUser(this.getUniqueID().toString(), this.username, this.secretKey, this.activeWorkout.toString(), null);
            }
        } else {
            if (this.profileImage != null) {
                DataManager.saveUser(this.getUniqueID().toString(), this.username, this.secretKey, null, this.profileImage);
            } else {
                DataManager.saveUser(this.getUniqueID().toString(), this.username, this.secretKey, null, null);
            }
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