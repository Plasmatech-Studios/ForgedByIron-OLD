import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.HashMap;

public class SecurityManager implements Config {

    private static HashMap<String, UniqueID> usernameMap = new HashMap<String, UniqueID>();
    private static HashMap<UniqueID, String> passwordMap = new HashMap<UniqueID, String>();
    public static void main(String args[])  throws NoSuchAlgorithmException {
        String username = "admin";
        String password = "password";
        createUser(username, password);
        System.out.println("Created user " + username + " with password " + password);
        User testLogin = login(username, (password));
        if (testLogin == null) {
            System.out.println("Error: Login failed");
            return;
        } else {
            System.out.println("Logged in as " + testLogin.username);

        }
        Date date = new Date(System.currentTimeMillis());
        testLogin.createWorkout();
        testLogin.getCurrentWorkout().addExercise();
        ((Exercise)(UniqueID.getLinked(testLogin.getCurrentWorkout().exercises.get(0)))).addSet(SetType.WEIGHT, 100.0f, 5);
        ((Exercise)(UniqueID.getLinked(testLogin.getCurrentWorkout().exercises.get(0)))).addSet(SetType.WEIGHT, 100.0f, 5);
        ((Exercise)(UniqueID.getLinked(testLogin.getCurrentWorkout().exercises.get(0)))).addSet(SetType.WEIGHT, 100.0f, 5);
        ((Exercise)(UniqueID.getLinked(testLogin.getCurrentWorkout().exercises.get(0)))).completeAllSets();
        testLogin.completeWorkout();
        Date date2 = new Date(System.currentTimeMillis());
        System.out.println("Workout started at " + date.getTime() + " and completed at " + date2.getTime());
        System.out.println("Workout duration: " + (date2.getTime() - date.getTime()) + " milliseconds");
        System.out.println("Workout duration: " + ((Workout)UniqueID.getLinked(testLogin.workoutHistory.get(0))).getWorkoutDurationMillis() + " milliseconds");
        //testLogin.workoutHistory.get(0).exercises.get(0).sets.get(0).setWeight(100.0f);
        //saveUserData();
    }

    public static User createUser(String username, String password) throws NoSuchAlgorithmException {
        if (usernameMap.containsKey(username)) {
            System.out.println("Error: Username already exists");
            return null;
        }
        String hashedPassword = hashString(password);
        //User user = new User(username, hashedPassword);
        //User.mainUser = user;
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
        User.mainUser = new User(username, userID, hashedPassword);  // TODO Delete this later, will need to load from the DB
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
            writer.close();
        } catch (Exception e) {
            System.out.println("Error: Could not write to userData.txt");
        }
    }
}
