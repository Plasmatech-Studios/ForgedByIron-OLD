import java.util.ArrayList;

public class TestClass implements Config {

    public static ArrayList<Object> list = new ArrayList<>();
    public static void main(String[] args) {
        UniqueID id = new UniqueID();

        list.add(id);

        if (list.get(0).getClass() == UniqueID.class) {

            System.out.println("I am a: " + list.get(0).getClass());
        }
    }
    


}