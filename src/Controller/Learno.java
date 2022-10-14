package Controller;
import Model.Card;
import Model.DataBase;
import Model.Set;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static javafx.application.Application.launch;

public class Learno extends Application {
    public static Card card;
    public static DataBase db = new DataBase();
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    static String basePath = new File("").getAbsolutePath();
    private final static Path setFilePath = Path.of(basePath + "/Sets/");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Controller/learno.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /*public static void createSetMenu() {
        FileManager.writeFile();
    }*/

    // Get a set with answers and returns a set with only the incorrect ones
    // Maybe temporary position in program
    public Set setFromIncorrectAnswers(Set currentSet, boolean[] answers) {
        ArrayList<Card> falseAnswersSet = new ArrayList<>();
        for (int i = 0; i < answers.length; i++) {
            if (!answers[i]) {
                falseAnswersSet.add(currentSet.getCards().get(i));
            }
        }

        return new Set("newSet", falseAnswersSet, currentSet.getThisSetType());
    }

    public static Set shuffleFlashSet(Set flashSet) {
        ArrayList<Card> flashCards = flashSet.getCards();
        ArrayList<Card> tmp = flashSet.getCards();
        int amountOfCards = tmp.size();
        int int_random;
        Card pickedCard;
        //for (Card card : tmp) {
        for (int i = 0; i < amountOfCards;) {
            int_random = rand.nextInt(amountOfCards);

            pickedCard = tmp.get(int_random);
            tmp.remove(pickedCard);
            amountOfCards--;

            System.out.println(int_random);
            System.out.println(pickedCard.getQuestion());
            System.out.println(pickedCard.getAnswers()[0] + "\n");
        }
        System.out.println(flashCards);
        //flashSet.run();
        return flashSet;
    }

    public static void shuffleMultiChoiceSet(Set multiSet) {
        ArrayList<Card> multiCards = multiSet.getCards();
        ArrayList<Card> tmp = multiSet.getCards();
        int amountOfCards = tmp.size();
        int int_random;
        Card pickedCard;
        //for (Card card : tmp) {
        for (int i = 0; i < amountOfCards;) {
            int_random = rand.nextInt(amountOfCards);

            pickedCard = tmp.get(int_random);
            String[] alt = pickedCard.getAnswers();
            for (int j = 0; j < alt.length; j++) {
                String[] newAlt = new String[alt.length];
                String pickedAlternative = alt[int_random];
                newAlt[j] = pickedAlternative;
            }

            tmp.remove(pickedCard);
            amountOfCards--;

            System.out.println(int_random);
            System.out.println(pickedCard.getQuestion());
            System.out.println(pickedCard.getAnswers()[0] + "\n");
        }
        System.out.println(multiCards);
        //multiSet.run();
    }

    public static void shuffleSpellingSet(Set spellingSet) {
        ArrayList<Card> spellingCards = spellingSet.getCards();
        ArrayList<Card> tmp = spellingSet.getCards();
        int amountOfCards = tmp.size();
        int int_random;
        Card pickedCard;
        //for (Card card : tmp) {
        for (int i = 0; i < amountOfCards;) {
            int_random = rand.nextInt(amountOfCards);

            pickedCard = tmp.get(int_random);
            tmp.remove(pickedCard);
            amountOfCards--;

            System.out.println(int_random);
            System.out.println(pickedCard.getQuestion());
            System.out.println(pickedCard.getAnswers()[0] + "\n");
        }
        System.out.println(spellingCards);
        //spellingSet.run();
    }


    /*Method for creating new set, the user inserts the name for the new set.
    * Then, if the method makeFile successfully creates a new file for the new set,
    * it will write in the sets name onto the first row, then the users question and answer
    * for card nr 1 onto the second row and the users question and answer for card nr 2 onto
    * the third row and so on.*/
    public static void createSetMenu() {
        Scanner sc = new Scanner(System.in);
        boolean temp = true;
        StringBuilder sb = new StringBuilder();
        System.out.println("What is the name of this set?");
        String name = sc.nextLine();
        boolean success = makeFile(name);
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

    //Creates a new file. Has a safegaurd, cannot create a file that already exists.
    public static boolean makeFile(String name) {
        try{
            File mySet = new File (db.getSetFolderPath() + "/fc." + name + ".txt");
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

    //Writes the content onto the file located in that path.
    public static void writeFile(String name, String content) {
        //Path path = Path.of("C:\\OOP\\DIT257-Learno updated\\Sets\\" + name + ".txt");
        Path path = Path.of(db.getSetFolderFile() + "/fc." + name + ".txt");
        try {
            Files.writeString(path, content, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }
    }

    /*Creates a flashCard.
      The user types in the cards question and answer,
      which are used to create the card.*/
    public void createAFlashCard () {

        System.out.println("What is the question? Enter it here: ");
        String userQuestion = sc.nextLine();
        System.out.println("What is the answer? Enter it here: ");
        String userAnswer = sc.nextLine();

        Card flash = new Card(userQuestion, userAnswer);
    }


    /*Creates a spellingCard.
      The user types in the cards question and answer,
      which are used to create the card.*/
    public void createASpellingCard () {
        System.out.println("What is the question? Enter it here: ");
        String userQuestion = sc.nextLine();
        System.out.println("What is the answer? Enter it here: ");
        String userAnswer = sc.nextLine();

        Card spelling = new Card(userQuestion, userAnswer);

        /*System.out.println(spelling.getQuestion());
        System.out.println("Enter the answer: ");
        String userGuess = sc.nextLine();
        System.out.println(userGuess);
        System.out.println(spelling.getAnswer());

        if (userAnswer.equals(spelling.getAnswer())) {

            System.out.println("That is correct! \n The answer is: " + spelling.getAnswer());
        }
        else {
            System.out.println("Unfortunately that's wrong. \n The answer is: " + spelling.getAnswer());
        }*/


    }

    /*Creates a multiChoiceCard.
      The user types in the cards question and answer plus the alternative answers,
      which are used to create the card.*/
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

        //Card multiple = new Card(userQuestion, userAnswer, alternativeAnswers);


        /*System.out.println(multiple.getQuestion() + "\n");

        int amountOfAlternatives = alternativeAnswers.length;
        int int_random = rand.nextInt(amountOfAlternatives);
        for (String alternativeAnswer : alternativeAnswers) {
            System.out.println(alternativeAnswer);
        }*/
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

        /*System.out.println("");
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
        }*/
    }


}
