import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class FXController implements Initializable {
    private DataBase db = new DataBase();
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
    @FXML private Button backButtonMC;
    @FXML private Button nextButtonMC;
    @FXML private Button previousButtonMC;
    @FXML private Text questionMC;
    @FXML private RadioButton answer1;
    @FXML private RadioButton answer2;
    @FXML private RadioButton answer3;
    @FXML private RadioButton answer4;
    @FXML private Text answer1Text;
    @FXML private Text answer2Text;
    @FXML private Text answer3Text;
    @FXML private Text answer4Text;
    //@FXML private List<Text> answerTexts = List.of(answer1Text, answer2Text, answer3Text, answer4Text);
    //private List<RadioButton> answerChoices; { assert false; answerChoices = List.of(answer1, answer2, answer3, answer4); }

    @FXML private Text confirmAnswerMC;
    @FXML private Text guessResultMC;

    @FXML private AnchorPane startMenu;

    @FXML private FlowPane createSetFlowPane;
    @FXML private TextField setNameC;

    @FXML private TextField definitionAnswerS;
    @FXML private Text curSetS;
    @FXML private Text termS;

    @FXML private AnchorPane createFolderMenu;
    @FXML private AnchorPane createSetView;
    @FXML private AnchorPane choooseSetView;
    @FXML private AnchorPane myFoldersView;
    @FXML private AnchorPane mySetsView;
    @FXML private AnchorPane spellingView;

    private static int atQuestionInd = 0;
    private static int curSetInd = 0;
    private boolean atQuestion;
    private List<Integer> correctAnswersInd = new ArrayList<>();

    public FXController() throws IOException {
    }

    /**
     * Initialize is run at runtime, this is separate from the method start() in the main class Learno
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        openStartMenu();
    }


    /** ____________FLASHCARDS____________ */

    /**
     * Sets up flash card play. Used when opening the play flashcard page
     */
    public void setUpFlashcardPlay(){
        atQuestion = true;
        atQuestionInd = 0;
        currentSetTextF.setText("You are running " + db.getFlashSets().get(0).name + " as a flash set.");
        QnATextF.setText(db.getFlashSets().get(0).getCards().get(atQuestionInd).getQuestion());
    }

    /**
     * Switches to the previous flashcard unless the last is currently played
     */
    public void nextFlashCard(){
        if (db.getFlashSets().get(0).getCards().size()-1 != atQuestionInd){
            atQuestionInd += 1;
            QnATextF.setText(db.getFlashSets().get(0).getCards().get(atQuestionInd).getQuestion());
        }
    }

    /**
     * Switches to the previous flashcard unless the first is currently played
     */
    public void previousFlashCard(){
        if (atQuestionInd != 0){
            atQuestionInd -= 1;
            QnATextF.setText(db.getFlashSets().get(curSetInd).getCards().get(atQuestionInd).getQuestion());
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
            QnATextF.setText(db.getFlashSets().get(curSetInd).getCards().get(atQuestionInd).getAnswer());
            atQuestion = false;
        } else{
            QnATextF.setText(db.getFlashSets().get(curSetInd).getCards().get(atQuestionInd).getQuestion());
            atQuestion = true;
        }
    }

    /** ____________CREATE SET____________ */
    /**
     * Creates a list item and adds it to both a list of list items and to the flow pane by calling updateCreateSetFlowPane()
     */
    public void addCreateSetItem() throws IOException {
        CreateSetListItem item = new CreateSetListItem(this, db.getCreateSetItems().size());
        db.getCreateSetItems().add(item);
        //out.println(db.getCreateSetItems().size() + "in flow");
        updateCreateSetFlowPane(db.getCreateSetItems());
        //out.println(createSetFlowPane.getChildren().size() + "in list");
    }

    /**
     * Updates the flow-pane by clearing it and then adding all items from the item list again (used when a new list-item is added)
     */
    public void updateCreateSetFlowPane(List<CreateSetListItem> items){
        createSetFlowPane.getChildren().clear();
        for (CreateSetListItem item : items) {
            createSetFlowPane.getChildren().add(new CreateSetListItem(this, 2));
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
        if (!setNameC.getText().equals("")) {
            String name = setNameC.getText();
            StringBuilder sb = new StringBuilder();
            for (CreateSetListItem item : db.getCreateSetItems()) {
                sb.append(name).append("\n").append(item.getTerm()).append(".").append(item.getDefinition()).append("\n");
            }
            FileManager.makeFile(name, sb.toString());
            // TODO set creation confirmation message to front
        } else{
            // TODO name error message to front
        }
    }

    /** ____________MULTIPLE CHOICES____________ */

    /**
     * Changes the radio button-texts to the answers, in case use the text contained inside the radio buttons
     * @param answers       List of the current cards answers
     */
    public void setAnswers(List<String> answers){
        for (int i = 0; i < answers.size(); i++) {
            //answerChoices.get(i).setSelected(false);
            //answerChoices.get(i).setText(answers.get(i));
        }
    }

    /**
     * Changes the choice-texts to the answers, in case we want to remove the text of radio buttons and add seperate text for better design
     * @param answers       List of the current cards answers
     */
    public void setAnswerTexts(List<String> answers){
        List<Text> answerTexts = List.of(answer1Text, answer2Text, answer3Text, answer4Text);
        for (int i = 0; i < answers.size(); i++) {
            answerTexts.get(i).setText(answers.get(i));
        }
    }

    /**
     * Checks whether the answer chosen by the user is right or wrong
     * @param choice        The users choice
     * @param rightAnswer   The right answer
     */
    public void confirmAnswer(RadioButton choice, String rightAnswer){
        if (choice.getText().equals(rightAnswer)){
            guessResultMC.setText("Right answer");
        } else{
            guessResultMC.setText("Wrong answer");
        }
        guessResultMC.toFront();
    }

    /*public void selectAnswer(RadioButton choice){
        for (RadioButton rb : answerChoices) {
            rb.setSelected(rb.getText().equals(choice.getText()));
        }
    }*/

    /*public void selectFirstAnswer(){ selectAnswer(answer1); }
    public void selectSecondAnswer(){ selectAnswer(answer2); }
    public void selectThirdAnswer(){ selectAnswer(answer3); }
    public void selectFourthAnswer(){ selectAnswer(answer4); }*/

    /**
     * Sets the current set text to the current set, for example "set test2"
     * @param s     The set name
     */
    public void setCurrentSetMC(String s){ currentSetTextF.setText("You are running " + s + " as a multiple choice set."); }


    /** ____________SPELLING____________ */

    /**
     * Sets up Play Spelling when a set is chosen
     */
    public void setUpSpellingPlay(){
        atQuestionInd = 0;
        correctAnswersInd.clear();
        currentSetTextF.setText("You are running " + db.getTextSets().get(curSetInd).name + " as a spelling set.");
        QnATextF.setText(db.getTextSets().get(curSetInd).getCards().get(atQuestionInd).getQuestion());
    }

    /**
     * Switches to the previous Spelling unless the last is currently played
     */
    public void nextSpellingTerm(){
        if (db.getTextSets().get(curSetInd).getCards().size()-1 != atQuestionInd){
            atQuestionInd += 1;
            QnATextF.setText(db.getTextSets().get(curSetInd).getCards().get(atQuestionInd).getQuestion());
        }
        if (isCorrectSpelling()){
            correctAnswersInd.add(atQuestionInd);
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
    public List<Card> getCorrectAnswers(){
        List<Card> correctAnswers = new ArrayList<>();
        for (int i = 0; i < db.getTextSets().get(curSetInd).getCards().size(); i++) {
            if (correctAnswersInd.contains(i)){
                correctAnswers.add(db.getTextSets().get(curSetInd).getCards().get(i));
            }
        }
        return correctAnswers;
    }

    /**
     * Gets the wrong answers of the set, the wrong answer is always at index i>0
     */
    public List<Card> getWrongAnswers(){
        List<Card> wrongAnswers = new ArrayList<>();
        for (int i = 0; i < db.getTextSets().get(curSetInd).getCards().size(); i++) {
            if (!correctAnswersInd.contains(i)){
                wrongAnswers.add(db.getTextSets().get(curSetInd).getCards().get(i));
            }
        }
        return wrongAnswers;
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
