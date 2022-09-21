import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    static String filePathAnthon = "C:\\Users\\Anthon\\Desktop\\LearnoFX\\src\\";
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

        for (int i = 1; i < lines.length ; i++) {
            String[] temp = lines[i].split("\\.");
            cards.add(new Card(temp[0],temp[1]));
        }
        flashSets.add(new FlashSet(lines[0], cards));
    }

    public List<FlashSet> getFlashSets() {
        return flashSets;
    }
}
