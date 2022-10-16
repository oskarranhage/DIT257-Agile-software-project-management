package Controller;
import Model.FileManager;
import Model.Set;
import Model.Card;
import Model.DataBase;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
        db.getSetHashMap().put("test multiple", new Set("test multiple", List.of(new Card("4", new String[]{"7-3", "3-1", "5-2", "3-2"}), new Card("3", new String[]{"5-2", "3-1", "7-3", "3-2"}), new Card("1", new String[]{"3-2", "3-1", "7-3", "5-1"})), Model.Set.setType.MultipleChoice));
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
        currentSetTextF.setText("Playing flashcards: " + curSetName);
        QnATextF.setText(db.getQuestion(curSetName,0));
        confirmButtonF.setText("Next");
    }

    /**
     * Switches to the previous flashcard unless the last is currently played
     */
    public void nextFlashCard(){
        if(!confirmButtonF.getText().equals("Submit")){
            triggerSpinCardAnimation();
            prevButtonF.setVisible(true);
            if (db.getSetSize(curSetName) -1 > atQuestionInd) {
                atQuestionInd += 1;
                QnATextF.setText(db.getQuestion(curSetName, atQuestionInd));
                atQuestion = true;
                if (db.getSetSize(curSetName) - 1 == atQuestionInd) {
                    confirmButtonF.setText("Submit");
                }
            }
        } else{
            openResultPage();
        }
    }

    /**
     * Triggers the flash card animation
     */
    private void triggerSpinCardAnimation(){
        FlashcardAnimation flashcardAnimation = new FlashcardAnimation(this, QnATextF.getText());
        tempFlashCard.getChildren().add(flashcardAnimation);
        flashcardAnimation.setLayoutX(511);
        flashcardAnimation.setLayoutY(355);
        flashcardAnimation.toFront();
        flashcardAnimation.nextFlashCard();
    }

    /**
     * Switches to the previous flashcard unless the first is currently played
     */
    public void previousFlashCard(){
        if (atQuestionInd != 0){
            confirmButtonF.setText("Next");
            atQuestionInd -= 1;
            QnATextF.setText(db.getQuestion(curSetName, atQuestionInd));
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
        if (atQuestion){
            QnATextF.setText(db.getAnswer(curSetName, atQuestionInd));
            atQuestion = false;
        } else{
            QnATextF.setText(db.getQuestion(curSetName, atQuestionInd));
            atQuestion = true;
        }
        triggerFlipCardAnimation();
    }

    private void triggerFlipCardAnimation(){
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
        CreateSetListItem item = new CreateSetListItem(this, ("C" + createItemID), curSetType);
        db.addCreateSetListItem(item);
        updateCreateSingleFlowPane(db.getCreateSetListItems());
        createItemID++;
    }

    /**
     * Updates the flow-pane by clearing it and then adding all items from the item list again (used when a new list-item is added)
     */
    public void updateCreateSingleFlowPane(List<CreateSetListItem> items){
        createSetFlowPane.getChildren().clear();
        for (CreateSetListItem item : items) {
            item.id = "CS" + createItemID;
            createItemID++;
            createSetFlowPane.getChildren().add(item);
        }
    }

    /**
     * Opens the create set page and adds 3 list items to the flow pane
     */
    public void openCreateSet(Set.setType type){
        db.clearCreateSetListItem();
        setNameC.setText("");
        createSetFlowPane.getChildren().clear();
        curSetType = type;
        switch (type){
            case MultipleChoice -> creatingEditingTitleText.setText("Creating multiple choices set");
            case Spelling -> creatingEditingTitleText.setText("Creating spelling set");
            case FlashCard -> creatingEditingTitleText.setText("Creating flashcards set");
        }
        createSetView.toFront();
        for (int i = 0; i < 3; i++) {addCreateSetItem();}
        updateCreateSingleFlowPane(db.getCreateSetListItems());
    }

    /**
     * Action event for save-button. Saves the set to a text file
     */
    public void saveCreatedSet(){
        if (!setNameC.getText().equals("")) {
            if(db.getSetHashMap().containsKey(setNameC.getText())) { // If set already exists
                System.out.println("Error : A set with that name already exists. Choose another name.");
            } else {
                List<Card> cards = new ArrayList<>();
                if (curSetType == Set.setType.FlashCard || curSetType == Set.setType.Spelling){
                    for (CreateSetListItem item : db.getCreateSetListItems()) {
                        cards.add(new Card(item.getTerm(), item.getDefinition()));
                    }
                } else if(curSetType == Set.setType.MultipleChoice) {
                    for (CreateSetListItem item : db.getCreateSetListItems()) {
                        cards.add(new Card(item.getTermMCS(), item.getDefinitionsMCS()));
                    }
                } else if (curSetType == Set.setType.Null){out.println("Error : setType is null.");}
                else{out.println("Error : Invalid setType.");}

                Set newSet = new Set(setNameC.getText(), cards, curSetType);

                try {
                    FileManager fm = new FileManager();
                    fm.saveSet(newSet);
                } catch (Exception e) {e.printStackTrace();}
                db.updateAll();
                setCreatedView.toFront();
                curSetName = setNameC.getText();
                createdSetName.setText(curSetName);
                createSetFlowPane.getChildren().clear();
                db.clearCreateSetListItem();
            }
        }
        else {System.out.println("Error : setNameC field is empty");} // If name is ""
    }


    /** ____________MULTIPLE CHOICES____________ */

    public void setUpMultiplePlay(){
        userAnswers.clear();
        resultFlowPane.getChildren().clear();
        curSetType = Set.setType.MultipleChoice;
        atQuestion = true;
        atQuestionInd = 0;
        currentSetTextF.setText(db.getSet(curSetName).getName());
        playingSetMultipleText.setText("Playing multiple choices: " + curSetName);
        setAnswers();
        questionMC.setText(db.getQuestion(curSetName,atQuestionInd));
        nextButtonTextMC.setText("Next");
    }

    /**
     * Randomizes the answers in a new array, the original array is still sorted
     * Changes the radio button-texts to the answers
     */
    public void setAnswers(){
        String[] answers = db.getAnswers(curSetName,atQuestionInd);

        List<String> answersShuffled = Arrays.asList(answers);
        Collections.shuffle(answersShuffled);

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
    }

    public void nextMultipleCard(){
        if(!nextButtonTextMC.getText().equals("Submit")) {
            if (db.getSetSize(curSetName) - 1 > atQuestionInd) {
                atQuestionInd += 1;
                questionMC.setText(db.getQuestion(curSetName,atQuestionInd));
                setAnswers();
                for (RadioButton rb : answerChoices) {
                    rb.setSelected(false);
                }
                if (db.getSetSize(curSetName) - 1 == atQuestionInd) {
                    nextButtonTextMC.setText("Submit");
                }
            }
            out.println(userAnswers.toString());
        } else{
            openResultPage();
        }
    }

    public void selectAnswer(RadioButton choice){
        for (RadioButton rb : answerChoices) {
            rb.setSelected(rb.getText().equals(choice.getText()));
        }
    }

    /**
     * Methods for action events where a button is pressed
     */
    public void selectFirstAnswer(){ selectAnswer(answer1); }
    public void selectSecondAnswer(){ selectAnswer(answer2); }
    public void selectThirdAnswer(){ selectAnswer(answer3); }
    public void selectFourthAnswer(){ selectAnswer(answer4); }

    /** ____________RESULT SCREEN____________ */
    public void presentResult(){
        int correct = 0;
        resultSetText.setText("Result of " + curSetName);
        for (int i = 0; i < userAnswers.size(); i++) {
            Card curCard = db.getCardAtIndex(curSetName,i);
            out.println(userAnswers.toString() + "PRINTING MESSAGE LMAO ");
            out.println(userAnswers.get(i) + " : " + userAnswers.size());
            resultFlowPane.getChildren().add(new ResultListItem(this, curCard, userAnswers.get(i)));
            if (curCard.getCorrectAnswer().equals(userAnswers.get(i))){
                correct++;
            }
        }
        if (userAnswers.size() > 0) {
            resultNumCorrect.setText("Correct answers: " + correct + "/" + userAnswers.size() + " (" + Math.round(correct * 100 / userAnswers.size())  + "%)");
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

    public void editSet(Model.Set set){
        setNameC.setText(set.getName());
        curSetType = set.getThisSetType();
        switch (curSetType){
            case Spelling -> creatingEditingTitleText.setText("Editing spelling: " + set.getName());
            case MultipleChoice -> creatingEditingTitleText.setText("Editing multiple choices: " + set.getName());
            case FlashCard -> creatingEditingTitleText.setText("Editing flashcard: " + set.getName());
        }
        db.getCreateSetListItems().clear();
        for (Card card : set.getCards()) {
            CreateSetListItem item = new CreateSetListItem(this, ("C" + createItemID), curSetType, card.getQuestion(), card.getAnswers());
            createItemID++;
            db.getCreateSetListItems().add(item);
        }
        createSetFlowPane.getChildren().clear();
        for (CreateSetListItem item : db.getCreateSetListItems()) {
            createSetFlowPane.getChildren().add(item);
        }
        createSetView.toFront();
    }

    public void editCurrentSet(){
        db.updateAll();
        editSet(db.getSet(curSetName));
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
    public void updateMySetsFlowPane(){
        mySetsFlowPane.getChildren().clear();
        ArrayList<Set> sets = new ArrayList<>(db.getSetHashMap().values());

        for (Set set : sets) {
            mySetsFlowPane.getChildren().add(new MySetsListItem(this, set));
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
        updateMySetsFlowPane();
        mySetsView.toFront(); }
    public void openChooseSet(){
        choooseSetView.toFront();
    }
    public void openIntroPage(){ introPage.toFront(); }

    public void openResultPage(){
        presentResult();
        resultPage.toFront();
    }
    public void openCreateSetFlashCards(){
        openCreateSet(Set.setType.FlashCard);
    }

    public void openCreateSetSpelling(){
        openCreateSet(Set.setType.Spelling);
    }

    public void openCreateSetMultiple(){
        openCreateSet(Set.setType.MultipleChoice);
    }
}



