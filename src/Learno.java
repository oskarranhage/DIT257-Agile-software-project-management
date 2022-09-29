import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Learno {
    public static Card card;
    public static DataBase db = new DataBase();
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    static String basePath = new File("").getAbsolutePath();
    private final static Path setFilePath = Path.of(basePath + "/Sets/");

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
                /*System.out.println("Type the name of the set you want to play.");
                String inputSetName = sc.nextLine();
                Set set = new FlashSet("");
                set.readFile(inputSetName);
                set.run();*/

                System.out.println("What set do you wan't to play?\n");
                //System.out.println("Press 'f' for flashSet, 't' for textSet, 'm' for multipleChoiceSet");
                //String whichSet = sc.nextLine();
                String inputSetName = sc.nextLine();
                //Set set = new FlashSet("");


                //run the requested set as a set full of flashCards.
                Set yo = db.getFlashSet(inputSetName);
                yo.run();


                //Path filepath = Path.of(db.getSetFolderFile() + "/" + inputSetName + ".txt");
                //db.readFile(filepath, set);
                //set.run();
                /*if (whichSet.equals("f")) {
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
                }*/
            }
            else if (input.equals("3")) {
                System.out.println("What set do you want to shuffle?\n");
                String input2 = sc.nextLine();
                //shuffle(db.getMultiSet(input2));
                shuffleFlashSet(db.getFlashSet(input2));
            }
        }
        //createSetMenu();

        //Set set1 = new FlashSet();              // create an empty FlashSet.
        //set1.readFile("./sets/set1.txt");// Read the designated file into the FlashSet
        //set1.run();                             // Run the set, calling the run() method in FlashSet
    }

    /*@Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("agilproj.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }*/

    /*public static void createSetMenu() {
        FileManager.writeFile();
    }*/


    public static void shuffleMultiChoiceSet(Set multiSet) {
        ArrayList<Card> multiCards = multiSet.cards;
        ArrayList<Card> tmp = multiSet.cards;
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
            System.out.println(pickedCard.getAnswer() + "\n");
        }
        System.out.println(multiCards);
        //flashSet.run();
    }


    public static void shuffleFlashSet(Set flashSet) {
        ArrayList<Card> flashCards = flashSet.cards;
        ArrayList<Card> tmp = flashSet.cards;
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
            System.out.println(pickedCard.getAnswer() + "\n");
        }
        System.out.println(flashCards);
        //flashSet.run();
    }

    public static void shuffleSpellingSet(Set spellingSet) {
        ArrayList<Card> spellingCards = spellingSet.cards;
        ArrayList<Card> tmp = spellingSet.cards;
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
            System.out.println(pickedCard.getAnswer() + "\n");
        }
        System.out.println(spellingCards);
        //flashSet.run();
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

        Card multiple = new Card(userQuestion, userAnswer, alternativeAnswers);


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
