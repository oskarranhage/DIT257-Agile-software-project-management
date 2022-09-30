// Author : Alexander Lisborg
package View;

import Controller.DataBase;
import Model.Card;
import Model.Set;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class View {
    //Change this path to the absolut path your project folder.
    String pathAlex = "C:\\Users\\Alex\\IdeaProjects\\DIT257-Agile-software-project-management";
    Scanner sc;
    DataBase db;

    public View(DataBase db) {
        this.db = db;
        this.sc = new Scanner(System.in);
    }

    /**
     * startMenu is the first menu shown to the user, from here you can navigate to all other menus.
     */
    public void startMenu(){
        Boolean contin = true;
        while (contin) {
            System.out.println("Enter 1 , 2 or 3");
            System.out.println("1 Run set");
            System.out.println("2 Create Set");
            System.out.println("3 Edit Set");
            System.out.println("Enter x to exit");
            String input = sc.nextLine();
            switch (input) {
                case "1" :
                    chooseSet();
                    break;
                case "2" :
                    createSet();
                    break;
                case "3" :
                    editSet();
                    break;
                case "x" :
                    contin = false;
                    break;
                default :
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }

    /**
     * The choose set menu is where you select the set you want to run.
     */
    private void chooseSet(){
        while(true){
            System.out.println("Type the name of the set you want to run.");

            ArrayList<String> namesOfSets = db.getNamesOfSavedSets();
            String[] fileNames = new String[namesOfSets.size()];
            String[] setTypes = new String[namesOfSets.size()];
            for (int i = 0 ; i < namesOfSets.size() ; i++) {
                setTypes[i] = namesOfSets.get(i).split("\\.")[0];
                fileNames[i] = namesOfSets.get(i).split("\\.")[1];
            }
            for (String name : fileNames) {System.out.println(name);}
            String input = sc.nextLine();
            int indexof = Arrays.asList(fileNames).indexOf(input);

            if(setTypes[indexof].equals("fs")){
                runFlashSet(db.readTextFileWithName("fs." + input));
                break;
            }
            else if (setTypes[indexof].equals("ss")){
                runSpelling(db.readTextFileWithName("ss." + input));
                break;
            }
            else if (setTypes[indexof].equals("mcs")) {
                runMultipleChoiceSet(db.readTextFileWithName("mcs." + input));
                break;
            }
        }
    }

    /**
     * Runs a flash set in the terminal view.
     * @param set The set that will be run
     */
    private void runFlashSet(Set set) {
        int count = 1;
        System.out.println("You are running " + set.getName() + " as a flash set.");
        for (Card card : set.getCards()){
            System.out.println("Press enter to view next card.");
            sc.nextLine();
            System.out.println("Card " + count + " :");
            System.out.println(card.getQuestion());
            sc.nextLine();
            System.out.println(card.getAnswers()[0]);
            count++;
        }
        System.out.println("Congratulations! You reached the end of the set. Press enter to continue.");
        sc.nextLine();
    }
    /**
     * Runs a text set in the terminal view.
     * @param set The set that will be run
     */
    private void runSpelling(Set set){
        int points = 0;
        int count = 1;
        System.out.println("You are running " + set.getName() + " as a spelling set.");
        for (Card card : set.getCards()){
            System.out.println("Press enter to view next card.");
            sc.nextLine();
            System.out.println("Card " + count + " :");
            System.out.println(card.getQuestion());
            System.out.println("Type out the answer:");
            String input = sc.nextLine();
            if(input.toLowerCase().equals(card.getAnswers()[0].toLowerCase())) {
                System.out.println("Your answer was correct!");
                points++;
            }
            else {
                System.out.println("Your answer was incorrect!");
                System.out.println("The correct answer is : " + card.getAnswers()[0]);
            }
            count++;
        }
        System.out.println("Congratulations! You reached the end of the set. You gave " + points + " correct answers.");
        System.out.println("Press enter to continue.");
        sc.nextLine();
    }

    /**
     * Runs a multiple choice set in the terminal view.
     * @param set The set that will be run
     */
    private void runMultipleChoiceSet(Set set){
        int points = 0;
        int count = 1;
        System.out.println("You are running " + set.getName() + " as a multiple choice set.");
        for (Card card : set.getCards()) {
            System.out.println("Press enter to view next card.");
            sc.nextLine();
            System.out.println("Card " + count + " :");
            System.out.println(card.getQuestion());
            String correctAnswer = card.getAnswers()[0];
            List<String> answersShuffled = Arrays.asList(card.getAnswers());
            Collections.shuffle(answersShuffled);
            System.out.println(answersShuffled);
            System.out.println("Please enter the answer :");
            String input = sc.nextLine();
            if (input.toLowerCase().equals(correctAnswer.toLowerCase())) {
                System.out.println("Your answer was correct!");
                points++;
            }
            else {
                System.out.println("Your answer was incorrect!");
                System.out.println("The correct answer was : " + correctAnswer);
            }
            count++;
        }
        System.out.println("Congratulations! You reached the end of the set. You gave " + points + " correct answers.");
        System.out.println("Press enter to continue.");
        sc.nextLine();
    }

    /**
     * Handles the printing of the create set menu. The createSet method that will be run depends on
     * user input.
     */
    private void createSet(){
        boolean cont = true;
        while(cont){
            System.out.println("Choose the set you want to create by entering a number 1-3.");
            System.out.println("1 : FlashSet");
            System.out.println("2 : Spelling");
            System.out.println("3 : MultipleChoiceSet");
            String input = sc.nextLine();
            switch(input) {
                case "1" :
                    createFlashSet();
                    cont = false;
                    break;
                case "2" :
                    createSpelling();
                    cont = false;
                    break;
                case "3" :
                    createMultipleChoiceSet();
                    cont = false;
                    break;
            }
        }
    }


    /**
     * Handles the menu for creating flash sets, printing messages and taking user input as values.
     * @return A set with a name, one question and one answer, all values taken from user input.
     */
    private Set createFlashSet(){
        Set set;
        System.out.println("Give the set a name :");
        String name = sc.nextLine();
        if (db.makeTextFile("fs."+name)){
            int count = 0;
            set = new Set(name);
            set.setTypeFlashCard();
            while(true){
                count++;
                System.out.println("Writing card " + count);
                System.out.println("What is the question?");
                String question = sc.nextLine();
                System.out.println("What is the Answer?");
                String answer = sc.nextLine();
                Card card = new Card(question, answer);
                set.addCard(card);
                System.out.println("Enter x to save set, enter anything else to write next card!");
                String input = sc.nextLine();
                if(input.equals("x")){
                    db.saveSet(set);
                    return set;
                }
            }
        }
        else return null;
    }

    /**
     * Handles the menu for creating spelling sets, printing messages and taking user input as values.
     * @return A set with a name, one question and one answer, all values taken from user input.
     */
    private Set createSpelling(){
        Set set;
        System.out.println("Give the set a name :");
        String name = sc.nextLine();
        if (db.makeTextFile("ss."+name)){
            int count = 0;
            set = new Set(name);
            set.setTypeSpelling();;
            while(true){
                count++;
                System.out.println("Writing card " + count);
                System.out.println("What is the question?");
                String question = sc.nextLine();
                System.out.println("What is the Answer?");
                String answer = sc.nextLine();
                Card card = new Card(question, answer);
                set.addCard(card);
                System.out.println("Enter x to save set, enter anything else to write next card!");
                String input = sc.nextLine();
                if(input.equals("x")){
                    db.saveSet(set);
                    return set;
                }
            }
        }
        else return null;
    }

    /**
     * Handles the menu for creating multiple choice sets, printing messages and taking user input as values.
     * @return A set with a name, one question, one answer, and three dummy answers, all values taken from user input.
     */
    private Set createMultipleChoiceSet(){
        Set set;
        System.out.println("Give the set a name :");
        String name = sc.nextLine();
        if (db.makeTextFile("mcs."+name)){
            int count = 0;
            set = new Set(name);
            set.setTypeMultipleChoice();
            while(true){
                count++;
                System.out.println("Writing card " + count);
                System.out.println("What is the question?");
                String question = sc.nextLine();
                System.out.println("What is the Answer?");
                String answer = sc.nextLine();
                System.out.println("Type 3 dummy answers!");
                String ans1 = sc.nextLine();
                String ans2 = sc.nextLine();
                String ans3 = sc.nextLine();
                String[] answers = {answer, ans1, ans2, ans3};
                Card card = new Card(question, answers);
                set.addCard(card);
                System.out.println("Enter x to save set, enter anything else to write next card!");
                String input = sc.nextLine();
                if(input.equals("x")){
                    db.saveSet(set);
                    return set;
                }
            }
        }
        else return null;
    }

    /**
     * Edit set menu, lets you choose a set and edit a specific card in that set.
     */
    private void editSet(){
        System.out.println("Enter the name of the set you want to edit.");
        ArrayList<String> namesOfSets = db.getNamesOfSavedSets();
        String[] fileNames = new String[namesOfSets.size()];
        String[] setTypes = new String[namesOfSets.size()];
        for (int i = 0 ; i < namesOfSets.size() ; i++) {
            setTypes[i] = namesOfSets.get(i).split("\\.")[0];
            fileNames[i] = namesOfSets.get(i).split("\\.")[1];
        }
        for (String name : fileNames) {System.out.println(name);}
        String input = sc.nextLine();
        int indexof = Arrays.asList(fileNames).indexOf(input);

        if(Arrays.stream(fileNames).anyMatch(input::equals)){ //if fileNames contains input
            System.out.println("Editing set " + input);
            Set set = db.readFileFromName(setTypes[indexof] + "." + input);
            int count = 1;
            for (Card card:set.getCards()) { //Prints all cards
                if(card.getAnswers()[1] == null && card.getAnswers()[2] == null && card.getAnswers()[3] == null) { // If it's just one answer, print the single answer.
                    System.out.println(count + " : Question : " + card.getQuestion() + " ; Answer : [" + card.getAnswers()[0] + "]");
                } else if (card.getAnswers().length == 4) { // If there's multiple answers, print the list of them.
                    System.out.println(count + " : Question : " + card.getQuestion() + " ; Answers : " + Arrays.toString(card.getAnswers()));
                }
                count++;
            }
            System.out.println("Which card do you want to edit? Enter index.");
            int index = Integer.parseInt(sc.nextLine());
            if(index > set.getCards().size()){
                System.out.println("Enter a valid index.");
            }
            else if(set.getThisSetType() == Set.setType.FlashCard || set.getThisSetType() == Set.setType.Spelling) {
                System.out.println("Writing card " + index);
                System.out.println("What is the question?");
                String question = sc.nextLine();
                System.out.println("What is the Answer?");
                String answer = sc.nextLine();
                set.updateCard(index,new Card(question,answer));
                db.saveSet(set);
            } else if (set.getThisSetType() == Set.setType.MultipleChoice) {
                System.out.println("Writing card " + index);
                System.out.println("What is the question?");
                String question = sc.nextLine();
                System.out.println("What is the Answer?");
                String answer = sc.nextLine();
                System.out.println("Type 3 dummy answers!");
                String ans1 = sc.nextLine();
                String ans2 = sc.nextLine();
                String ans3 = sc.nextLine();
                String[] answers = {answer,ans1,ans2,ans3};
                Card card = new Card(question,answers);
                set.updateCard(index, card);
                db.saveSet(set);
            } else {System.out.println("error");}
        }
    }










}