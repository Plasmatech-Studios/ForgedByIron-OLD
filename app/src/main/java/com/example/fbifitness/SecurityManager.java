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

    // Attempt to login a user. Returns null if login fails - usually incorrect password
     public static User login(String username, String password) throws NoSuchAlgorithmException {
         String secretKey = hashString(password); // Hash the password
         String tempID = "";

         // Try to find the user in the login table
         if (DataManager.findUsername(username) == false) { // If the user is not in the database
             Log.e("SecurityManager", "Error: Username not found in Login function" + username);
             User user = newUser(username, password); // Create a new user
             return user; // Return the new user
         } else { // If the user is in the database
             tempID = DataManager.findUserIDFromUsername(username); // Get the userID from the login table
         }

         if (secretKey.equals(DataManager.getAuthSecretKey(username, tempID))) { // If the password is correct
             System.out.println("Logged in as " + username); // Print to console (for debugging)
             User user = User.newUserFromLoad(tempID, username, secretKey); // Create a new user from the database
             return user; // Return the new user
         } else { // If the password is incorrect
             Log.d("SecurityManager", "Error: Incorrect password"); // Print to console (for debugging)
             return null; // Return null
         }
     }

     public static User newUser(String username, String password) throws NoSuchAlgorithmException {
         String secretKey = hashString(password);
         User user = User.newUser(username, secretKey);
         DataManager.saveNewUser(user.getUniqueID().toString(), user.getUsername(), user.getSecretKey());
         SessionController.currentUser = user;
         SessionController.isNewUser = true;
         return user;
     }
//
//
     public static String hashString(String password) throws NoSuchAlgorithmException {
         String SALT = "FBI_Fitness"; // Salt for the password
         password = password + SALT; // Add the salt to the password
         MessageDigest digest = MessageDigest.getInstance("SHA-384"); // Create a SHA-384 hash
         byte[] hashbytes = digest.digest(password.getBytes(StandardCharsets.UTF_8)); // Create a byte array of the hash
         String sha3Hex = bytesToHex(hashbytes); // Convert the byte array to a hex string
         Log.d("SecurityManager", "Hashed password: " + sha3Hex); // Print to console (for debugging)
         return sha3Hex; // Return the hashed password
     }

     private static String bytesToHex(byte[] hash) {
         StringBuilder hexString = new StringBuilder(2 * hash.length); // Create a new string builder
         for (int i = 0; i < hash.length; i++) { // For each byte in the hash
             String hex = Integer.toHexString(0xff & hash[i]); // Convert the byte to a hex string
             if(hex.length() == 1) { // If the hex string is only one character long
                 hexString.append('0'); // Add a 0 to the front
             }
             hexString.append(hex); // Add the hex string to the string builder
         }
         return hexString.toString(); // Return the string builder as a string
     }
}
