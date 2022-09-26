import java.util.ArrayList;
import java.util.Scanner;

public class SetFactory {

    String name;
    ArrayList<Card> cards = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);


    //Set textSet = new TextSet(name, cards);
    //Set multiSet = new MultipleChoiceSet(name, cards);


    public static void createFlashSet() {
        System.out.println("Type the name of the set you want to play.");
        String nameOfSet = sc.nextLine();
        Set flashSet = new FlashSet("");
        flashSet.readFile(nameOfSet);
        flashSet.run();
    }

    public static void createTextSet() {
        System.out.println("Type the name of the set you want to play.");
        String nameOfSet = sc.nextLine();
        Set textSet = new TextSet("");
        textSet.readFile(nameOfSet);
        textSet.run();
    }

    public static void createMultipleChoiceSet() {
        System.out.println("Type the name of the set you want to play.");
        String nameOfSet = sc.nextLine();
        Set multipleChoiceSet = new MultipleChoiceSet("");
        multipleChoiceSet.readFile(nameOfSet);
        multipleChoiceSet.run();
    }







    /*public Card[] readFile(String filePath) {
        Path filepath = Path.of(filePath); // "./sets/set1.txt"
        String content = "Empty";
        try {
            content = Files.readString(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(content);
        String[] lines = content.split("\r?\n|\r");
        System.out.println(Arrays.asList(lines));

        return null;
    }*/
}
