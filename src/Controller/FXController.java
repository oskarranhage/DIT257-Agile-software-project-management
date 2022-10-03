package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FXController implements Initializable {
    public DataBase db = new DataBase();
    /**
     * Play flashcards
     * The F at the end of each variable is short for "Flashcard"
     */
    @FXML private AnchorPane playFlashcardView;
    @FXML private Text QnATextF;
    @FXML private Text instructionF;
    @FXML private Text currentSetTextF;

    /**
     * Play MultipleChoice
     * The MC at the end of each variable is short for "MultipleChoice"
     */

    @FXML private AnchorPane playMultipleChoiceView;
    @FXML private RadioButton answer1;
    @FXML private RadioButton answer2;
    @FXML private RadioButton answer3;
    @FXML private RadioButton answer4;
    private List<RadioButton> answerChoices;

    @FXML private AnchorPane startMenu;

    @FXML private FlowPane createSetFlowPane;
    @FXML private TextField setNameC;

    @FXML private TextField definitionAnswerS;
    @FXML private Text termS;

    @FXML private AnchorPane createFolderMenu;
    @FXML private AnchorPane createSetView;
    @FXML private AnchorPane choooseSetView;
    @FXML private AnchorPane myFoldersView;
    @FXML private AnchorPane mySetsView;
    @FXML private AnchorPane spellingView;

    private static int atQuestionInd = 0;
    private static String curSetName = "";
    private boolean atQuestion;
    private List<String> userAnswers = new ArrayList<>();
    public int createItemID = 1;

    public FXController() {
    }

    /**
     * Initialize is run at runtime, this is separate from the method start() in the main class Learno
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        answerChoices = List.of(answer1, answer2, answer3, answer4);
        openStartMenu();
    }


    /** ____________FLASHCARDS____________ */

    /**
     * Sets up flash card play. Used when opening the play flashcard page
     */
    public void setUpFlashcardPlay(){
        curSetName = "English Set 1";
        atQuestion = true;
        atQuestionInd = 0;
        //currentSetTextF.setText("You are running " + db.getFlashSets().get(curSetName).name + " as a flash set.");
        QnATextF.setText(db.getFlashSet(curSetName).getCards().get(3).getQuestion());
    }

    /**
     * Switches to the previous flashcard unless the last is currently played
     */
    public void nextFlashCard(){
        if (db.getFlashSet(curSetName).getCards().size()-1 != atQuestionInd){
            atQuestionInd += 1;
            QnATextF.setText(db.getFlashSet(curSetName).getCards().get(atQuestionInd).getQuestion());
        }
        atQuestion = true;
    }

    /**
     * Switches to the previous flashcard unless the first is currently played
     */
    public void previousFlashCard(){
        if (atQuestionInd != 0){
            atQuestionInd -= 1;
            QnATextF.setText(db.getFlashSet(curSetName).getCards().get(atQuestionInd).getQuestion());
        }
    }

    // TODO Flashcards
    //  * If at card, grey out the "previous" button or make it not visible
    //  * If at last card, make this known visually to the user (maybe change the "next" button text)

    /**
      Reveals the answer or the question/term
     */
    public void turnCard(){
        if (atQuestion){
            QnATextF.setText(db.getFlashSet(curSetName).getCards().get(atQuestionInd).getAnswers()[0]);
            atQuestion = false;
        } else{
            QnATextF.setText(db.getFlashSet(curSetName).getCards().get(atQuestionInd).getQuestion());
            atQuestion = true;
        }
    }

    /** ____________CREATE SET____________ */
    /**
     * Creates a list item and adds it to both a list of list items and to the flow pane by calling updateCreateSetFlowPane()
     */
    public void addCreateSetItem() {
        CreateSetListItem item = new CreateSetListItem(this, ("C" + createItemID));
        createItemID++;
        db.getCreateSetListItems().add(item);
        //out.println(db.getCreateSetItems().size() + "in flow");
        updateCreateSetFlowPane(db.getCreateSetListItems());
        //out.println(createSetFlowPane.getChildren().size() + "in list");
    }

    /**
     * Updates the flow-pane by clearing it and then adding all items from the item list again (used when a new list-item is added)
     */
    public void updateCreateSetFlowPane(List<CreateSetListItem> items){
        createSetFlowPane.getChildren().clear();
        createItemID = 1;
        for (CreateSetListItem item : items) {
            item.id = "C" + createItemID;
            createItemID++;
            createSetFlowPane.getChildren().add(item);
        }
    }

    /**
     * Opens the create set page and adds 5 list items to the flow pane
     */
    public void openCreateSet() throws IOException {
        createSetView.toFront();
        addCreateSetItem(); addCreateSetItem(); addCreateSetItem(); addCreateSetItem();
    }

    /**
     * Action event for save-button. Saves the set to a text file
     */
    public void saveCreatedSet(){
        String type = "fc.";
        if (!setNameC.getText().equals("")) {
            String name = setNameC.getText();
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            for (CreateSetListItem item : db.getCreateSetListItems()) {
                sb.append("\n").append(item.getTerm()).append(".").append(item.getDefinition());
            }
            FileManager.makeFile(type + name, sb.toString());
            db.updateAll();
            // TODO set creation confirmation message to front
        } else{
            // TODO name error message to front
        }
    }


    /** ____________MULTIPLE CHOICES____________ */

    /**
     * Randomizes the answers in a new array, the original array is still sorted
     * Changes the radio button-texts to the answers
     */
    public void setAnswers(){
        Random rand = new Random();
        String[] answers = db.getMultiSet(curSetName).getCards().get(atQuestionInd).getAnswers();
        for (int i = 3; i > 0; i--) {
            int j = rand.nextInt(i+1);
            String temp = answers[i];
            answers[i] = answers[j];
            answers[j] = temp;
        }
        for (int i = 0; i < answerChoices.size(); i++) {
            answerChoices.get(i).setText(answers[i]);
        }
    }

    /**
     * Checks whether the answer chosen by the user is right or wrong
     */
    public void confirmAnswer(){
        boolean hasAnswered = false;
        for (RadioButton rb : answerChoices) {
            if (rb.isSelected()) {
                hasAnswered = true;
                userAnswers.add(rb.getText());
                if (rb.getText().equals(db.getMultiSet(curSetName).getCards().get(atQuestionInd).getAnswers()[0])) {
                    // TODO maybe show that it was the right answer
                } else {
                    // TODO maybe show that it was the right answer
                }
            }
        }
        if (hasAnswered){
            // TODO go to next card
        } else{
            // TODO say that they need to answer the question before they can go to the next
        }
    }

    /**
     * -
     */
    public void presentResult(){
        for (int i = 0; i < userAnswers.size(); i++) {
            if (db.getMultiSet(curSetName).getCards().get(i).getAnswers().equals(userAnswers.get(i))){
                // TODO add a list item to result list flow pane that represents a correct answer
            } else{
                // TODO add a list item to result list flow pane that represents a wrong answer
            }
        }
    }

    public void selectAnswer(RadioButton choice){
        for (RadioButton rb : answerChoices) {
            rb.setSelected(rb.getText().equals(choice.getText()));
        }
    }

    public void selectFirstAnswer(){ selectAnswer(answer1); }
    public void selectSecondAnswer(){ selectAnswer(answer2); }
    public void selectThirdAnswer(){ selectAnswer(answer3); }
    public void selectFourthAnswer(){ selectAnswer(answer4); }

    /**
     * Sets the current set text to the current set, for example "set test2"
     * @param s     The set name
     */
    public void setCurrentSetMC(String s){ currentSetTextF.setText("You are running " + s + " as a multiple choice set."); }


    /** ____________SPELLING____________ */

    /**
     * Sets up Play Spelling when a set is chosen (getFlashSet will be replaced by getTextSet)
     */
    public void setUpSpellingPlay(){
        atQuestionInd = 0;
        currentSetTextF.setText("You are running " + curSetName + " as a spelling set.");
        termS.setText(db.getSpellingSet(curSetName).getCards().get(atQuestionInd).getQuestion());
    }

    /**
     * Switches to the previous Spelling unless the last is currently played
     */
    public void nextSpellingTerm(){
        if (1 != atQuestionInd){
            atQuestionInd += 1;
            termS.setText(db.getSpellingSet(curSetName).getCards().get(atQuestionInd).getQuestion());
            userAnswers.add(definitionAnswerS.getText());
            definitionAnswerS.setText("");
        }
    }

    /**
     * Checks whether the user input is the right answer
     */
    public boolean isCorrectSpelling(){
        return definitionAnswerS.getText().equals(termS.getText());
    }

    /**
     * Gets the correct answers of the set, the correct answer is always at index 0
     */
    public void viewResults(){
        for (int i = 0; i < userAnswers.size(); i++) {
            if (db.getFlashSets().get(i).equals(userAnswers.get(i))){
                // TODO make wrong answers red
            }
        }
    }

    /*
     * Switches to the previous Spelling unless the first is currently played (unsure if needed)
    public void previousSpelling(){
        if (atQuestionInd != 0){
            atQuestionInd -= 1;
            QnATextF.setText(db.getTextSets().get(curSetInd).getCards().get(atQuestionInd).getQuestion());
        }
    }
    */

    /** -------------- Generalized methods -------------- */


    /** -------------- Methods to open pages -------------- */
    public void openPlayFlashcard(){
        setUpFlashcardPlay();
        playFlashcardView.toFront();
    }
    public void openPlaySpelling(){
        spellingView.toFront();
        setUpSpellingPlay();
    }
    public void openPlayMultipleChoice(){ playMultipleChoiceView.toFront(); }
    public void openStartMenu(){ startMenu.toFront(); }
    public void openCreateFolder(){ createFolderMenu.toFront(); }
    public void openMyFolders(){ myFoldersView.toFront(); }
    public void openMySets(){ mySetsView.toFront(); }
    public void openChooseSet(){ choooseSetView.toFront(); }
}
