package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class FileManager {

    static String basePath = new File("").getAbsolutePath();
    private final static Path setFilePath = Path.of(basePath + "/Sets/");

    //For testing
    /*public static void main(String[] args) {
        System.out.println("Set path: " + setFilePath);
        //writeFile();
        //System.out.println(setFilePath.getFileName());
        //editFile();
        //System.out.println(setFilePath.getFileName());
    }*/

    public static void writeFile() {
        Scanner sc = new Scanner(System.in);
        boolean temp = true;
        StringBuilder sb = new StringBuilder();
        System.out.println("What is the name of this set?");
        String name = "";
        try {
            name = sc.nextLine();
        } catch (NullPointerException e) {
            System.out.println("The set name cannot be empty");
        }
        sb.append(name).append("\n");
        while(temp){
            System.out.println("What is the question? Enter it here: ");
            String userQuestion = sc.nextLine();
            sb.append(userQuestion).append(".");
            System.out.println("What is the answer? Enter it here: ");
            String userAnswer = sc.nextLine();
            sb.append(userAnswer).append("\n");
            System.out.println("Do you want to continue? (n)");
            String ans = sc.nextLine();
            if (ans.equals("n")) {
                temp = false;
            }
        }
        makeFile(name,sb.toString());
    }

    public static void makeFile(String name, String string) {
        try{
            File mySet = new File (setFilePath + "/" + name + ".txt");
            if (mySet.createNewFile()) {
                System.out.println("File created: " + mySet.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Path path = Path.of(setFilePath + "/" + name + ".txt");
        try {
            Files.writeString(path, string, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }

    }

    //TODO: Do it more generic and not just for flashcards
    public void editFile(String setName, String string){
        Path path = Path.of(setFilePath + "/" + setName + ".txt");
        try {
            System.out.println("What do you want to change?\n"
                    + "Do you want to add more questions and answers to existing flashcard? (1)"
                    + "Do you want to change existing question to the flashcard? (2)"
                    + "Do you want to change existing answer to the flashcard? (3)"
                    + "Do you want to change the name of the set of flashcards? (4)"
                    + "I do not want to change anything. (0)");
            Scanner sc = new Scanner(System.in);
            int answer = Integer.parseInt(sc.toString());
            switch(answer) {
                case 1:
                    //TODO: Reuse code in Controller.Learno.createAFlashCard()? or move it?
                    /*while(temp){
                        System.out.println("What is the question? Enter it here: ");
                        String userQuestion = sc.nextLine();
                        sb.append(userQuestion).append(".");
                        System.out.println("What is the answer? Enter it here: ");
                        String userAnswer = sc.nextLine();
                        sb.append(userAnswer).append("\n");
                        System.out.println("Do you want to continue? (n)");
                        String ans = sc.nextLine();
                        if (ans.equals("n")) {
                            temp = false;
                        }
                    }*/
                    break;
                case 2:
                    System.out.println("Which question do you want to change? Write the number of the question.");
                    // TODO: Read the given rownumber+1 to see the question. + give option to change it or not.
                    break;
                case 3:
                    // TODO: Read the given rownumber+1 to see the answer. + give option to change it or not.
                    break;
                case 4:
                    // TODO: Read the first row of the set. + give option to change it or not.
                    break;
                case 0:
                    break;
                default:
                    System.out.println("You have to answer with 1, 2, 3 or 4");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

    }

    public void readFile(String name){
        System.out.println(name.toString());
    }
}