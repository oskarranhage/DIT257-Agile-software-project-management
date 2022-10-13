package Controller;
import Model.FileManager;
import Model.Set;
import Model.Card;
import Model.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.*;

import static java.lang.System.out;

public class FXController implements Initializable {
    public DataBase db = new DataBase();
    private FileManager fm = new FileManager();

    /** Play flashcards variables */
    @FXML private AnchorPane playFlashcardView;
    @FXML private Text QnATextF;
    @FXML private Text currentSetTextF;
    @FXML private Button prevButtonF;
    @FXML private Button confirmButtonF;

    /** Play MultipleChoice variables */
    @FXML private AnchorPane playMultipleChoiceView;
    @FXML private RadioButton answer1;
    @FXML private RadioButton answer2;
    @FXML private RadioButton answer3;
    @FXML private RadioButton answer4;
    @FXML private Text questionMC;
    private List<RadioButton> answerChoices;
    @FXML private Text nextButtonTextMC;

    /** Start Menu variables */
    @FXML private AnchorPane startMenu;
    @FXML private AnchorPane introPage;

    /** Spelling variables */
    @FXML private TextField definitionAnswerS;
    @FXML private Text termS;
    @FXML private AnchorPane spellingView;

    /** Create set variables */
    @FXML private FlowPane createSetFlowPane;
    @FXML private TextField setNameC;
    @FXML private AnchorPane createSetView;
    @FXML private AnchorPane choooseSetView;
    @FXML private Text creatingEditingTitleText;
    @FXML private AnchorPane setCreatedView;
    @FXML private Text createdSetName;

    /** Create folder variables */
    @FXML private AnchorPane createFolderMenu;

    /** My foldes variables */
    @FXML private AnchorPane myFoldersView;

    /** My sets variables */
    @FXML private AnchorPane mySetsView;
    @FXML private GridPane mySetsGridPane;

    @FXML private ImageView bulbImage;

    /** Variables for action events */
    private static int atQuestionInd = 0;
    private static String curSetName = "";
    private boolean atQuestion;
    private List<String> userAnswers = new ArrayList<>();
    public int createItemID = 1;
    private Model.Set.setType creatingType = null;

    public FXController() {
        //bulbImage.setImage(new Image("C:/Programmering/DIT257-Agile-software-project-management/src/images/light-bulb.png"));
    }

    /**
     * Initialize is called at runtime, this is separate from the method start() in the main class Learno
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        answerChoices = List.of(answer1, answer2, answer3, answer4);
        openIntroPage();
        db.getSetHashMap().put("test spelling", new Set("test spelling", List.of(new Card("blue", "blå"), new Card("red", "röd"), new Card("green", "grön")), Model.Set.setType.Spelling));
        db.getSetHashMap().put("test multiple", new Set("test multiple", List.of(new Card("4", new String[]{"5-2", "3-1", "7-3", "3-2"}), new Card("3", new String[]{"5-2", "3-1", "7-3", "3-2"}), new Card("1", new String[]{"5-2", "3-1", "7-3", "3-2"})), Model.Set.setType.MultipleChoice));
    }


    /** ____________FLASHCARDS____________ */

    /**
     * Sets up flash card play. Used when opening the play flashcard page
     */
    public void setUpFlashcardPlay(){
        prevButtonF.setVisible(false);
        atQuestion = true;
        atQuestionInd = 0;
        currentSetTextF.setText(db.getSetHashMap().get(curSetName).getName());
        QnATextF.setText(db.getSet(curSetName).getCards().get(0).getQuestion());
    }

    /**
     * Switches to the previous flashcard unless the last is currently played
     */
    public void nextFlashCard(){
        prevButtonF.setVisible(true);
        if (db.getSet(curSetName).getCards().size()-1 != atQuestionInd){
            atQuestionInd += 1;
            QnATextF.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
            atQuestion = true;
        }
        if (db.getSet(curSetName).getCards().size()-1 == atQuestionInd){
            confirmButtonF.setText("Submit");
        }
    }

    /**
     * Switches to the previous flashcard unless the first is currently played
     */
    public void previousFlashCard(){
        if (atQuestionInd != 0){
            atQuestionInd -= 1;
            QnATextF.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
            atQuestion = true;
        }
        if (atQuestionInd < 1){
            prevButtonF.setVisible(false);
        }
    }

    // TODO Flashcards
    //  * If at card, grey out the "previous" button or make it not visible
    //  * If at last card, make this known visually to the user (maybe change the "next" button text)

