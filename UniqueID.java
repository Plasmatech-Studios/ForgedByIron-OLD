public class UniqueID implements Config {
    public String uniqueID;
    public IDType type;
    public User user;
    public Workout workout;
    public Exercise exercise;
    public Set set; // not in class diagram
    public Report report;
    public Badge badge;

    public UniqueID(IDType type) {
        // TODO
    }

    public static User getUser(UniqueID uniqueID) {
        return uniqueID.user;
    }

    public static Object getLinked(UniqueID uniqueID) { // may need to change return type for processing
        if (uniqueID.type == IDType.USER) {
            return uniqueID.user;
        } else if (uniqueID.type == IDType.WORKOUT) {
            return uniqueID.workout;
        } else if (uniqueID.type == IDType.EXERCISE) {
            return uniqueID.exercise;
        } else if (uniqueID.type == IDType.SET) {
            return uniqueID.set;
        } else if (uniqueID.type == IDType.REPORT) {
            return uniqueID.report;
        } else if (uniqueID.type == IDType.BADGE) {
            return uniqueID.badge;
        } else {
            return null;
        }
    }



}
