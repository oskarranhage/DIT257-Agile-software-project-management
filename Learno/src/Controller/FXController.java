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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.List;

import static java.lang.System.out;

/**
 * FXController manages the visual presentation of the program.
 */
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
    @FXML private ScrollPane createSetScrollPane;

    /** Result variables */
    @FXML private Text resultSetText;
    @FXML private Text resultPercentage;
    @FXML private Text resultNumCorrect;
    @FXML private FlowPane resultFlowPane;
    @FXML private AnchorPane resultPage;
    @FXML private ScrollPane resultsScrollPane;

    /** Create folder variables */
    @FXML private AnchorPane createFolderMenu;

    /** My foldes variables */
    @FXML private AnchorPane myFoldersView;

    /** My sets variables */
    @FXML private AnchorPane mySetsView;
    @FXML private FlowPane mySetsFlowPane;
    @FXML private ScrollPane mySetsScrollPane;

    /** Variables for action events */
    private static int atQuestionInd = 0;
    private static String curSetName = "";
    private boolean atQuestion;
    private List<String> userAnswers = new ArrayList<>();
    public int createItemID = 1;
    private Model.Set.setType curSetType = null;

    /**
     * Initialize is called at runtime, this is separate from the method start() in the main class Learno
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        answerChoices = List.of(answer1, answer2, answer3, answer4);
        openIntroPage();
    }


    /** ____________GENERAL METHODS_____________*/

    /**
     * Method for setting up the different play menus. Uses a switch implementation to do different things
     * depending on the current setType.
     * @param setType
     */
    public void setUpPlay(Set.setType setType) {
        curSetType = setType;
        atQuestionInd = 0;
        userAnswers.clear();
        resultFlowPane.getChildren().clear();

        switch (curSetType) {
            case FlashCard:
                prevButtonF.setVisible(false);
                atQuestion = true;
                QnATextF.setText(db.getQuestion(curSetName,0));
                currentSetTextF.setText("Playing flashcards: " + curSetName);
                confirmButtonF.setText("Next");
                break;
            case Spelling:
                termS.setText(db.getQuestion(curSetName, atQuestionInd));
                definitionAnswerS.clear();
                playingSetTextSpelling.setText("Playing spelling: " + curSetName);
                nextButtonTextS.setText("Next");
                break;
            case MultipleChoice:
                setAnswers();
                questionMC.setText(db.getQuestion(curSetName,atQuestionInd));
                playingSetMultipleText.setText("Playing multiple choices: " + curSetName);
                nextButtonTextMC.setText("Next");
        }
    }

    /**
     * Method for displaying the next card when playing a set. Uses a switch implementation to do different things
     * depending on the current set type.
     * @param setType The type of the set that is being played.
     * @param nextButton The text object of the nextButton. This is used to generalize the code.
     */
    public void nextCard(Set.setType setType, Text nextButton){
        atQuestionInd += 1;
        int setSize = db.getSetSize(curSetName);
        if(atQuestionInd < setSize){
            switch(setType) {
                case FlashCard :
                    triggerSpinCardAnimation();
                    prevButtonF.setVisible(true);
                    QnATextF.setText(db.getQuestion(curSetName, atQuestionInd));
                    atQuestion = true;
                    if (atQuestionInd + 1 == setSize){confirmButtonF.setText("Quit");}
                    break;
                case Spelling :
                    termS.setText(db.getSet(curSetName).getCards().get(atQuestionInd).getQuestion());
                    userAnswers.add(definitionAnswerS.getText());
                    definitionAnswerS.setText("");
                    break;
                case MultipleChoice :
                    questionMC.setText(db.getQuestion(curSetName,atQuestionInd));
                    setAnswers();
                    for (RadioButton rb : answerChoices) {
                        rb.setSelected(false);
                    }
                    break;
            }
            if (atQuestionInd + 1 == setSize && nextButton!=null) {
                nextButton.setText("Submit");
            }
        } else{
            if (curSetType == Set.setType.Spelling){userAnswers.add(definitionAnswerS.getText());}
            if(curSetType == Set.setType.FlashCard){openMySets();}
            else{openResultPage();}
        }
    }

    /** ____________FLASHCARDS____________ */

    /**
     * Sets up flash card play. Used when opening the play flashcard page
     */
    public void setUpFlashcardPlay(){
        setUpPlay(Set.setType.FlashCard);
    }

    /**
     * Switches to the previous flashcard unless the last is currently played
     */
    public void nextFlashCard(){
        nextCard(Set.setType.FlashCard, null);
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

    /**
     * Method for flip flash card animation.
     */
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
        createSetFlowPane.setPrefHeight(10);
        for (CreateSetListItem item : items) {
            createSetFlowPane.setPrefHeight(createSetFlowPane.getPrefHeight() + item.getPaneSize());
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
        else {System.out.println("Error : setNameC field is empty");} // If name is ""
    }


    /** ____________MULTIPLE CHOICES____________ */

    /**
     * Sets up menu for playing multiple choice set.
     */
    public void setUpMultiplePlay(){
        setUpPlay(Set.setType.MultipleChoice);
    }

    /**
     * Randomizes the answers in a new array, the original array is still sorted
     * Changes the radio button-texts to the answers
     */
    public void setAnswers(){
        List<String> answersShuffled = new ArrayList<>(Arrays.asList(db.getAnswers(curSetName,atQuestionInd)));
        Collections.shuffle(answersShuffled);

        for (int i = 0; i < answerChoices.size(); i++) {
            answerChoices.get(i).setText(answersShuffled.get(i));
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

    /**
     * Method for bringing up the next card when playing multiple choice set.
     */
    public void nextMultipleCard(){
        nextCard(Set.setType.MultipleChoice, nextButtonTextMC);
    }

    /**
     * Handles the buttons when playing a multiple choice set.
     * @param choice
     */
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
    /**
     * Handles the presentation of the result of the recently played set.
     */
    public void presentResult(){
        int correct = 0;
        resultFlowPane.setPrefHeight(10);
        resultSetText.setText("Result of " + curSetName);
        for (int i = 0; i < userAnswers.size(); i++) {
            Card curCard = db.getCardAtIndex(curSetName,i);
            ResultListItem item = new ResultListItem(this, curCard, userAnswers.get(i));
            resultFlowPane.setPrefHeight(resultFlowPane.getPrefHeight() + item.getPaneSize());
            resultFlowPane.getChildren().add(item);
            if (curCard.getCorrectAnswer().equals(userAnswers.get(i))){
                correct++;
            }
        }
        if (userAnswers.size() > 0) {
            resultNumCorrect.setText("Correct answers: " + correct + "/" + userAnswers.size() + " (" + Math.round(correct * 100 / userAnswers.size())  + "%)");
        }
    }

    /**
     * Handles the play again button.
     */
    public void playAgain(){
        switch (curSetType){
            case Spelling -> openPlaySpelling();
            case MultipleChoice -> openPlayMultipleChoice();
            case FlashCard -> openPlayFlashcard();
        }
    }

    /** ____________EDIT SET____________ */

    /**
     * Handles the edit set manu.
     * @param set The set that should be edited.
     */
    public void editSet(Set set){
        setNameC.setText(curSetName);
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
        createSetFlowPane.setPrefHeight(10);
        createSetFlowPane.getChildren().clear();
        for (CreateSetListItem item : db.getCreateSetListItems()) {
            createSetFlowPane.setPrefHeight(createSetFlowPane.getPrefHeight() + item.getPaneSize());
            createSetFlowPane.getChildren().add(item);
        }
        createSetView.toFront();
    }

    /**
     * Handles the edit current set menu.
     */
    public void editCurrentSet(){
        editSet(db.getSet(curSetName));
    }

    /** ____________SPELLING____________ */

    /**
     * Sets up Play Spelling when a set is chosen (getFlashSet will be replaced by getTextSet)
     */
    public void setUpSpellingPlay(){
        setUpPlay(Set.setType.Spelling);
    }

    /**
     * Switches to the next Spelling unless the last is currently played
     */
    public void nextSpellingTerm(){
        nextCard(Set.setType.Spelling,nextButtonTextS);
    }

    /** ____________MY SETS____________ */
    /**
     * Updates the my sets flow pane.
     */
    public void updateMySetsFlowPane(){
        mySetsFlowPane.getChildren().clear();
        mySetsFlowPane.setPrefHeight(10);
        ArrayList<Set> sets = new ArrayList<>(db.getSetHashMap().values());

        for (Set set : sets) {
            MySetsListItem item = new MySetsListItem(this, set);
            mySetsFlowPane.setPrefHeight(mySetsFlowPane.getPrefHeight() + item.getPaneHeight());
            mySetsFlowPane.getChildren().add(item);
        }
    }

    /** -------------- General methods -------------- */

    /**
     * Sets the name of the current set to the given string.
     * @param name The name of the new set.
     */
    public void setCurSetName(String name){
        curSetName = name;
    }

    /**
     * Removes the set with the given name.
     * @param setName The name of the set that should be removed.
     * @throws Exception Throws an exception if delete set or get sail fails.
     */
    public void removeSet(String setName) throws Exception {
        Set set;
        set = db.getSet(setName);
        switch (set.getThisSetType()) {
            case FlashCard :
                db.deleteSet(db.getFlashSetStringRepresentation() + db.getSplitterChar() + setName);
                break;
            case Spelling:
                db.deleteSet(db.getSpellingSetStringRepresentation() + db.getSplitterChar() + setName);
                break;
            case MultipleChoice:
                db.deleteSet(db.getMultipleChoiceSetStringRepresentation() + db.getSplitterChar() + setName);
                break;
        }
        updateMySetsFlowPane();
    }

    /** -------------- Methods to open pages -------------- */
    /**
     * Opens the play flashcard page.
     */
    public void openPlayFlashcard(){
        setUpFlashcardPlay();
        playFlashcardView.toFront();
    }

    /**
     * Opens the play spelling page
     */
    public void openPlaySpelling(){
        spellingView.toFront();
        setUpSpellingPlay();
    }

    /**
     * Opens the play multiple choice page.
     */
    public void openPlayMultipleChoice(){
        setUpMultiplePlay();
        playMultipleChoiceView.toFront();
    }

    /**
     * Opens the start menu.
     */
    public void openStartMenu(){ startMenu.toFront(); }

    /**
     * Opens the my sets page.
     */
    public void openMySets(){
        updateMySetsFlowPane();
        mySetsView.toFront(); }

    /**
     * Opens the choose set page.
     */
    public void openChooseSet(){
        choooseSetView.toFront();
    }

    /**
     * Opens the intro page.
     */
    public void openIntroPage(){ introPage.toFront(); }

    /**
     * Opens the result page.
     */
    public void openResultPage(){
        presentResult();
        resultPage.toFront();
    }

    /**
     * Opens the create flashcard set page.
     */
    public void openCreateSetFlashCards(){
        openCreateSet(Set.setType.FlashCard);
    }

    /**
     * Opens the crate spelling set page.
     */
    public void openCreateSetSpelling(){
        openCreateSet(Set.setType.Spelling);
    }

    /**
     * Opens the create multiple choice set page.
     */
    public void openCreateSetMultiple(){
        openCreateSet(Set.setType.MultipleChoice);
    }
}



