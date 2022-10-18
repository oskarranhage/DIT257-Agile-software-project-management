package Model;


import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManager {

    private Path setFilePath;

    /**
     * String representations of the different types of sets. These should not be modified, if these change,
     * old saved sets will be compromised.
     */
    private final String flashSetStringRepresentation = "fs";
    private final String spellingSetStringRepresentation = "ss";
    private final String multipleChoiceSetStringRepresentation = "mcs";

    /**
     * regexSplitter is the string that is used in split to differentiate between question / answer and setType
     * from setName when reading files
     */
    private final String regexSplitter = "\\.";
    /**
     * splitterChar should represent the character that is written in regexSplitter and is used when writing files.
     * regexSplitter and splitterChar can not be the same in the case of using a dot because of the way regex works.
     */
    private final char splitterChar = '.';

    public FileManager(){
        setFilePath = Paths.get(".").toAbsolutePath().resolve("Sets"); //Dynamically sets the path according to
    }

    // CREATION AND WRITING OF FILES
    // ----------------------------------------------------------------------------------------------------
    /**
     * Creates a file with a given name. If the creation fails, an exception gets thrown.
     * @param path The path where the text file should be created .
     */
    public void createTextFile(Path path) throws Exception {
        try{
            Files.createFile(path);
        } catch (IOException e) {
            throw new Exception("File already exists.");
        }
    }
    /**
     * Creates textfile with given name inside /Sets/.
     * @param name The name of the set that should be created.
     * @throws Exception Forwards exception from File.createNewFile().
     */
    public void createTextFile(String name) throws Exception {
        createTextFile(setFilePath.resolve(name + ".txt"));
    }

    public void deleteTextFile(String setName) throws IOException {
        Files.delete(setFilePath.resolve(setName+".txt"));
    }

    /**
     * Creates textfile with given name inside /Sets/folderName/.
     * @param name The name of the set that should be created.
     * @param folderName The name of the folder inside /Sets/ where the textfile should be created.
     * @throws Exception Forwards exception from File.createNewFile().
     */
    public void createTextFile(String name, String folderName) throws Exception {
        createTextFile(setFilePath.resolve(folderName + "/" + name + ".txt"));
    }

    /**
     * Creates a folder in /Sets/. Sets can be saved inside these folders. Cannot create a folder inside a folder.
     * @param name The name of the folder.
     */
    public void createFolder(String name) throws IOException {
        Files.createDirectory(setFilePath.resolve(name));
    }

    /**
     * Tries to write the given String to a file in the Sets/ folder with the given name.
     * @param path The path of the file that should be created. (ex. setFilePath.resolve("textfile.txt"))
     * @param content The string that should be written to the textfile.
     * @throws Exception Forwards Files.writeString() exception.
     */
    private void writeFile(Path path, String content) throws Exception {
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }
    /**
     * Writes a string to a text file in the default sets folder.
     * @param fileName the entire name of the textfile that should be written. (setType.setName)
     * @param content the string that should be written to the textfile.
     * @throws Exception Forwards Files.writeString() exception.
     */
    public void writeFile(String fileName, String content) throws Exception {
        writeFile(setFilePath.resolve(fileName + ".txt"), content);
    }


    /**
     * Writes a string to a text file in the default sets folder.
     * @param fileName the entire name of the textfile that should be written. (setType.setName)
     * @param content the string that should be written to the textfile.
     * @param folder The folder in which the file that should be written to exists.
     * @throws Exception Forwards Files.writeString() exception.
     */
    public void writeFile(String fileName, String folder, String content) throws Exception {

        writeFile(setFilePath.resolve(folder + "/" + fileName + ".txt"), content);
    }

    // READING OF FILES
    // ----------------------------------------------------------------------------------------------------
    /**
     * Reads a file and returns it as a Set.
     * @param filepath The file path of the file that should be read.
     * @return Returns the corresponding set read from the file.
     * @throws Exception If file does not exist or can not be read or if the file is in the wrong format, throw exception.
     */
    private Set readFile(Path filepath) throws Exception {
        String fileName = filepath.toFile().getName();
        //Assigns the right setType according to file name, throws exception if not possible.
        Set.setType type;
        if(fileName.split(regexSplitter)[0].equals(flashSetStringRepresentation)) {type = Set.setType.FlashCard;}
        else if(fileName.split(regexSplitter)[0].equals(spellingSetStringRepresentation)) {type = Set.setType.Spelling;}
        else if(fileName.split(regexSplitter)[0].equals(multipleChoiceSetStringRepresentation)) {type = Set.setType.MultipleChoice;}
        else {throw new Exception("Filename not compatible.");}
        Set set = new Set(fileName.split(regexSplitter)[1], type);

        String content = Files.readString(filepath); // Throws exception if file is not textfile or does not exist.

        String[] lines = content.split("\r?\n|\r");
        for (int i = 0; i < lines.length ; i++) {
            String[] cardRepresent = lines[i].split(regexSplitter);
            if(cardRepresent.length == 2){
                set.addCard(new Card(cardRepresent[0],cardRepresent[1]));
            } else if (cardRepresent.length == 5) {
                String[] listOfAnswers = {cardRepresent[1],cardRepresent[2],cardRepresent[3],cardRepresent[4]};
                set.addCard(new Card(cardRepresent[0], listOfAnswers));
            }
        }
        set.setName(fileName.split(regexSplitter)[1]);
        return set;
    }

    /**
     * Reads the text file with the given name inside /Sets/.
     * @param textFileName The filename of the text file that should be read. (setType.setName)
     * @return Returns the corresponding set read from the file.
     * @throws Exception Forwards readFile(Path) exception.
     */
    public Set readFile(String textFileName) throws Exception {
        return readFile(setFilePath.resolve(textFileName+".txt"));
    }

    /**
     * Reads a file inside a given folder.
     * @param textFileName The filename of the text file that should be read. (setType.setName)
     * @param folderName The name of the folder in which the file that should be read exists.
     * @return Returns the corresponding set read from the file.
     * @throws Exception Forwards readFile(Path) exception.
     */
    public Set readFile(String textFileName, String folderName) throws Exception {
        return readFile(setFilePath.resolve(folderName + "/" + textFileName + ".txt"));
    }


    //SAVING A SET AS A TEXTFILE
    // ----------------------------------------------------------------------------------------------------
    /**
     * Saves the given set to a textfile in the right format.
     * @param set the set that gets written to a file.
     */
    public void saveSet(Set set,Path path) throws Exception {
        StringBuilder sb = new StringBuilder();
        ArrayList<Card> cards = set.getCards();
        for (Card card: cards) {
            sb.append(card.getQuestion());
            for (String answer : card.getAnswers()) {
                if(answer != null){
                    sb.append(splitterChar+answer);
                }
            }
            sb.append("\n");
        }
        String type = flashSetStringRepresentation;
        if(set.getThisSetType() == Set.setType.Spelling){type = spellingSetStringRepresentation;}
        else if(set.getThisSetType() == Set.setType.MultipleChoice){type = multipleChoiceSetStringRepresentation;}
        writeFile(type + splitterChar + set.getName(), sb.toString());
    }

    /**
     * Uses saveSet(Set,Path).
     * @param set The set that gets written to a file.
     * @throws Exception
     */
    public void saveSet(Set set) throws Exception {
        saveSet(set, setFilePath.resolve(getTypeStringRepresentation(set)+splitterChar+set.getName()+".txt"));
    }

    // GETTING LIST OF NAMES OF AVAILABLE SETS
    // ----------------------------------------------------------------------------------------------------
    /**
     * Returns an ArrayList of the names of all sets in the given folder, this method is recursive and
     * also returns the names of the sets in folders. NO TAIL RECURSION
     * @param path - the path in which to look for folders.
     * @return Returns an arraylist of Strings representing the names of sets inside a folder. This includes
     * sets inside folders.
     */
    public ArrayList<String> getNamesOfSets(Path path){
        File folder = new File(path.toString());
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> listOfFilesAsStrings = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                listOfFilesAsStrings.add(file.getName());
            } else if (file.isDirectory()) { // If file is a directory, enter recursion.
                ArrayList<String> listOfFilesInFolder = getNamesOfSets(file.toPath());
                for (String name : listOfFilesInFolder) {
                    listOfFilesAsStrings.add(name);
                }
            }
        }
        return listOfFilesAsStrings;
    }

    /**
     * Goes through all sets in folder path and calls recursively on folders.
     * @param path The path that should be read.
     * @return Returns a list of all sets inside folder and child folders of directory.
     */
    public ArrayList<Set> getAllSets(Path path){
        File folder = new File(path.toString());
        File[] listOfFiles = folder.listFiles();
        ArrayList<Set> listOfSets = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    listOfSets.add(readFile(path.resolve(file.getName())));
                } catch (Exception e) {System.out.println("Error reading set : " + file.getName());}
            } else if (file.isDirectory()) { // If file is a directory, enter recursion.
                ArrayList<String> listOfFilesInFolder = getNamesOfSets(file.toPath());
                for (String name : listOfFilesInFolder) {
                    try {
                        listOfSets.add(readFile(name+".txt"));
                    } catch (Exception e) {System.out.println("Error reading set : " + name);}
                }
            }
        }
        return listOfSets;
    }

    public ArrayList<Set> getAllSets() {
        return getAllSets(setFilePath);
    }

    /**
     * Runs getNameOfSets with setFilePath as argument.
     * @return the result of getNamesOfSets(setFilePath)
     */
    public ArrayList<String> getNamesOfSets(){
        return getNamesOfSets(setFilePath);
    }

    public String getTypeStringRepresentation(Set set){
        if(set.getThisSetType() == Set.setType.FlashCard){return flashSetStringRepresentation;}
        else if(set.getThisSetType() == Set.setType.FlashCard){return spellingSetStringRepresentation;}
        else if (set.getThisSetType() == Set.setType.FlashCard){return multipleChoiceSetStringRepresentation;}
        else return null;
    }

    //GETTERS
    // ----------------------------------------------------------------------------------------------------

    public String getFlashSetStringRepresentation() {return flashSetStringRepresentation;}
    public String getSpellingSetStringRepresentation() {return spellingSetStringRepresentation;}
    public String getMultipleChoiceSetStringRepresentation() {return multipleChoiceSetStringRepresentation;}
    public String getRegexSplitter() {return regexSplitter;}
    public char getSplitterChar() {return splitterChar;}
    public Path getSetFilePath() {return setFilePath;}
}