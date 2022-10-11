package com.example.fbifitness;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Badge implements Config {

    private static ArrayList<Badge> badgeList = new ArrayList<>();
    private static HashMap<String, Badge> badgeMap = new HashMap<>();
    public static boolean isLoading = true;

    private String badgeName;
    private String badgeDescription;
    private boolean isUnlocked;
    private String badgeCode;


    public Badge(String badgeName, String badgeDescription, String badgeCode) {
        this.badgeName = badgeName;
        this.badgeDescription = badgeDescription;
        this.isUnlocked = false;
        this.badgeCode = badgeCode;
        badgeList.add(this);
        badgeMap.put(badgeCode, this);
    }

    public static void initBadges() {
        if (!badgeList.isEmpty()) {
            return;
        }

        new Badge("TeamFBI Competitor", "Sign in to the Forged By Iron App", "FBI");

        //Total Workout Badges
        new Badge("Beginner", "Complete your first workout", "W1");
        new Badge("Intermediate", "Complete 3 workouts", "W3");
        new Badge("Advanced", "Complete 5 workouts", "W5");
        new Badge("Expert", "Complete 10 workouts", "W10");
        new Badge("Master", "Bench 25 workouts", "W25");
        new Badge("Champion", "Complete 50 workouts", "W50");
        new Badge("Legend", "Complete 100 workouts", "W100");
        new Badge("God", "Complete 1000 workouts", "W1000");

        //Total Weight Lifted in a single workout Badges
        new Badge("Lightweight", "Lift 100 kgs in a single workout", "L100");
        new Badge("Middleweight", "Lift 500 kgs in a single workout", "L500");
        new Badge("Heavyweight", "Lift 1,000 kgs in a single workout", "L1000");
        new Badge("Super Heavyweight", "Lift 2,500 kgs in a single workout", "L2500");
        new Badge("Ultra Heavyweight", "Lift 5,000 kgs in a single workout", "L5000");
        new Badge("Titan", "Lift 10,000 kgs in a single workout", "L10000");
        new Badge("God of the Gym", "Lift 25,0000 kgs in a single workout", "L25000");
        new Badge("God of the Universe", "Lift 50,000 kgs in a single workout", "L50000");
        new Badge("God of the Multiverse", "Lift 100,000 kgs in a single workout", "L100000");

        //Number of followers a user has (Anchorman Puns) Badges
        new Badge("Who Are You?", "Have 1 follower", "F1");
        new Badge("I'm Kind of a Big Deal", "Have 10 followers", "F10");
        new Badge("People Know Me", "Have 25 followers", "F25");
        new Badge("I'm Very Important", "Have 50 followers", "F50");
        new Badge("I Have Many Leather Bound Books", "Have 100 followers", "F100");
        new Badge("My Gym Smells of Rich Mahogany", "Have 250 followers", "F250");
        new Badge("God Walking Amongst Mere Mortals", "Have 1000 followers", "F1000");

        //Bench Press Weight PR Badges
        new Badge("That's just the bar", "Bench 20 kgs", "B20");
        new Badge("You found the children's weights", "Bench 40 kgs", "B40");
        new Badge("That's my warmup set", "Bench 60 kgs", "B60");
        new Badge("I'm getting stronger", "Bench 80 kgs", "B80");
        new Badge("Can I get a spot?", "Bench 100 kgs", "B100");
        new Badge("Where are the safety bars?", "Bench 125 kgs", "B125");
        new Badge("This could kill me", "Bench 150 kgs", "B150");
        new Badge("Pass me the smelling salts", "Bench 200 kgs", "B200");
        new Badge("My eyes are bleeding", "Bench 250 kgs", "B250");

        //Squat Weight PR Badges
        new Badge("That's not a squat", "Squat 20 kgs", "S20");
        new Badge("The towel stops it hurting my back", "Squat 50 kgs", "S50");
        new Badge("Is it supposed to hurt this much?", "Squat 100 kgs", "S100");
        new Badge("Really? I'm supposed to do this?", "Squat 150 kgs", "S150");
        new Badge("Screaming makes it easier", "Squat 200 kgs", "S200");
        new Badge("Bro can you wrap me up?", "Squat 250 kgs", "S250");
        new Badge("I'm going to need a wheelchair", "Squat 300 kgs", "S300");

        //Deadlift Weight PR Badges
        new Badge("I'm not sure how to do this", "Deadlift 20 kgs", "D20");
        new Badge("You lift with your back right?", "Deadlift 50 kgs", "D50");
        new Badge("It's getting easier now", "Deadlift 100 kgs", "D100");
        new Badge("I lift sumo now", "Deadlift 150 kgs", "D150");
        new Badge("Sumo is for amateurs", "Deadlift 200 kgs", "D200");
        new Badge("Yo grab me my spare pair of underwear", "Deadlift 250 kgs", "D250");
        new Badge("I know it looked like a stroke but I'm fine", "Deadlift 300 kgs", "D300");
        new Badge("He wasn't fine, call an ambulance", "Deadlift 350 kgs", "D350");

        //Longest Run PR Badges
        new Badge("Let's be real, I wasn't running", "Run 1km", "R1");
        new Badge("American...", "Run 1 mile", "R1.6");
        new Badge("I barely broke a sweat", "Run 2.5 kms", "R2.5");
        new Badge("Athlete", "Run 5 km", "R5");
        new Badge("Spartan", "Run 10 km", "R10");
        new Badge("Half Marathon", "Run 21 km", "R21");
        new Badge("Marathon", "Run 42 km", "R42");
        new Badge("Ultra Marathon", "Run 50 km", "R50");
        new Badge("Forrest Gump", "Run 100 km", "R100");

    }

    public static Badge getBadge(String badgeCode) {
        return badgeMap.get(badgeCode);
    }

    public static ArrayList<Badge> getBadgeList() {
        return badgeList;
    }

    public static ArrayList<Badge> getUnlockedBadges() {
        ArrayList<Badge> unlockedBadges = new ArrayList<>();
        for (Badge badge : badgeList) {
            if (badge.isUnlocked) {
                unlockedBadges.add(badge);
            }
        }
        return unlockedBadges;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public String getBadgeDescription() {
        return badgeDescription;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    public String getBadgeCode() {
        return badgeCode;
    }

    public static void checkTotalWeightLifted(double weight) {
        Log.e("Badge", "Checking Total Weight Lifted: " + weight);
        if (weight >= 100) {
            unlockBadge("L100");
        }
        if (weight >= 500) {
            unlockBadge("L500");
        }
        if (weight >= 1000) {
            unlockBadge("L1000");
        }
        if (weight >= 2500) {
            unlockBadge("L2500");
        }
        if (weight >= 5000) {
            unlockBadge("L5000");
        }
        if (weight >= 10000) {
            unlockBadge("L10000");
        }
        if (weight >= 25000) {
            unlockBadge("L25000");
        }
        if (weight >= 50000) {
            unlockBadge("L50000");
        }
        if (weight >= 100000) {
            unlockBadge("L100000");
        }
    }

    public static void checkWorkoutTotal(int total) {
        Log.e("Badge", "Checking Workout Total: " + total);
        if (total >= 1) {
            unlockBadge("W1");
        }
        if (total >= 3) {
            unlockBadge("W3");
        }
        if (total >= 5) {
            unlockBadge("W5");
        }
        if (total >= 10) {
            unlockBadge("W10");
        }
        if (total >= 25) {
            unlockBadge("W25");
        }
        if (total >= 50) {
            unlockBadge("W50");
        }
        if (total >= 100) {
            unlockBadge("W100");
        }
        if (total >= 1000) {
            unlockBadge("W1000");
        }
    }

    public static boolean unlockBadge(String badgeCode) {

        Badge badge = getBadge(badgeCode);
        if (badge != null) {
            if (!badge.isUnlocked()) {
                badge.setUnlocked(true);
                if (!isLoading) {
                    Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "Unlocked " + badge.getBadgeName(), Toast.LENGTH_SHORT).show();
                    Log.e("Badge", "Badge Unlocked: " + badgeCode);
                    Log.e("Badge", "Badge Name: " + getBadge(badgeCode).getBadgeName());
                    Log.e("Badge", "Badge Description: " + getBadge(badgeCode).getBadgeDescription());
                }
                save();
                return true;
            }
        }
        return false;
    }

    public static void loadBadges(String userID) {
        ArrayList<String> badges = DataManager.getUserBadges(userID);
        if (badges != null) {
            for (String badgeCode : badges) {
                boolean unlocked = unlockBadge(badgeCode);
                if (unlocked) {
                    Log.e("Badge", "Badge Unlocked: " + badgeCode);
                    Log.e("Badge", "Badge Name: " + getBadge(badgeCode).getBadgeName());
                    Log.e("Badge", "Badge Description: " + getBadge(badgeCode).getBadgeDescription());
                } else {
                    Log.e("Badge", "Badge Already Unlocked: " + badgeCode);
                }
            }
        }
    }

    public static int getBadgeUnlockCount() {
        int count = 0;
        for (Badge badge : badgeList) {
            if (badge.isUnlocked()) {
                count++;
            }
        }
        return count;
    }



    public static void save() {
        DataManager.saveUserBadges(SessionController.currentUser.getUniqueID().toString());
    }

    public static void resetData() {
        isLoading = true;
        badgeList = new ArrayList<>();
        badgeMap = new HashMap<>();
    }

}