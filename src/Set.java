import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public abstract class Set implements Runnable{
    String name;
    ArrayList<Card> cards = new ArrayList<Card>();

    void addCard(Card c){
        this.cards.add(c);
    }
    void removeCard(Card c){
        this.cards.remove(c);
    }
    @Override
    public void readFile(String filePath) {
        Path filepath = Path.of(filePath); // "./sets/set1.txt"
        String content = "Empty";
        try {
            content = Files.readString(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(content);
        String[] lines = content.split("\r?\n|\r");
        name = lines[0];
        for (int i = 1; i < lines.length ; i++) {
            addCard(lineToCard(lines[i]));
        }
    }
    public Card lineToCard(String line) {
        String[] temp = line.split(".");
        Card card = new Card(temp[0],temp[1]);
        return card;
    }


}

