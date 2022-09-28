import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public abstract class Set implements Runnable{
    String name;
    String basePath = new File("").getAbsolutePath();
    private final Path setFilePath = Path.of(basePath + "/Sets/");
    ArrayList<Card> cards = new ArrayList<>();

    void addCard(Card c){
        this.cards.add(c);
    }
    void removeCard(Card c){
        this.cards.remove(c);
    }
    @Override
    public void readFile(String fileName) {
        Path filepath = Path.of(setFilePath + "/fc." + fileName + ".txt"); // "./sets/set1.txt"
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

    public String getName() { return name; }
}

