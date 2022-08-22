public class Set implements Config, Saveable {
    public UniqueID exerciseID;
    public Exercise exercise;
    public UniqueID setID; // not on class diagram
    public float weight;
    public int reps;
    public int setNumber; // not in class diagram
    public float timeSeconds;
    public SetType setType;
    public ActivityState state;

    public Set(Exercise exercise, SetType setType) {
        this.exercise = exercise;
        this.exerciseID = exercise.getID();
        this.setID = new UniqueID(IDType.SET, exercise.workout.getUser(), this);
        this.setType = setType;
        this.state = ActivityState.NOT_STARTED;
    }

    public Set(Exercise exercise, SetType type, float weightTime, int reps) {

        this.exercise = exercise;
        this.exerciseID = exercise.getID();
        this.setID = new UniqueID(IDType.SET, exercise.workout.getUser(), this);
        this.state = ActivityState.NOT_STARTED;
        this.reps = reps;
        this.setType = type;

        if (type == SetType.WEIGHT) {
            this.weight = weightTime;
            this.timeSeconds = 0;
        } else {
            this.timeSeconds = weightTime;
            this.weight = 0;
        }
    }


    @Override
    public void save() {
        if (User.mainUser == this.exercise.workout.getUser()) {
            // TODO - Save to database
        }
        
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setTime(float timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void setSetType(SetType setType) {
        this.setType = setType;
    }

    public void setState(ActivityState state) {
        this.state = state;
    }

    public void completeSet() {
        this.state = ActivityState.COMPLETED;
    }

    public void deleteSet() {
        this.exercise.deleteSet(this);
    }
}
