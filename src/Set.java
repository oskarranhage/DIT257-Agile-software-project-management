import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public abstract class Set {
    String name;
    //Set set;
    //String basePath = new File("").getAbsolutePath();
    //private final Path setFilePath = Path.of(basePath + "/Sets/");
    //public static DataBase db = new DataBase();
    ArrayList<Card> cards = new ArrayList<Card>();

    void addCard(Card c){
        this.cards.add(c);
    }
    void removeCard(Card c){
        this.cards.remove(c);
    }

    public void run() {

    }

    /*@Override
    public void readFile(String fileName) {

        //db.readFile(db.getSetFolderPath(), set);
        /*Path filepath = Path.of(db.getSetFolderFile() + "/" + fileName + ".txt"); // "./sets/set1.txt"
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
        }*/
    //}

    public String getName() { return name; }
}

