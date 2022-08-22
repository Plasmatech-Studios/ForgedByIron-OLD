import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UniqueID implements Config, Saveable {
    private String uniqueID;
    private IDType type;
    private User user;
    private Workout workout;
    private Exercise exercise;
    private Set set; // not in class diagram
    private Report report;
    private Badge badge;
    private static HashMap<String, UniqueID> allUniqueIDsByID = new HashMap<String, UniqueID>();
    private static HashMap<UniqueID, User> userMap = new HashMap<UniqueID, User>();
    private static HashMap<UniqueID, Object> linkedObjectMap = new HashMap<UniqueID, Object>();

    // todo how are we assigning these?
    public UniqueID(){
        System.out.println("Error: UniqueID constructor called without parameters");
    }

    // Used to load data from the database
    public UniqueID(String uniqueID, IDType type){
        this.uniqueID = uniqueID;
        this.type = type;
        if (allUniqueIDsByID.containsKey(uniqueID)){
            System.out.println("Error: UniqueID constructor called with duplicate uniqueID");
        } else {
            allUniqueIDsByID.put(uniqueID, this);
            // if (type == IDType.USER) { // this should be handled separately
            //     createLinkedUser(this);
            // } else {
            //     createLinkedObject(this); // can't do this until all loaded
            // }
        }
    }

    public UniqueID(IDType type, User user) {
        // TODO should this only be called for a new user?
        if (type == IDType.USER) {
            this.uniqueID = UUID.randomUUID().toString();
            userMap.put(this, user);
            this.type = type;
            this.user = user;
        } else {
            System.out.println("Error: Attempting to create a User UniqueID that is not of type user");
        }
    }

    public UniqueID(IDType type, User user, Object object) {
        // TODO should there always be a user?
        this.type = type; // Set type to the parameter type
        this.user = user; // Set user to the parameter user
        userMap.put(this, user); // Add this UniqueID to the user map
        this.generateUUID(); // Generate a UUID for this UniqueID
        this.assignObject(type, object);
        linkedObjectMap.put(this, object);
    }

    //getters
    public String getUniqueID() {
        return uniqueID;
    }

    public IDType getType() {
        return type;
    }

    public static User getUserFromMap(UniqueID uniqueID) {
        // check if the user exists in the map
        if (userMap.containsKey(uniqueID)) {
            return userMap.get(uniqueID);
        } else {
            System.out.println("Error: User does not exist in the map");
            return null;
        }
    }

    public static Object getObjectFromMap(UniqueID uniqueID) {
        // check if the object exists in the map
        if (linkedObjectMap.containsKey(uniqueID)) {
            return linkedObjectMap.get(uniqueID);
        } else {
            System.out.println("Error: Object does not exist in the map");
            return null;
        }
    }
    
    // Use getLinked() to get the linked object

    //setters
    public void setWorkout(Workout workout) {
        if (this.type == IDType.WORKOUT) {
            this.workout = workout;
        } else {
            System.out.println("Error: Attempting to assign a workout to a UniqueID that is not a workout");
        }
    }

    public void setExercise(Exercise exercise) {
        if (this.type == IDType.EXERCISE) {
            this.exercise = exercise;
        } else {
            System.out.println("Error: Attempting to assign an exercise to a UniqueID that is not an exercise");
        }
    }

    public void setSet(Set set) {
        if (this.type == IDType.SET) {
            this.set = set;
        } else {
            System.out.println("Error: Attempting to assign a set to a UniqueID that is not a set");
        }
    }

    public void setReport(Report report) {
        if (this.type == IDType.REPORT) {
            this.report = report;
        } else {
            System.out.println("Error: Attempting to assign a report to a UniqueID that is not a report");
        }
    }

    public void setBadge(Badge badge) {
        if (this.type == IDType.BADGE) {
            this.badge = badge;
        } else {
            System.out.println("Error: Attempting to assign a badge to a UniqueID that is not a badge");
        }
    }

    private void assignObject(IDType type, Object object) {
        if (type == IDType.WORKOUT) {
            this.workout = (Workout) object;
            return;
        }
        if (type == IDType.EXERCISE) {
            this.exercise = (Exercise) object;
            return;
        }
        if (type == IDType.SET) {
            this.set = (Set) object;
            return;
        }
        if (type == IDType.REPORT) {
            this.report = (Report) object;
            return;
        }
        if (type == IDType.BADGE) {
            this.badge = (Badge) object;
            return;
        }
        System.out.println("Error: Attempting to assign an object to a UniqueID of unknown type");

    }

    private void generateUUID() {
        if (this.uniqueID == null) {
            this.uniqueID = UUID.randomUUID().toString();
        } else {
            System.out.println("UniqueID already exists");
        }
    }

    public static User getUserByID(UniqueID uniqueID) {
        return uniqueID.user;
    }

    public static Object getLinked(UniqueID uniqueID) { // may need to change return type for processing
        if (uniqueID.type == IDType.USER) {
            return uniqueID.user;
        }
        if (uniqueID.type == IDType.WORKOUT) {
            return uniqueID.workout;
        }
        if (uniqueID.type == IDType.EXERCISE) {
            return uniqueID.exercise;
        }
        if (uniqueID.type == IDType.SET) {
            return uniqueID.set;
        }
        if (uniqueID.type == IDType.REPORT) {
            return uniqueID.report;
        }
        if (uniqueID.type == IDType.BADGE) {
            return uniqueID.badge;
        }

        return null;
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub
        
    }
}  