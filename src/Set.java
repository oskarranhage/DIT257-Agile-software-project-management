import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

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
    public void readFile(String fileName) {
        Path filepath = Path.of("C:\\Users\\Alex\\IdeaProjects\\DIT257-Agile-software-project-management\\Sets\\" + fileName + ".txt"); // "./sets/set1.txt"
        String content = "Empty";
        try {
            content = Files.readString(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] lines = content.split("\r?\n|\r");
        name = lines[0];
        for (int i = 1; i < lines.length ; i++) {
            String[] cardRepresent = lines[i].split("\\.");
            addCard(new Card(cardRepresent[0],cardRepresent[1]));
        }
    }
}

