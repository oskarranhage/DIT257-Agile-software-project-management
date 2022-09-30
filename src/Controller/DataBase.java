package Controller;

import Model.Card;
import Model.Set;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    private final File setFolderFile = setFolderPath.toFile();

    // Constructor initialises the workable data
    public DataBase() {
        initialise();
    }

    private void initialise() {
        for (File fileEntry : setFolderFile.listFiles()) {
            String fileName = fileEntry.getName();

            String[] nameStructure = fileName.split("\\.", 3); // 1: Type, 2: Name, 3: txt

            switch (nameStructure[0]) { // fc = FlashCard, mc = Multiple Choice, s = Spelling
                case "fc":
                    flashSets.add(readFile(setFolderPath.resolve(fileName)));
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



    // Given a filename update its corresponding set
    public void updateAFlashCardSet(String fileName) {
        for (int i = 0; i < flashSets.size(); i++) {
            Set setInFocus = flashSets.get(i);
            if (setInFocus.getName().equals(fileName)) {
                flashSets.set(i, readFile(setFolderPath.resolve(fileName + ".txt")));
            }
        }
    }

    public void updateAllFlashCardSets() {
        for (File fileEntry : setFolderFile.listFiles()) {
            String fileName = fileEntry.getName();

            String[] nameStructure = fileName.split("\\.", 3);

            if (nameStructure[0].equals("FlashSet")) {
                flashSets.add(readFile(setFolderPath.resolve(fileName)));
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
    public File getSetFolderFile() { return setFolderFile; }




    /**
     * Reads a file and converts it into a set.
     * @param filepath the path of the file that should be read
     * @return returns a Set
     */
    private Set readFile(Path filepath) {
        String fileName = filepath.toFile().getName();
        Set set;
        String content = "Empty";
        try {
            content = Files.readString(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] lines = content.split("\r?\n|\r");
        String name = fileName.split("\\.")[1];
        set = new Set(name);
        //Assigns the right setType according to file name
        if(fileName.split("\\.")[0].contains("fs")) {set.setTypeFlashCard();}
        else if(fileName.split("\\.")[0].contains("ss")) {set.setTypeSpelling();}
        else if(fileName.split("\\.")[0].contains("mcs")) {set.setTypeMultipleChoice();}

        for (int i = 0; i < lines.length ; i++) {
            String[] cardRepresent = lines[i].split("\\.");
            if(cardRepresent.length == 2){
                set.addCard(new Card(cardRepresent[0],cardRepresent[1]));
            }
            else if (cardRepresent.length == 5) {
                String[] listOfAnswers = {cardRepresent[1],cardRepresent[2],cardRepresent[3],cardRepresent[4]};
                set.addCard(new Card(cardRepresent[0], listOfAnswers));
            }
        }
        return set;
    }

    /**
     * Hrlper function for readFile that reads a text file in the Set folder given its name.
     * @param name the name of the textfile that should be read from the Sets folder
     * @return returns a Set.
     */
    public Set readTextFileWithName(String name){
        return readFile(setFolderPath.resolve(name+".txt"));
    }

    /**
     * Reads the textfile with the given name in the Sets folder.
     * @param name the name of the text file that should be read
     * @return returns a Set.
     */
    public Set readFileFromName(String name){
        return readFile(setFolderPath.resolve(name+".txt"));
    }

    /**
     * Tries to write the given String to a file in the Sets/ folder with the given name.
     * @param name the name of the file it should try to write to.
     * @param content the string that it should write to the file.
     */
    private void writeTextFile(String name, String content) {
        try {
            Files.writeString(setFolderPath.resolve(name+".txt"), content, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }
    }

    /**
     * Saves the given set to a file in the format shown in the text files.
     * @param set the set that gets written to a file.
     */
    public void saveSet(Set set){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        ArrayList<Card> cards = set.getCards();
        for (Card card: cards) {
            sb.append(card.getQuestion());
            for (String answer : card.getAnswers()) {
                if(answer != null){
                    sb.append("."+answer);
                }
            }
            sb.append("\n");
        }
        String type = "fs.";
        if(set.getThisSetType() == Set.setType.Spelling){type = "ss.";}
        else if(set.getThisSetType() == Set.setType.MultipleChoice){type = "mcs.";}
        writeTextFile(type + set.getName(),sb.toString());
    }

    /**
     * Creates a file with a given name. If the creation fails, a message gets printed and the methods returns false.
     * @param name The name of the text file (without .txt) that shuld be created .
     * @return True if success, false if fail.
     */
    public boolean makeTextFile(String name) {
        try{
            File mySet = setFolderPath.resolve(name+".txt").toFile();
            if (mySet.createNewFile()) {
                System.out.println("File created: " + mySet.getName());
                return true;
            } else {
                System.out.println("File already exists.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns an ArrayList of the names of all sets in the given folder, this method is recursive and
     * also returns the names of the sets in folders.
     * @param path - the path
     * @return
     */
    private ArrayList<String> getNamesOfSetsInFolder(Path path){
        File folder = path.toFile();
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> listOfFilesAsStrings = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                listOfFilesAsStrings.add(file.getName());
            } else if (file.isDirectory()) { // If file is a directory, enter recursion.
                getNamesOfSetsInFolder(setFolderPath.resolve(file.getName()));
            }
        }
        return listOfFilesAsStrings;
    }

    /**
     * Helper function for getNamesOfSetsInFolder(Path path).
     * @return The names of all textfiles in Sets folder as an ArrayList<String>.
     */
    public ArrayList<String> getNamesOfSavedSets() {
        return getNamesOfSetsInFolder(setFolderPath);
    }
}