    /**
     Reveals the answer or the question/term
     */
    public void turnCard(){
        out.println(atQuestion);
        if (atQuestion){
            out.println(db.getSet(curSetName).getCards().get(atQuestionInd).getAnswers()[0]);
            QnATextF.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getAnswers()[0]);
            atQuestion = false;
        } else{
            QnATextF.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
            atQuestion = true;
        }
    }

    /** ____________CREATE SET____________ */
    /**
     * Creates a list item and adds it to both a list of list items and to the flow pane by calling updateCreateSetFlowPane()
     */
    public void addCreateSetItem() {
        if (creatingType == Model.Set.setType.FlashCard){
            CreateSetSingleAnswer item = new CreateSetSingleAnswer(this, ("C" + createItemID));
            db.getCreateSingleListItems().add(item);
            updateCreateSingleFlowPane(db.getCreateSingleListItems());
        } else if (creatingType == Model.Set.setType.MultipleChoice) {
            CreateSetMultipleAnswer item = new CreateSetMultipleAnswer(this, ("C" + createItemID));
            db.getCreateMultipleListItems().add(item);
            updateCreateMultipleFlowPane(db.getCreateMultipleListItems());
        }
        createItemID++;
    }

    /**
     * Updates the flow-pane by clearing it and then adding all items from the item list again (used when a new list-item is added)
     */
    public void updateCreateSingleFlowPane(List<CreateSetSingleAnswer> items){
        createSetFlowPane.getChildren().clear();
        createItemID = 1;
        for (CreateSetSingleAnswer item : items) {
            item.id = "CS" + createItemID;
            createItemID++;
            createSetFlowPane.getChildren().add(item);
        }
    }

    public void updateCreateMultipleFlowPane(List<CreateSetMultipleAnswer> items){
        createSetFlowPane.getChildren().clear();
        createItemID = 1;
        for (CreateSetMultipleAnswer item : items) {
            item.id = "CM" + createItemID;
            createItemID++;
            createSetFlowPane.getChildren().add(item);
        }
    }

    /**
     * Opens the create set page and adds 4 list items to the flow pane
     */
    public void openCreateSetSingle(){
        createSetFlowPane.getChildren().clear();
        creatingType = Model.Set.setType.FlashCard;
        createSetView.toFront();
        if (createSetFlowPane.getChildren().size() == 0){
            for (int i = 0; i < 3; i++) {
                addCreateSetItem();
            }
        }
    }

    public void openCreateSetMultiple(){
        createSetFlowPane.getChildren().clear();
        creatingType = Model.Set.setType.MultipleChoice;
        createSetView.toFront();
        if (createSetFlowPane.getChildren().size() == 0){
            for (int i = 0; i < 3; i++) {
                addCreateSetItem();
            }
        }
    }

    /**
     * Action event for save-button. Saves the set to a text file
     */
    public void saveCreatedSet() {
        String type = fm.getFlashSetStringRepresentation();
        switch (creatingType){
            case FlashCard -> type = fm.getFlashSetStringRepresentation();
            case Spelling -> type = fm.getSpellingSetStringRepresentation();
            case MultipleChoice -> type = fm.getMultipleChoiceSetStringRepresentation();
        }
        if (!setNameC.getText().equals("")) {
            String name = setNameC.getText();
            StringBuilder sb = new StringBuilder();
            for (CreateSetSingleAnswer item : db.getCreateSingleListItems()) {
                sb.append("\n").append(item.getTerm()).append(".").append(item.getDefinition());
            }
            try {
                fm.createTextFile(type + name);
                fm.writeFile(type+name, sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            db.updateAll();
            // TODO set creation confirmation message to front
        } else{
            // TODO name error message to front
        }
        db.updateAll();
        curSetName = setNameC.getText();
        createdSetName.setText(curSetName);
        setCreatedView.toFront();
    }


    /** ____________MULTIPLE CHOICES____________ */

    public void setUpMultiplePlay(){
        curSetName = "test multiple";
        atQuestion = true;
        atQuestionInd = 0;
        currentSetTextF.setText(db.getSetHashMap().get(curSetName).getName());
        questionMC.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
        setAnswers();
    }

    /**
     * Randomizes the answers in a new array, the original array is still sorted
     * Changes the radio button-texts to the answers
     */
    public void setAnswers(){
        Random rand = new Random();
        String[] answers = db.getSet(curSetName).getCards().get(atQuestionInd).getAnswers();
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
                if (rb.getText().equals(db.getSet(curSetName).getCards().get(atQuestionInd).getAnswers()[0])) {
                    // TODO maybe show that it was the right answer
                } else {
                    // TODO maybe show that it was the right answer
                }
            }
        }
        if (hasAnswered){
            nextMultipleCard();
        } else{
            // TODO say that they need to answer the question before they can go to the next
        }
    }

    public void nextMultipleCard(){
        if (db.getSet(curSetName).getCards().size()-1 != atQuestionInd){
            atQuestionInd += 1;
            questionMC.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
            setAnswers();
            for (RadioButton rb : answerChoices) {
                rb.setSelected(false);
            }
            if (atQuestionInd == db.getSet(curSetName).getCards().size()-1){
                nextButtonTextMC.setText("Submit");
            }
        }
    }
    /**
     * -
     */
    public void presentResult(){
        int correct = 0;
        for (int i = 0; i < userAnswers.size(); i++) {
            if (db.getSet(curSetName).getCards().get(i).getAnswers().equals(userAnswers.get(i))){
                correct++;
                // TODO add a list item to result list flow pane that represents a correct answer
            } else{
                // TODO add a list item to result list flow pane that represents a wrong answer
            }
        }
        int wrong = db.getSet(curSetName).getCards().size() - correct;
        // setText(correct/db.getMultiSet(curSetName).getCards().size());
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

    /** ____________EDIT SET____________ */

    public void editSingleSet(Model.Set set){
        creatingEditingTitleText.setText("Editing " + set.getName());
        setNameC.setText(set.getName());
        creatingType = Model.Set.setType.FlashCard;
        createSetView.toFront();
        // button text to save
        db.getCreateSingleListItems().clear();
        for (Card card : set.getCards()) {
            CreateSetSingleAnswer item = new CreateSetSingleAnswer(this, ("C" + createItemID), card.getQuestion(), card.getAnswers()[0]);
            createItemID++;
            db.getCreateSingleListItems().add(item);
        }
        createSetFlowPane.getChildren().clear();
        for (CreateSetSingleAnswer item : db.getCreateSingleListItems()) {
            createSetFlowPane.getChildren().add(item);
        }
    }

    public void editCurrentSet(){
        db.updateAll();
        editSingleSet(db.getSet(curSetName));
        //editSingleSet(db.getMultiSet(curSetName));
        //editSingleSet(db.getSpellingSet(curSetName));
    }

    /** ____________SPELLING____________ */

    /**
     * Sets up Play Spelling when a set is chosen (getFlashSet will be replaced by getTextSet)
     */
    public void setUpSpellingPlay(){
        curSetName = "test spelling";
        atQuestionInd = 0;
        currentSetTextF.setText(curSetName);
        termS.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
    }

    /**
     * Switches to the previous Spelling unless the last is currently played
     */
    public void nextSpellingTerm(){
        if (db.getSet("test spelling").getCards().size()-1 != atQuestionInd){
            atQuestionInd += 1;
            termS.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
            userAnswers.add(definitionAnswerS.getText());
            definitionAnswerS.setText("");
        }
    }

    /**
     * Gets the correct answers of the set, the correct answer is always at index 0
     */
    public void viewResults(){
        for (int i = 0; i < userAnswers.size(); i++) {
            if (db.getSetHashMap().get(i).equals(userAnswers.get(i))){
                // TODO make wrong answers red
            }
        }
    }

    /** ____________MY SETS____________ */
    public void updateMySetsGridPane(Model.Set.setType type){
        mySetsGridPane.getChildren().clear();
        ArrayList<Model.Set> sets = new ArrayList<>();
        switch (type){
            case FlashCard -> sets.addAll(db.getSetHashMap().values());
            case MultipleChoice -> sets.addAll(db.getSetHashMap().values());
            case Spelling -> sets.addAll(db.getSetHashMap().values());
            case Null -> {
                sets.addAll(db.getSetHashMap().values());
                sets.addAll(db.getSetHashMap().values());
                sets.addAll(db.getSetHashMap().values());
            }
        }
        mySetsGridPane.getRowConstraints().add(new RowConstraints(3)); // column 0 is 100 wide
        mySetsGridPane.getColumnConstraints().add(new ColumnConstraints(200)); // column 1 is 200 wide
        for (int i = 0; i < mySetsGridPane.getColumnCount(); i++) {
            for (int j = 0; j < mySetsGridPane.getRowCount(); j++) {
                if (sets.size() > i+j) {
                    mySetsGridPane.add(new MySetsGridItem(this, sets.get(i + j)), j, i);
                }
            }
        }
    }

    public void updateMyFlashSetsGridPane(){
        updateMySetsGridPane(Model.Set.setType.FlashCard);
    }
    public void updateMySpellingSetsGridPane(){
        updateMySetsGridPane(Model.Set.setType.Spelling);
    }
    public void updateMyMulitSetsGridPane(){
        updateMySetsGridPane(Model.Set.setType.MultipleChoice);
    }
    public void updateAllMySetsGridPane(){
        updateMySetsGridPane(Model.Set.setType.Null);
    }

    /** -------------- Generalized methods -------------- */

    public void setCurSetName(String name){
        curSetName = name;
    }

    /** -------------- Methods to open pages -------------- */
    public void openPlayFlashcard(){
        setUpFlashcardPlay();
        playFlashcardView.toFront();
    }
    public void openPlaySpelling(){
        spellingView.toFront();
        setUpSpellingPlay();
    }
    public void openPlayMultipleChoice(){
        setUpMultiplePlay();
        playMultipleChoiceView.toFront();
    }
    public void openStartMenu(){ startMenu.toFront(); }
    public void openCreateFolder(){ createFolderMenu.toFront(); }
    public void openMyFolders(){ myFoldersView.toFront(); }
    public void openMySets(){
        updateAllMySetsGridPane();
        mySetsView.toFront(); }
    public void openChooseSet(){ choooseSetView.toFront(); }
    public void openIntroPage(){ introPage.toFront(); }
}