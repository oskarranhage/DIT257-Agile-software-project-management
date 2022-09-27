import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class DataBase {

    //Lists of the different types of sets
    private ArrayList<Set> flashSets;           // A list of all sets of flashcards
    private ArrayList<Set> multiChoiceSets;     // A list of all sets of multiple choice cards
    //private ArrayList<Set> spellingSets;      // A list of all sets of spelling cards

    // Directory for the folder of sets in File and Path data types.
    private final Path setFolderPath = Path.of(new File("").getAbsolutePath() + "/Sets/");
    private final File setFolder = setFolderPath.toFile();

    // Constructor initialises the workable data
    public DataBase() {
        initialise();
    }

    private void initialise() {
        for (File fileEntry : setFolder.listFiles()) {
            String fileName = fileEntry.getName();

            String[] nameStructure = fileName.split("\\.", 3); // 1: Type, 2: Name, 3: txt

            switch (nameStructure[0]) { // fc = FlashCard, mc = Multiple Choice, s = Spelling
                case "fc":
                    Set fSTmp = new FlashSet(nameStructure[1], new ArrayList<>());
                    flashSets.add(readFile(setFolderPath.resolve(fileName), fSTmp));
                    break;
                case "mc":

                    break;
                case "s":

                    break;
                default:
                    //System.out.println("File with missing marker: " + fileName);
            }
        }
    }

    // Converts a txt file into a Set
    private Set readFile(Path path, Set set) {
        try {
            String content = Files.readString(path);
            String[] lines = content.split("\r?\n|\r");

            for (int i = 1; i < lines.length; i++) {
                set.addCard(lineToCard(lines[i]));
            }
        } catch (IOException e){
            System.out.println("error: IOException");
        }

        return set;
    }

    private Card lineToCard(String line) {
        String[] temp = line.split("\\.");
        return new Card(temp[0],temp[1]);
    }

    // Given a filename update its corresponding set
    public void updateAFlashCardSet(String fileName) {
        for (int i = 0; i < flashSets.size(); i++) {
            Set setInFocus = flashSets.get(i);
            if (setInFocus.getName().equals(fileName)) {
                Set setTmp = new FlashSet(fileName, new ArrayList<>());
                flashSets.set(i, readFile(setFolderPath.resolve(fileName + ".txt"), setTmp));
            }
        }
    }

    public void updateAllFlashCardSets() {
        for (File fileEntry : setFolder.listFiles()) {
            String fileName = fileEntry.getName();

            String[] nameStructure = fileName.split("\\.", 3);

            if (nameStructure[0].equals("FlashSet")) {
                Set flashSetTmp = new FlashSet(nameStructure[1], new ArrayList<>());
                flashSets.add(readFile(setFolderPath.resolve(fileName), flashSetTmp));
            }
        }
    }

    public void updateAll() {
        flashSets.clear();
        multiChoiceSets.clear();

        initialise();
    }

    public ArrayList<Set> getFlashSets() {
        return flashSets;
    }

    public Path getSetFolderPath() { return setFolderPath; }
    public File getSetFolder() { return setFolder; }
}
