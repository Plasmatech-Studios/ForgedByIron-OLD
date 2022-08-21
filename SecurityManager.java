import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class SecurityManager implements Config {

    private static HashMap<String, UniqueID> usernameMap = new HashMap<String, UniqueID>();
    private static HashMap<UniqueID, String> passwordMap = new HashMap<UniqueID, String>();
    public static void main(String args[])  throws NoSuchAlgorithmException{
        createUser("Peter", "password");
        System.out.println("Created user Peter with password password");
        User testLogin = login("Peter", "password");
        System.out.println("Logged in user Peter with password password");
        if (testLogin == null) {
            System.out.println("Error: Login failed");
        } else {
            System.out.println("Success: Login succeeded");
        }
        saveUserData();
    }

    public static User createUser(String username, String password) throws NoSuchAlgorithmException {
        if (usernameMap.containsKey(username)) {
            System.out.println("Error: Username already exists");
            return null;
        }
        String hashedPassword = hashString(password);
        User.mainUser = new User(username, hashedPassword);
        UniqueID userID = User.mainUser.userID;
        usernameMap.put(username, userID);
        passwordMap.put(userID, hashedPassword);
        return User.mainUser;
    }

    public static User login(String username, String password) throws NoSuchAlgorithmException {
        if (!usernameMap.containsKey(username)) {
            System.out.println("Error: Username does not exist");
            return null;
        }
        UniqueID userID = usernameMap.get(username);
        String hashedPassword = hashString(password);
        if (!hashedPassword.equals(passwordMap.get(userID))) {
            System.out.println("Error: Password is incorrect");
            return null;
        }
        User.mainUser = new User(userID);
        User.secretKey = hashedPassword;
        return User.mainUser;
    }


    public static String hashString(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        byte[] hashbytes = digest.digest(
          password.getBytes(StandardCharsets.UTF_8));
        String sha3Hex = bytesToHex(hashbytes);
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

    public static void logout() {
        User.mainUser = null;
        User.secretKey = null;
    }

    public static boolean isLoggedIn() {
        return User.mainUser != null;
    }

    public static void saveUserData() {
        
        File userData = new File("userData.txt");
        userData.delete();
        try {
            userData.createNewFile();
        } catch (Exception e) {
            System.out.println("Error: Could not create userData.txt");
        }
        try {
            FileWriter writer = new FileWriter(userData);
            writer.write(User.mainUser.userID.getUniqueID()+ "\n");
            writer.write((UniqueID.getUserFromMap(User.mainUser.userID)).username + "\n");
            writer.write(User.secretKey + "\n");
            // writer.write(User.mainUser.summary.toString() + "\n");
            // writer.write(User.mainUser.goal.toString() + "\n");
            // writer.write(User.mainUser.profilePicture + "\n");
            // writer.write(User.mainUser.workoutHistory.size() + "\n");
            // for (Workout workout : User.mainUser.workoutHistory) {
            //     writer.write(workout.toString() + "\n");
            // }
            // writer.write(User.mainUser.badges.size() + "\n");
            // for (Badge badge : User.mainUser.badges) {
            //     writer.write(badge.toString() + "\n");
            // }
            // writer.write(User.mainUser.followers.size() + "\n");
            // for (UniqueID follower : User.mainUser.followers) {
            //     writer.write(follower.toString() + "\n");
            // }
            // writer.write(User.mainUser.following.size() + "\n");
            // for (UniqueID following : User.mainUser.following) {
            //     writer.write(following.toString() + "\n");
            // }
            // writer.write(User.mainUser.summaryHistory.size() + "\n");
            // for (Summary summary : User.mainUser.summaryHistory) {
            //     writer.write(summary.toString() + "\n");
            // }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error: Could not write to userData.txt");
        }
    }
}
