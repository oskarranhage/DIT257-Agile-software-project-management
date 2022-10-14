package Controller;
import Model.FileManager;
import Model.Set;
import Model.Card;
import Model.DataBase;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import static java.lang.System.out;

public class FXController implements Initializable {
    public DataBase db = new DataBase();

    /** Play flashcards variables */
    @FXML private AnchorPane playFlashcardView;
    @FXML private Text QnATextF;
    @FXML private Text currentSetTextF;
    @FXML private Button prevButtonF;
    @FXML private Button confirmButtonF;
    @FXML private AnchorPane tempFlashCard;
    @FXML private Pane flashCardBack;

    /** Play MultipleChoice variables */
    @FXML private AnchorPane playMultipleChoiceView;
    @FXML private RadioButton answer1;
    @FXML private RadioButton answer2;
    @FXML private RadioButton answer3;
    @FXML private RadioButton answer4;
    @FXML private Text questionMC;
    private List<RadioButton> answerChoices;
    @FXML private Text nextButtonTextMC;
    @FXML private Text playingSetMultipleText;

    /** Start Menu variables */
    @FXML private AnchorPane startMenu;
    @FXML private AnchorPane introPage;

    /** Spelling variables */
    @FXML private TextField definitionAnswerS;
    @FXML private Text termS;
    @FXML private AnchorPane spellingView;
    @FXML private Text playingSetTextSpelling;
    @FXML private Text nextButtonTextS;

    /** Create set variables */
    @FXML private FlowPane createSetFlowPane;
    @FXML private TextField setNameC;
    @FXML private AnchorPane createSetView;
    @FXML private AnchorPane choooseSetView;
    @FXML private Text creatingEditingTitleText;
    @FXML private AnchorPane setCreatedView;
    @FXML private Text createdSetName;

    /** Result variables */
    @FXML private Text resultSetText;
    @FXML private Text resultPercentage;
    @FXML private Text resultNumCorrect;
    @FXML private FlowPane resultFlowPane;
    @FXML private AnchorPane resultPage;

    /** Create folder variables */
    @FXML private AnchorPane createFolderMenu;

    /** My foldes variables */
    @FXML private AnchorPane myFoldersView;

    /** My sets variables */
    @FXML private AnchorPane mySetsView;
    @FXML private FlowPane mySetsFlowPane;

