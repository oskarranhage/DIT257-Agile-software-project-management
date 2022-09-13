import java.util.Scanner;

public class Learno {
    Scanner input = new Scanner(System.in);

    public static void main() {
        new Learno().writeAFlashCard();
    }

    void writeAFlashCard() {
        FlashCards tmp = new FlashCards("hej", "då");

        System.out.println(tmp.getQuestion());
        input.nextLine();
        System.out.println(tmp.getAnswer());
    }
}
