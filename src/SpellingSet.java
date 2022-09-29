import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellingSet extends Set {
    static Scanner sc = new Scanner(System.in);

    SpellingSet(String name) {
        super.name = name;
    }

    SpellingSet(String name, ArrayList<Card> cards) {
        super.name = name;
        super.cards = cards;
    }

    //@Override
    //run method for if you run a set as a spellingSet (a set full of spelling cards).
    public void run() {
        System.out.println("You are running " + super.name + " as a spelling set.");
        System.out.println("Type in the correct answer and hit enter.");

        int count = 1;
        for (Card card : super.cards) {
            System.out.println("Card " + count + " :");
            System.out.println("-------------------------------");

            System.out.println(card.getQuestion()); // Print string 1

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
