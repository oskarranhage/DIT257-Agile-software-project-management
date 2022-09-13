import java.util.Scanner;

public class Main {
    Scanner input = new Scanner(System.in);

    public static void main() {
        new Main().writeAFlashCard();
    }

    void writeAFlashCard() {
        FlashCards tmp = new FlashCards("hej", "då");

        System.out.println(tmp.getQuestion());
        input.nextLine();
        System.out.println(tmp.getAnswer());
    }
}