    /** Variables for action events */
    private static int atQuestionInd = 0;
    private static String curSetName = "";
    private boolean atQuestion;
    private List<String> userAnswers = new ArrayList<>();
    public int createItemID = 1;
    private Model.Set.setType curSetType = null;

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
        curSetType = Set.setType.FlashCard;
        prevButtonF.setVisible(false);
        atQuestion = true;
        atQuestionInd = 0;
        out.println(curSetName);
        currentSetTextF.setText("Playing flashcards: " + db.getSet(curSetName).getName());
        QnATextF.setText(db.getSet(curSetName).getCards().get(0).getQuestion());
        confirmButtonF.setText("Next");
    }

    /**
     * Switches to the previous flashcard unless the last is currently played
     */
    public void nextFlashCard(){
        if(!confirmButtonF.getText().equals("Submit")){
            FlashcardListItem flashcardListItem = new FlashcardListItem(this, db.getSet(curSetName).getCards().get(atQuestionInd).getAnswers()[0]);
            tempFlashCard.getChildren().add(flashcardListItem);
            flashcardListItem.setLayoutX(511);
            flashcardListItem.setLayoutY(355);
            flashcardListItem.toFront();
            flashcardListItem.nextFlashCard();

            prevButtonF.setVisible(true);
            if (db.getSet(curSetName).getCards().size()-1 > atQuestionInd) {
                atQuestionInd += 1;
                QnATextF.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
                atQuestion = true;
                if (db.getSet(curSetName).getCards().size() - 1 == atQuestionInd) {
                    confirmButtonF.setText("Submit");
                }
            }
        } else{
            userAnswers.add(definitionAnswerS.getText());
            openResultPage();
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

        ScaleTransition stShowBack = new ScaleTransition(Duration.millis(1000), flashCardBack);
        stShowBack.setFromX(0);
        stShowBack.setToX(1);
        stShowBack.play();
    }

    /** ____________CREATE SET____________ */
    /**
     * Creates a list item and adds it to both a list of list items and to the flow pane by calling updateCreateSetFlowPane()
     */
    public void addCreateSetItem() {
        if (curSetType == Model.Set.setType.FlashCard || curSetType == Set.setType.Spelling){
            SingleAnswerListItem item = new SingleAnswerListItem(this, ("C" + createItemID));
            db.getCreateSingleListItems().add(item);
            updateCreateSingleFlowPane(db.getCreateSingleListItems());
        } else if (curSetType == Model.Set.setType.MultipleChoice) {
            MultipleAnswerListItem item = new MultipleAnswerListItem(this, ("C" + createItemID));
            db.getCreateMultipleListItems().add(item);
            updateCreateMultipleFlowPane(db.getCreateMultipleListItems());
        }
        createItemID++;
    }

    /**
     * Updates the flow-pane by clearing it and then adding all items from the item list again (used when a new list-item is added)
     */
    public void updateCreateSingleFlowPane(List<SingleAnswerListItem> items){
        createSetFlowPane.getChildren().clear();
        for (SingleAnswerListItem item : items) {
            item.id = "CS" + createItemID;
            createItemID++;
            createSetFlowPane.getChildren().add(item);
        }
    }

    public void updateCreateMultipleFlowPane(List<MultipleAnswerListItem> items){
        createSetFlowPane.getChildren().clear();
        for (MultipleAnswerListItem item : items) {
            item.id = "CM" + createItemID;
            createItemID++;
            createSetFlowPane.getChildren().add(item);
        }
    }

    /**
     * Opens the create set page and adds 4 list items to the flow pane
     */

    public void openCreateSetFlashCards(){
        creatingEditingTitleText.setText("Creating flashcards set");
        curSetType = Model.Set.setType.FlashCard;
        openCreateSetSingle();
    }

    public void openCreateSetSpelling(){
        creatingEditingTitleText.setText("Creating spelling set");
        curSetType = Set.setType.Spelling;
        openCreateSetSingle();
    }

    public void openCreateSetSingle(){
        createSetFlowPane.getChildren().clear();
        createSetView.toFront();
        if (db.getCreateSingleListItems().size() < 3) {
            for (int i = 0; i < 3-db.getCreateSingleListItems().size(); i++) {
                addCreateSetItem();
            }
        }
        updateCreateSingleFlowPane(db.getCreateSingleListItems());
    }

    public void openCreateSetMultiple(){
        creatingEditingTitleText.setText("Creating multiple choices set");
        createSetFlowPane.getChildren().clear();
        curSetType = Model.Set.setType.MultipleChoice;
        createSetView.toFront();
        if (db.getCreateSingleListItems().size() == 0) {
            for (int i = 0; i < 3; i++) {
                addCreateSetItem();
            }
        }
    }

    /**
     * Action event for save-button. Saves the set to a text file
     */
    public void saveCreatedSet(){
        if (!setNameC.getText().equals("")) {
            List<Card> cards = new ArrayList<>();
            for (SingleAnswerListItem item : db.getCreateSingleListItems()) {
                cards.add(new Card(item.getTerm(), item.getDefinition()));
            }
            Set newSet = new Set(setNameC.getText(), cards, curSetType);
            try {
                FileManager fm = new FileManager();
                fm.saveSet(newSet);
            } catch (Exception e) {e.printStackTrace();}
            db.updateAll();
        }
        curSetName = setNameC.getText();
        createdSetName.setText(curSetName);
        setCreatedView.toFront();
        createSetFlowPane.getChildren().clear();
        db.getCreateSingleListItems().clear();
    }


    /** ____________MULTIPLE CHOICES____________ */

    public void setUpMultiplePlay(){
        curSetType = Set.setType.MultipleChoice;
        curSetName = "test multiple";
        atQuestion = true;
        atQuestionInd = 0;
        currentSetTextF.setText(db.getSet(curSetName).getName());
        playingSetMultipleText.setText("Playing multiple choices: " + curSetName);
        setAnswers();
        questionMC.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
        nextButtonTextMC.setText("Next");
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
        for (RadioButton rb : answerChoices) {
            if (rb.isSelected()) {
                userAnswers.add(rb.getText());
                nextMultipleCard();
                break;
            }
        }
        out.println(userAnswers.toString());
    }

    public void nextMultipleCard(){
        if(!nextButtonTextMC.getText().equals("Submit")) {
            if (db.getSet(curSetName).getCards().size() - 1 > atQuestionInd) {
                atQuestionInd += 1;
                questionMC.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
                setAnswers();
                for (RadioButton rb : answerChoices) {
                    rb.setSelected(false);
                }
                if (db.getSet(curSetName).getCards().size() - 1 == atQuestionInd) {
                    nextButtonTextMC.setText("Submit");
                }
            }
        } else{
            openResultPage();
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
     * RESULT SCREEN
     */
    public void presentResult(){
        int correct = 0;
        resultSetText.setText("Result of " + curSetName);
        for (int i = 0; i < userAnswers.size(); i++) {
            Card curCard = db.getSet(curSetName).getCards().get(i);
            resultFlowPane.getChildren().add(new ResultSingleListItem(this, curCard, userAnswers.get(i)));
            if (curCard.getCorrectAnswer().equals(userAnswers.get(i))){
                correct++;
            }
        }
        if (userAnswers.size() > 0) {
            resultNumCorrect.setText("Correct answers: " + correct + "/" + userAnswers.size() + " (" + ((correct / userAnswers.size()) * 100) + "%)");
        }
    }

    public void playAgain(){
        switch (curSetType){
            case Spelling -> openPlaySpelling();
            case MultipleChoice -> openPlayMultipleChoice();
            case FlashCard -> openPlayFlashcard();
        }
    }

    /** ____________EDIT SET____________ */

    public void editSingleSet(Model.Set set){
        setNameC.setText(set.getName());
        curSetType = set.getThisSetType();
        switch (curSetType){
            case Spelling -> creatingEditingTitleText.setText("Editing spelling: " + set.getName());
            case MultipleChoice -> creatingEditingTitleText.setText("Editing multiple choices: " + set.getName());
            case FlashCard -> creatingEditingTitleText.setText("Editing flashcard: " + set.getName());
        }
        createSetView.toFront();
        db.getCreateSingleListItems().clear();
        for (Card card : set.getCards()) {
            SingleAnswerListItem item = new SingleAnswerListItem(this, ("C" + createItemID), card.getQuestion(), card.getAnswers()[0]);
            createItemID++;
            db.getCreateSingleListItems().add(item);
        }
        createSetFlowPane.getChildren().clear();
        for (SingleAnswerListItem item : db.getCreateSingleListItems()) {
            createSetFlowPane.getChildren().add(item);
        }
    }

    public void editCurrentSet(){
        db.updateAll();
        editSingleSet(db.getSet(curSetName));
    }

    /** ____________SPELLING____________ */

    /**
     * Sets up Play Spelling when a set is chosen (getFlashSet will be replaced by getTextSet)
     */
    public void setUpSpellingPlay(){
        curSetType = Set.setType.Spelling;
        atQuestionInd = 0;
        currentSetTextF.setText(curSetName);
        playingSetTextSpelling.setText("Playing spelling: " + curSetName);
        termS.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
        definitionAnswerS.clear();
        nextButtonTextS.setText("Next");
    }

    /**
     * Switches to the next Spelling unless the last is currently played
     */
    public void nextSpellingTerm(){
        if(!nextButtonTextS.getText().equals("Submit")) {
            if (db.getSet(curSetName).getCards().size() - 1 > atQuestionInd) {
                atQuestionInd += 1;
                termS.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
                userAnswers.add(definitionAnswerS.getText());
                definitionAnswerS.setText("");
                if (db.getSet(curSetName).getCards().size() - 1 == atQuestionInd) {
                    nextButtonTextS.setText("Submit");
                }
            }
        } else {
            userAnswers.add(definitionAnswerS.getText());
            openResultPage();
        }
    }

    /** ____________MY SETS____________ */
    public void updateMySetsGridPane(){
        mySetsFlowPane.getChildren().clear();
        ArrayList<Set> sets = new ArrayList<>(db.getSetHashMap().values());

        for (Set set : sets) {
            mySetsFlowPane.getChildren().add(new MySetsGridItem(this, set));
        }
    }

    /** -------------- General methods -------------- */

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

    public void openMySets(){
        updateMySetsGridPane();
        mySetsView.toFront(); }
    public void openChooseSet(){
        choooseSetView.toFront();
    }
    public void openIntroPage(){ introPage.toFront(); }

    public void openResultPage(){
        presentResult();
        resultPage.toFront();
    }
}