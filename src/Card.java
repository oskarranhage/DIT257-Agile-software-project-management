import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Card {
    private String question;
    private String answer;
    private String[] alternatives;

    static Scanner sc = new Scanner(System.in);
    Random rand = new Random();

    /**
     *
     * @param question
     * @param answer
     */
    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Added another constructor with different parameters than the one above,
     * to make it suitable for the multipleChoiceCard.
     * It contains an array of Strings (the alternative answers),
     * in addition to a question and an answer.
     *
     * @param question
     * @param answer
     * @param alternatives
     */
    public Card(String question, String answer, String[] alternatives) {
        this.question = question;
        this.answer = answer;
        this.alternatives = alternatives;
    }

    //Getters
    String getQuestion() {
        return question;
    }

    String getAnswer() {
        return answer;
    }

    public String[] getAlternatives() {return alternatives;}

    /**
     * Setter, updates the question
     * @param newQuestion
     * @return
     */
    public String setQuestion (String newQuestion) {
        question = newQuestion;
        return question;
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

        //Behövs ej
        //System.out.println(flash.getQuestion() + "\n");
        //System.out.println(flash.getAnswer());
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
    public void createAMultipleChoiceCard () {
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


        //Behövs ej
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
