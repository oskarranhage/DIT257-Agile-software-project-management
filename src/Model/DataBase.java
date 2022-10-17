package Model;
import Controller.CreateSetListItem;

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

    // ------- Getters -------

    public Set getSet(String setname) {return new Set(setHashMap.get(setname));}

    public HashMap<String, Set> getSetHashMap() { return new HashMap<>(setHashMap); }
    public ArrayList<CreateSetListItem> getCreateSetListItems() {return new ArrayList<>(createSetListItems);}
}
