import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FlashSet extends Set {

    FlashSet(String name) {
        super.name = name;
    }

    FlashSet(String name, ArrayList<Card> cards) {
        super.name = name;
        super.cards = cards;
    }

    @Override
    public void run() {
        System.out.println("You are running " + super.name + " as a flash set.");
        System.out.println("Press enter to view the first card, view answer by pressing enter again.");


        int count = 1;
        for (Card card : super.cards) {
            try { // Wait for user to press enter
                System.in.read();
            } catch (IOException e) {e.printStackTrace();}

            System.out.println("Card " + count + " :");
            System.out.println("-------------------------------");

            System.out.println(card.getQuestion()); // Print string 1

            try { // Wait for user to press enter
                System.in.read();
            } catch (IOException e) {e.printStackTrace();}

            System.out.println("Answer: " + card.getAnswer()); // Print string 2 (Answer)
            System.out.println("-------------------------------");
            count++;
        }
        System.out.println("Congratulations! You finished the set. :)");
    }
}

