import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Learno {

    Scanner sc = new Scanner(System.in);
    Random rand = new Random();

    public static void main(String[] arg) {

        //new Learno().createAFlashCard();
        //new Learno().createASpellingCard();
        //new Learno().createAMultipleCard();

        //Set set1 = new FlashSet();              // create an empty FlashSet.
        //set1.readFile("./sets/set1.txt");// Read the designated file into the FlashSet
        //set1.run();                             // Run the set, calling the run() method in FlashSet

        //createSetMenu();
        Set set = new FlashSet();
        set.readFile("test2.txt");
        set.run();
    }

    public static void createSetMenu() {
        Scanner sc = new Scanner(System.in);
        Boolean temp = true;
        StringBuilder sb = new StringBuilder();
        System.out.println("What is the name of this set?");
        String name = sc.nextLine();
        sb.append(name + "\n");
        while(temp){
            System.out.println("What is the question? Enter it here: ");
            String userQuestion = sc.nextLine();
            sb.append(userQuestion + ".");
            System.out.println("What is the answer? Enter it here: ");
            String userAnswer = sc.nextLine();
            sb.append(userAnswer + "\n");
            System.out.println("Do you want to continue? (n)");
            String ans = sc.nextLine();
            if (ans.equals("n")) {
                temp = false;
            }
        }
        makeFile(name,sb.toString());
    }

    public static void makeFile(String name, String string) {
        try{
            File mySet = new File ("C:\\Users\\Alex\\Downloads\\DIT257-Agile-software-project-management-main\\DIT257-Agile-software-project-management-main\\Sets\\" + name + ".txt");
            if (mySet.createNewFile()) {
                System.out.println("File created: " + mySet.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Path path = Path.of("C:\\Users\\Alex\\Downloads\\DIT257-Agile-software-project-management-main\\DIT257-Agile-software-project-management-main\\Sets\\" + name + ".txt");
        try {
            Files.writeString(path, string, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }

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
        for (int i = 0; i < alternativeAnswers.length; i++) {
            System.out.println(alternativeAnswers[i]);
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
