package Model;
import Controller.CreateSetListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Database acts as a pipeline between the model and the rest of the program. It extends filemanager since some of
 * the FileManager methods needs to be used elsewhere aswell.
 */
public class DataBase extends FileManager {

    /**
     * setHashMap is a hash map that contains all sets readable. The keys are entered in the format : setType.name
     */
    private HashMap<String, Set> setHashMap = new HashMap<>();

    /**
     * An array of all the items that should be displayed when creating sets. This list is manipulated when adding
     * and removing terms / definitions.
     */
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
            setHashMap.replace(fileName,readFile(fileName));
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

    /**
     * Gets the question of the card at the given card index of set with the given name inside setHashMap.
     * @param setName The name of the set. In the format : (SetType.SetName)
     * @param cardIndex The index of the card inside the set.
     * @return Returns the question in the form of a String.
     */
    public String getQuestion(String setName, int cardIndex){
        return setHashMap.get(setName).getCardAtIndex(cardIndex).getQuestion();
    }

    /**
     * Gets the answer of the card at the given card index of set with the given name inside setHashMap.
     * @param setName The name of the set. In the format : (SetType.SetName)
     * @param cardIndex The index of the card inside the set.
     * @return Returns the question in the form of a String.
     */
    public String getAnswer(String setName, int cardIndex) {
        return setHashMap.get(setName).getAnswersOfCardAtIndex(cardIndex)[0];
    }

    /**
     * Deletes the set with the given setName from setHashMap and deletes the corresponding text file
     * from the hard drive.
     * @param setName The name of the set that should be deleted. In the format : (SetType.SetName)
     * @throws IOException Forwards exception if deleting the text file results in an error.
     */
    public void deleteSet(String setName) throws IOException {
        deleteTextFile(setName);
        setHashMap.remove(setName);
        updateAll();
    }

    /**
     * Adds a createSetListItem to the createSetListItems list.
     * @param c The createSetListItem object that should be added to the list.
     */
    public void addCreateSetListItem(CreateSetListItem c) {
        createSetListItems.add(c);
    }

    /**
     * Clears the createSetListItems list.
     */
    public void clearCreateSetListItem () {createSetListItems.clear();}

    /**
     * Gets the card with the given index inside the set with the given name inside setHashMap.
     * @param setName The name of the set that contains the card. In the format : (setName)
     * @param cardIndex The index of the card inside the cards list in the given set.
     * @return Returns a card.
     */
    public Card getCardAtIndex(String setName, int cardIndex){return setHashMap.get(setName).getCardAtIndex(cardIndex);}

    // ------- Getters -------

    /**
     * Gets the set with the given name from the setHashMap.
     * @param setname The name of the set. In the format : (setName)
     * @return Returns a set.
     */
    public Set getSet(String setname) {return setHashMap.get(setname);}

    /**Getter for hashmap*/
    public HashMap<String, Set> getSetHashMap() { return setHashMap; }
    /**Getter for createSetListItems*/
    public ArrayList<CreateSetListItem> getCreateSetListItems() {return createSetListItems;}
}
