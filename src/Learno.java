import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class Learno extends Application {


    static String basePath = new File("").getAbsolutePath();
    private final static Path setFilePath = Path.of(basePath + "/Sets/");
    static Scanner sc = new Scanner(System.in);
    Random rand = new Random();

    public static void main(String[] args) {
        //launch(args);

        //new Learno().createAFlashCard();
        //new Learno().createASpellingCard();
        //new Learno().createAMultipleCard();

        while (true) {
            System.out.println("Choose action.");
            System.out.println("1. Create set");
            System.out.println("2. Play set");
            String input = sc.nextLine();
            System.out.println(input);
            if (input.equals("1")) {
                createSetMenu();
            } else if (input.equals("2")) {
                System.out.println("Type the name of the set you want to play.");
                String inputSetName = sc.nextLine();
                Set set = new FlashSet("");
                set.readFile(inputSetName);
                set.run();
            }
        }
        //createSetMenu();

        //Set set1 = new FlashSet();              // create an empty FlashSet.
        //set1.readFile("./sets/set1.txt");// Read the designated file into the FlashSet
        //set1.run();                             // Run the set, calling the run() method in FlashSet
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("agilproj.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void createSetMenu() {
        FileManager.writeFile();
    }

    public void createAFlashCard () {

        System.out.println("What is the question? Enter it here: ");
        String userQuestion = sc.nextLine();
        System.out.println("What is the answer? Enter it here: ");
        String userAnswer = sc.nextLine();

        Card flash = new Card(userQuestion, userAnswer);

        //Behövs ej
        //System.out.println(flash.getQuestion() + "\n");
        //System.out.println(flash.getAnswer());
    }


    public void createASpellingCard () {
        System.out.println("What is the question? Enter it here: ");
        String userQuestion = sc.nextLine();
        System.out.println("What is the answer? Enter it here: ");
        String userAnswer = sc.nextLine();

        Card spelling = new Card(userQuestion, userAnswer);

        System.out.println(spelling.getQuestion());
        System.out.println("Enter the answer: ");
        String userGuess = sc.nextLine();
        System.out.println(userGuess);
        System.out.println(spelling.getAnswer());

        if (userAnswer.equals(spelling.getAnswer())) {

            System.out.println("That is correct! \n The answer is: " + spelling.getAnswer());
        }
        else {
            System.out.println("Unfortunately that's wrong. \n The answer is: " + spelling.getAnswer());
        }


    }

    public void createAMultipleCard () {
        System.out.println("What is the question? Enter it here: ");
        String userQuestion = sc.nextLine();
        System.out.println("What is the answer? Enter it here: ");
        String userAnswer = sc.nextLine();

        /*Frågar efter alternativa svar till din fråga som lagras i
        string arrayen alternativeAnswers, separerar varje alternativ
        med '¤' tecknet.*/
        System.out.println("Enter alternatives with a '¤' between: \n");
        String userAlternatives = sc.nextLine();
        String[] alternativeAnswers = userAlternatives.split("¤");

        MultipleChoiceCard multiple = new MultipleChoiceCard(userQuestion, alternativeAnswers, userAnswer);

        System.out.println(multiple.getQuestion() + "\n");

        int amountOfAlternatives = alternativeAnswers.length;
        int int_random = rand.nextInt(amountOfAlternatives);
        for (String alternativeAnswer : alternativeAnswers) {
            System.out.println(alternativeAnswer);
        }
        //Tried to randomize the order of printing out alternatives
        /*int amountOfAlternatives = alternativeAnswers.length;
        //int[] alreadyShown = new int[alternativeAnswers.length];
        ArrayList<Integer> alreadyShown = new ArrayList<Integer>(alternativeAnswers.length);
        for (int i = 0; i < alternativeAnswers.length; i++) {
            int randomElement = rand.nextInt(amountOfAlternatives);
            if (!alternativeAnswers[randomElement].contains((CharSequence) alreadyShown)) {
                System.out.println(alternativeAnswers[randomElement]);
                //add element to ArrayList
                alreadyShown.add(Integer.valueOf(alternativeAnswers[randomElement]));
            }
            else {
                i--;
            }
        }*/

        System.out.println("");
        //convert into arrayList
        ArrayList<String> listOfAnswers = new ArrayList<String>(Arrays.asList(alternativeAnswers));
        if (listOfAnswers.contains(userAnswer)) {
            System.out.println("Enter your answer: ");
            String guess = sc.nextLine();

            if (guess.equals(multiple.getAnswer())) {

                System.out.println("That is correct! \n The answer is: " + multiple.getAnswer());
            }
            else {
                System.out.println("Unfortunately that's wrong. \n The answer is: " + multiple.getAnswer());
            }
        }
        else {
            System.out.println("Your answer isn't included in the alternative answers!");
        }
    }


}
