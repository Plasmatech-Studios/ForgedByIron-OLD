public class TestClass implements Config{
    public static void main(String[] args) {
        User testUser = new User("testUser", "testPassword".toCharArray());
        testUser.createWorkout();
        System.out.println(testUser.currenWorkout.state.toString());
        testUser.currenWorkout.addExercise();
    }
}
