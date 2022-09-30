import Model.Card;
import Model.Set;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBase {

    //Lists of the different types of sets
    private HashMap<String, Set> fc = new HashMap<>();      // A list of all sets of flashcards
    private HashMap<String, Set> mc = new HashMap<>();      // A list of all sets of multiple choice cards
    private HashMap<String, Set> s = new HashMap<>();       // A list of all sets of spelling cards

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
                case "fs":
                    Set fcSetTmp = new Set(nameStructure[1]);
                    fc.put(nameStructure[1], readFile(setFolderPath.resolve(fileName), fcSetTmp));
                    break;
                case "mcs":
                    Set mcSetTmp = new Set(nameStructure[1]);
                    mc.put(nameStructure[1], readFile(setFolderPath.resolve(fileName), mcSetTmp));
                    break;
                case "ss":
                    //Set sSetTmp = new Set(nameStructure[1]);
                    //multiChoiceSets.add(readFile(setFolderPath.resolve(fileName), sSetTmp));
                    break;
                default:
                    System.out.println("File with missing marker: " + fileName);
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

    // -------------------------------------------------------------------
    // Updates methods for the lists of sets

    public void updateAll() {
        fc.clear();
        mc.clear();
        s.clear();

        initialise();
    }

    // ------- For Map Sets -------

    public void updateAllFlashSets() {
        updateAllSets(fc);
    }

    public void updateAllMultiSets() {
        updateAllSets(mc);
    }

    public void updateAllSpellingSets() {
        updateAllSets(s);
    }

    // ------- For Singular Sets -------

    public void updateAFlashSet(String filename) {
        updateASet(filename, fc);
    }

    public void updateAMultiSet(String filename) {
        updateASet(filename, mc);
    }

    public void updateASpellingSet(String filename) {
        updateASet(filename, s);
    }

    // ------- General -------

    // Given a filename update its corresponding set
    public void updateASet(String fileName, HashMap<String, Set> mapTmp) {
        for (int i = 0; i < mapTmp.size(); i++) {
            Set setInFocus = mapTmp.get(fileName);
            if (setInFocus.getName().equals(fileName)) {
                Set setTmp = new Set(fileName);
                String typeTmp = "";
                if (mapTmp == fc) typeTmp = "/fc.";
                else if (mapTmp == mc) typeTmp = "/mc.";
                else if (mapTmp == s) typeTmp = "/s.";
                try {
                    mapTmp.replace(fileName, readFile(setFolderPath.resolve(typeTmp + fileName + ".txt"), setTmp));
                }
                    catch(Exception e) {
                        System.out.println("Error: File structure is wrong");
                }
            }
        }
    }

    public void updateAllSets(Map<String, Set> mapTmp) {
        for (File fileEntry : setFolderFile.listFiles()) {
            String fileName = fileEntry.getName();

            String[] nameStructure = fileName.split("\\.", 3);

            if (nameStructure[0].equals(mapTmp.toString())) {
                Set flashSetTmp = new Set(nameStructure[1]);
                mapTmp.put(nameStructure[1], readFile(setFolderPath.resolve(fileName), flashSetTmp));
            }
        }
    }

    // ------- Getters -------

    public Set getFlashSet(String setname) { return fc.get(setname); }
    public Set getMultiSet(String setname) { return mc.get(setname); }
    public Set getSpellingSet(String setname) { return s.get(setname); }

    public HashMap<String, Set> getFlashSets() { return fc; }
    public HashMap<String, Set> getMultiSets() { return mc; }
    public HashMap<String, Set> getSpellingSets() { return s; }

    public Path getSetFolderPath() { return setFolderPath; }
    public File getSetFolderFile() { return setFolderFile; }
}
