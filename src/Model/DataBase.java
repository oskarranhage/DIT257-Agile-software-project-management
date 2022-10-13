package Model;
import Controller.SingleAnswerListItem;
import Controller.MultipleAnswerListItem;

import Controller.MultipleAnswerListItem;
import Controller.SingleAnswerListItem;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBase extends FileManager {

    FileManager fm = new FileManager();

    /**
     * setHashMap is a hash map that contains all sets readable. The keys are entered in the format : setType.name
     */
    private HashMap<String, Set> setHashMap = new HashMap<>();

    private ArrayList<SingleAnswerListItem> createSingeListItems = new ArrayList<>();
    private ArrayList<MultipleAnswerListItem> createMultipleListItems = new ArrayList<>();

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

    // ------- Getters -------

    public Set getSet(String setname) {return setHashMap.get(setname);}

    public HashMap<String, Set> getSetHashMap() { return setHashMap; }
    public ArrayList<SingleAnswerListItem> getCreateSingleListItems() {return createSingeListItems;}
    public ArrayList<MultipleAnswerListItem> getCreateMultipleListItems() {return createMultipleListItems;}
}
