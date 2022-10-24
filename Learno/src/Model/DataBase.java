package Model;
import Controller.CreateSetListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase extends FileManager {

    FileManager fm = new FileManager();

    /**
     * setHashMap is a hash map that contains all sets readable. The keys are entered in the format : setType.name
     */
    private HashMap<String, Set> setHashMap = new HashMap<>();

    private ArrayList<CreateSetListItem> createSetListItems = new ArrayList<>();

    /**
     * Constrinctor initialises data.
     */
    public DataBase() {
        initialise();
    }

    /**
     * Reads files and puts them into setHashMap..
     */
    private void initialise() {
        ArrayList<Set> allSets = getAllSets();
        for (Set set:allSets) {
            setHashMap.put(set.getName(),set);
        }
    }

    // -------------------------------------------------------------------
    // Updates methods for the lists of sets

    /**
     * Clears setHashMap and initialises
     */
    public void updateAll() {
        setHashMap.clear();
        initialise();
    }

    // ------- General -------

    /**
     * Given a filename update its corresponding set. Removes set from setHashMap if it is not readable.
     * @param fileName The given fileName (setType.setName)
     */
    public void updateSet(String fileName) {
        try {
            setHashMap.replace(fileName,fm.readFile(fileName));
        } catch (Exception e) {setHashMap.remove(fileName);} //remove the set from setHashMap if file is not readable.
    }

    /**
     * Gets the set size.
     * @param setName The set name. In the format : (setName) without setType and without .txt
     * @return The size of the list of cards inside the Set.
     */
    public int getSetSize(String setName){
        return setHashMap.get(setName).getCards().size();
    }

    /**
     * Gets the answers of the given set at the given index.
     * @param setName The name of the set in format : (setName) without setType and without .txt
     * @param cardIndex The index of the card in the set.
     * @return Returns a list of answers.
     */
    public String[] getAnswers(String setName, int cardIndex){
        return setHashMap.get(setName).getCardAtIndex(cardIndex).getAnswers();
    }

    public String getQuestion(String setName, int cardIndex){
        return setHashMap.get(setName).getCardAtIndex(cardIndex).getQuestion();
    }

    public String getAnswer(String setName, int cardIndex) {
        return setHashMap.get(setName).getAnswersOfCardAtIndex(cardIndex)[0];
    }

    public void deleteSet(String setName) throws IOException {
        deleteTextFile(setName);
        setHashMap.remove(setName);
        updateAll();
    }

    public void addCreateSetListItem(CreateSetListItem c) {
        createSetListItems.add(c);
    }

    public void clearCreateSetListItem () {createSetListItems.clear();}

    public Card getCardAtIndex(String setName, int cardIndex){return setHashMap.get(setName).getCardAtIndex(cardIndex);}

    // ------- Getters -------

    public Set getSet(String setname) {return setHashMap.get(setname);}

    public HashMap<String, Set> getSetHashMap() { return setHashMap; }
    public ArrayList<CreateSetListItem> getCreateSetListItems() {return createSetListItems;}
}
