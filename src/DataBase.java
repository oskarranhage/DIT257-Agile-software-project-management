import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    static String filePathAnthon = "C:\\Users\\Anthon\\Desktop\\untitled\\src\\";
    static String filePath = filePathAnthon;

    private List<FlashSet> flashSets = new ArrayList<>();

    public DataBase() throws IOException {
        readFlashCardFile();
    }

    public void readFlashCardFile() throws IOException {
        Path filepath = Path.of(filePath + "test2.txt"); // "./sets/set1.txt"
        String content = Files.readString(filepath);
        String[] lines = content.split("\r?\n|\r");

        ArrayList<Card> cards = new ArrayList<>();
        String name = lines[0];
        for (int i = 1; i < lines.length ; i++) {
            cards.add(lineToCard(lines[i]));
        }
        flashSets.add(new FlashSet(name, cards));
    }

    public Card lineToCard(String line) {
        String[] temp = line.split("\\.");
        return new Card(temp[0],temp[1]);
    }

    public List<FlashSet> getFlashSets() {
        return flashSets;
    }
}
