import java.util.UUID;

public class UniqueID implements Config {
    private String uniqueID;
    private IDType type;
    private User user;
    private Workout workout;
    private Exercise exercise;
    private Set set; // not in class diagram
    private Report report;
    private Badge badge;

    // todo how are we assigning these?
    
    //getters
    public String getUniqueID() {
        return uniqueID;
    }

    public IDType getType() {
        return type;
    }

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
    public UniqueID(){
        
    }

    public UniqueID(IDType type, User user) {
        // TODO should there always be a user?
        this.type = type; // Set type to the parameter type
        this.user = user; // Set user to the parameter user
        this.generateUUID(); // Generate a UUID for this UniqueID   
    }

    public UniqueID(IDType type, User user, Object object) {
        // TODO should there always be a user?
        this.type = type; // Set type to the parameter type
        this.user = user; // Set user to the parameter user
        this.generateUUID(); // Generate a UUID for this UniqueID   
    }
    private void generateUUID() {
        if (this.uniqueID == null) {
            this.uniqueID = UUID.randomUUID().toString();
        } else {
            System.out.println("UniqueID already exists");
        }
    }

    private void newUser() {
         // TODO
    }

    private void newWorkout() {
        // TODO
    }

    private void newExercise() {
        // TODO
    }

    private void newSet() {
        // TODO
    }

    private void newReport() {
        // TODO
    }

    private void newBadge() {
        // TODO
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
}  