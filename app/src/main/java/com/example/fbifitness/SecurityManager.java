package com.example.fbifitness;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.HashMap;

public class SecurityManager implements Config {


     //Singleton
     private static SecurityManager instance = null;

     private SecurityManager() {
         // Exists only to defeat instantiation.
     }
     public static SecurityManager getInstance() {
         if(instance == null) {
             instance = new SecurityManager();
         }
         return instance;
     }

    // Attempt to login a user. Returns null if login fails
     public static User login(String username, String password) throws NoSuchAlgorithmException {
         String secretKey = hashString(password);
         String tempID = "";

         // Try to find the user in the login table
         if (DataManager.findUsername(username) == false) { // If the user is not in the database
             Log.e("SecurityManager", "Error: Username not found in Login function" + username);
             // TODO - Sign Up
             return null;
         } else {
             // Get the userID from the login table
             tempID = DataManager.findUserIDFromUsername(username);
         }

         if (secretKey.equals(DataManager.getAuthSecretKey(username, tempID))) {
             System.out.println("Logged in as " + username);
             User user = User.newUserFromLoad(username, tempID, secretKey);
             return user;
         } else {
             Log.d("SecurityManager", "Error: Incorrect password");
             return null;
         }
     }

     public static User newUser(String username, String password) throws NoSuchAlgorithmException {
         String secretKey = hashString(password);
         User user = User.newUser(username, secretKey);
         DataManager.saveNewUser(user.getUniqueID().toString(), user.getUsername(), user.getSecretKey());
         SessionController.currentUser = user;
         return user;
     }
//
//
     public static String hashString(String password) throws NoSuchAlgorithmException {

         // add salt
         String SALT = "FBI_Fitness";
         password = password + SALT;
         MessageDigest digest = MessageDigest.getInstance("SHA-384");
         byte[] hashbytes = digest.digest(
           password.getBytes(StandardCharsets.UTF_8));
         String sha3Hex = bytesToHex(hashbytes);
         Log.d("SecurityManager", "Hashed password: " + sha3Hex);
         return sha3Hex;
     }


     private static String bytesToHex(byte[] hash) {
         StringBuilder hexString = new StringBuilder(2 * hash.length);
         for (int i = 0; i < hash.length; i++) {
             String hex = Integer.toHexString(0xff & hash[i]);
             if(hex.length() == 1) {
                 hexString.append('0');
             }
             hexString.append(hex);
         }
         return hexString.toString();
     }
//
//     public static void logout() {
//         User.mainUser = null;
//         User.secretKey = null;
//     }
//
//     public static boolean isLoggedIn() {
//         return User.mainUser != null;
//     }
//
//     public static void saveUserData() {
//
//         File userData = new File("userData.txt");
//         userData.delete();
//         try {
//             userData.createNewFile();
//         } catch (Exception e) {
//             System.out.println("Error: Could not create userData.txt");
//         }
//         try {
//             FileWriter writer = new FileWriter(userData);
//             writer.write(User.mainUser.userID.getUniqueID()+ "\n");
//             writer.write((UniqueID.getUserFromMap(User.mainUser.userID)).username + "\n");
//             writer.write(User.secretKey + "\n");
//             writer.close();
//         } catch (Exception e) {
//             System.out.println("Error: Could not write to userData.txt");
//         }
//     }
}
