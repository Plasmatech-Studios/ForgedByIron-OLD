import java.sql.Date;

public class Summary implements Config, Saveable {

    private UniqueID summaryID;
    private UniqueID userID;
    private float weight;
    private float height;
    private Gender gender;
    private Age ageBrack;
    private float bodyFat;
    private float longestRun;
    private float benchPR;
    private float deadliftPR;
    private float squatPR;
    private Date dateUpdated;

    // Consider adding consecutive days to this summary

    public Summary(UniqueID userID) {
        this.userID = userID;
        this.summaryID = new UniqueID(IDType.SUMMARY, UniqueID.getUserByID(userID), this);
        this.dateUpdated = new Date(System.currentTimeMillis());
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub
        
    }

    //getters and setters
    public UniqueID getSummaryID() {
        return this.summaryID;
    }

    public UniqueID getUserID() {
        return this.userID;
    }

    public User getUser() {
        return UniqueID.getUserByID(this.userID);
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Age getAgeBrack() {
        return this.ageBrack;
    }

    public void setAgeBrack(Age ageBrack) {
        this.ageBrack = ageBrack;
    }

    public float getBodyFat() {
        return this.bodyFat;
    }

    public void setBodyFat(float bodyFat) {
        this.bodyFat = bodyFat;
    }

    public float getLongestRun() {
        return this.longestRun;
    }

    public void setLongestRun(float longestRun) {
        this.longestRun = longestRun;
    }

    public float getBenchPR() {
        return this.benchPR;
    }

    public void setBenchPR(float benchPR) {
        this.benchPR = benchPR;
    }

    public float getDeadliftPR() {
        return this.deadliftPR;
    }

    public void setDeadliftPR(float deadliftPR) {
        this.deadliftPR = deadliftPR;
    }

    public float getSquatPR() {
        return this.squatPR;
    }

    public void setSquatPR(float squatPR) {
        this.squatPR = squatPR;
    }

    public Date getDateUpdated() {
        return this.dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}