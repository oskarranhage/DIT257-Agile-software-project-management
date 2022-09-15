import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class MultipleChoiceSet {
    String name;
    ArrayList<MultipleChoiceCard> cards = new ArrayList<>();
    void addCard(MultipleChoiceCard c){
        this.cards.add(c);
    }
    void removeCard(MultipleChoiceCard c){
        this.cards.remove(c);
    }

    public Card[] readFile(String filePath) {
        Path filepath = Path.of(filePath); // "./sets/set1.txt"
        String content = "Empty";
        try {
            content = Files.readString(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(content);
        String[] lines = content.split("\r?\n|\r");
        System.out.println(Arrays.asList(lines));

        return null;
    }
}
