import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MultipleChoiceSet extends Set {
    static Scanner sc = new Scanner(System.in);
    Random rand = new Random();

    MultipleChoiceSet(String name) {
        super.name = name;
    }

    MultipleChoiceSet(String name, ArrayList<Card> cards) {
        super.name = name;
        super.cards = cards;
    }

    //@Override
    public void run() {
        System.out.println("You are running " + super.name + " as a multipleChoice set.");
        System.out.println("Type in the correct alternative answer.");

        int count = 1;
        for (Card card : super.cards) {
            System.out.println("Card " + count + " :");
            System.out.println("-------------------------------");

            System.out.println(card.getQuestion() + "\n"); // Print string 1
            System.out.println(card.getAlternatives());
            System.out.println("Type in the correct answer out of the alternatives: ");

            String input = sc.nextLine();
            if (input.equals(card.getAnswer())) {

                System.out.println("That is correct! \n The answer is: " + card.getAnswer());
            }
            else {
                System.out.println("Unfortunately that's wrong. \n The answer is: " + card.getAnswer());
            }

            System.out.println("Answer: " + card.getAnswer()); // Print string 2 (Answer)
            System.out.println("-------------------------------");
            count++;
        }
        System.out.println("Congratulations! You finished the set. :)");
    }
}
