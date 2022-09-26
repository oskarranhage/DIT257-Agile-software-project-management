import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Learno {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        //new Learno().createAFlashCard();
        //new Learno().createASpellingCard();
        //new Learno().createAMultipleCard();

        //Set set1 = new FlashSet();              // create an empty FlashSet.
        //set1.readFile("./sets/set1.txt");// Read the designated file into the FlashSet
        //set1.run();                             // Run the set, calling the run() method in FlashSet

        while(true) {
            System.out.println("Choose action.");
            System.out.println("1. Create set");
            System.out.println("2. Play set");
            String input = sc.nextLine();
            System.out.println(input);
            if(input.equals("1")) {
                createSetMenu();
            }
            else if (input.equals("2")) {
                /*System.out.println("Type the name of the set you want to play.");
                String inputSetName = sc.nextLine();

                Set set1 = new FlashSet("");
                set1.readFile(inputSetName);
                set1.run();*/
                System.out.println("What set do you wan't to play?\n");
                System.out.println("Press 'f' for flashSet, 't' for textSet, 'm' for multipleChoiceSet");
                String whichSet = sc.nextLine();
                if (whichSet.equals("f")) {
                    SetFactory.createFlashSet();
                }
                else if (whichSet.equals("t")) {
                    SetFactory.createTextSet();
                }
                else if (whichSet.equals("m")) {
                    SetFactory.createMultipleChoiceSet();
                }
                else {
                    System.out.println(whichSet + " is not an available set.");
                }

                /*Set set2 = new TextSet("");
                set2.readFile(inputSetName);
                set2.run();

                //Behöver fixa så att alternativen syns
                Set set3 = new MultipleChoiceSet("");
                set3.readFile(inputSetName);
                set3.run();
                */
            }
        }
    }

    public static void createSetMenu() {
        Scanner sc = new Scanner(System.in);
        boolean temp = true;
        StringBuilder sb = new StringBuilder();
        System.out.println("What is the name of this set?");
        String name = sc.nextLine();
        Boolean success = makeFile(name);
        if(success){
            sb.append(name).append("\n");
            while(temp){
                System.out.println("What is the question? Enter it here: ");
                String userQuestion = sc.nextLine();
                sb.append(userQuestion).append(".");
                System.out.println("What is the answer? Enter it here: ");
                String userAnswer = sc.nextLine();
                sb.append(userAnswer).append("\n");
                System.out.println("Do you want to continue? (n)");
                String ans = sc.nextLine();
                if (ans.equals("n")) {
                    temp = false;
                }
            }
            writeFile(name,sb.toString());
        }
    }

    public static boolean makeFile(String name) {
        try{
            File mySet = new File ("C:\\OOP\\DIT257-Learno updated\\Sets\\" + name + ".txt");
            if (mySet.createNewFile()) {
                System.out.println("File created: " + mySet.getName());
                return true;
            } else {
                System.out.println("File already exists.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }
    public static void writeFile(String name, String content) {
        Path path = Path.of("C:\\OOP\\DIT257-Learno updated\\Sets\\" + name + ".txt");
        try {
            Files.writeString(path, content, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }
    }
}
