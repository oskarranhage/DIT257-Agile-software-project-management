package Controller;
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

    @FXML private ImageView bulbImage;

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
        db.getSpellingSets().put("test spelling", new Set("test spelling", List.of(new Card("blue", "blå"), new Card("red", "röd"), new Card("green", "grön")), Model.Set.setType.Spelling));
        db.getMultiSets().put("test multiple", new Set("test multiple", List.of(new Card("4", new String[]{"5-2", "3-1", "7-3", "3-2"}), new Card("3", new String[]{"5-2", "3-1", "7-3", "3-2"}), new Card("1", new String[]{"5-2", "3-1", "7-3", "3-2"})), Model.Set.setType.MultipleChoice));
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
        currentSetTextF.setText("Playing flashcards: " + db.getFlashSets().get(curSetName).getName());
        QnATextF.setText(db.getFlashSet(curSetName).getCards().get(0).getQuestion());
    }

    /**
     * Switches to the previous flashcard unless the last is currently played
     */
    public void nextFlashCard(){
        FlashcardListItem flashcardListItem = new FlashcardListItem(this, db.getFlashSet(curSetName).getCards().get(atQuestionInd).getAnswers()[0]);
        tempFlashCard.getChildren().add(flashcardListItem);
        flashcardListItem.setLayoutX(511);
        flashcardListItem.setLayoutY(355);
        flashcardListItem.toFront();
        flashcardListItem.nextFlashCard();

        prevButtonF.setVisible(true);
        if (db.getFlashSet(curSetName).getCards().size()-1 > atQuestionInd){
            atQuestionInd += 1;
            QnATextF.setText(db.getFlashSet(curSetName).getCards().get(atQuestionInd).getQuestion());
            atQuestion = true;
            if (db.getFlashSet(curSetName).getCards().size()-1 == atQuestionInd) {
                confirmButtonF.setText("Submit");
            }
        }

        /*TranslateTransition translate = new TranslateTransition();
        translate.setNode(flashCardBack);
        translate.setDuration(Duration.millis(1500));
        translate.setCycleCount(1);
        translate.setByX(500);
        translate.setByY(-250);
        translate.setAutoReverse(true);
        translate.play();

        // rotate
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(flashCardBack);
        rotate.setDuration(Duration.millis(1500));
        rotate.setCycleCount(1);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(180);
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.play();

        // fade
        FadeTransition fade = new FadeTransition();
        fade.setNode(flashCardBack);
        fade.setDuration(Duration.millis(1500));
        fade.setCycleCount(1);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();

        ScaleTransition scale = new ScaleTransition();
        scale.setNode(flashCardBack);
        scale.setDuration(Duration.millis(1500));
        scale.setCycleCount(1);
        scale.setInterpolator(Interpolator.EASE_OUT);
        scale.setByX(-1.0);
        scale.setByY(-1.0);
        scale.setAutoReverse(false);
        scale.play();*/
    }

    /**
     * Switches to the previous flashcard unless the first is currently played
     */
    public void previousFlashCard(){
        if (atQuestionInd != 0){
            atQuestionInd -= 1;
            QnATextF.setText(db.getFlashSet(curSetName).getCards().get(atQuestionInd).getQuestion());
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
        //RotateTransition rt = createRotator(flashCard);
//        ScaleTransition stHideFront = new ScaleTransition(Duration.millis(1000), );
//        stHideFront.setFromX(1);
//        stHideFront.setToX(0);
        if (atQuestion){
            out.println(db.getFlashSet(curSetName).getCards().get(atQuestionInd).getAnswers()[0]);
            QnATextF.setText(db.getFlashSet(curSetName).getCards().get(atQuestionInd).getAnswers()[0]);
            atQuestion = false;
        } else{
            QnATextF.setText(db.getFlashSet(curSetName).getCards().get(atQuestionInd).getQuestion());
            atQuestion = true;
        }

        ScaleTransition stShowBack = new ScaleTransition(Duration.millis(1000), flashCardBack);
        stShowBack.setFromX(0);
        stShowBack.setToX(1);
        stShowBack.play();
    }

    private RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(1000), card);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(10);

        return rotator;
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
        createItemID = 1;
        for (SingleAnswerListItem item : items) {
            item.id = "CS" + createItemID;
            createItemID++;
            createSetFlowPane.getChildren().add(item);
        }
    }

    public void updateCreateMultipleFlowPane(List<MultipleAnswerListItem> items){
        createSetFlowPane.getChildren().clear();
        createItemID = 1;
        for (MultipleAnswerListItem item : items) {
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
        curSetType = Model.Set.setType.FlashCard;
        createSetView.toFront();
        if (createSetFlowPane.getChildren().size() == 0){
            for (int i = 0; i < 3; i++) {
                addCreateSetItem();
            }
        }
    }

    public void openCreateSetMultiple(){
        createSetFlowPane.getChildren().clear();
        curSetType = Model.Set.setType.MultipleChoice;
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
    public void saveCreatedSet(){
        String type = "fs.";
        switch (curSetType){
            case FlashCard -> type = "fs.";
            case Spelling -> type = "ss.";
            case MultipleChoice -> type = "mcs.";
        }
        if (!setNameC.getText().equals("")) {
            String name = setNameC.getText();
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            for (SingleAnswerListItem item : db.getCreateSingleListItems()) {
                sb.append("\n").append(item.getTerm()).append(".").append(item.getDefinition());
            }
            FileManager.makeFile(type + name, sb.toString());
            db.updateAll();
            // TODO set creation confirmation message to front
        } else{
            // TODO name error message to front
        }
        db.updateAll();
        curSetName = setNameC.getText();
        createdSetName.setText(curSetName);
        //createdSetName.setX(0);
        //createdSetName.setY(0);
        setCreatedView.toFront();
    }


    /** ____________MULTIPLE CHOICES____________ */

    public void setUpMultiplePlay(){
        curSetType = Set.setType.MultipleChoice;
        curSetName = "test multiple";
        atQuestion = true;
        atQuestionInd = 0;
        currentSetTextF.setText(db.getMultiSets().get(curSetName).getName());
        playingSetMultipleText.setText("Playing multiple choices: " + db.getMultiSet(curSetName).getCards().get(atQuestionInd).getQuestion());
        setAnswers();
    }

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
            nextMultipleCard();
        } else{
            // TODO say that they need to answer the question before they can go to the next
        }
    }

    public void nextMultipleCard(){
        if (db.getMultiSet(curSetName).getCards().size()-1 > atQuestionInd){
            atQuestionInd += 1;
            questionMC.setText(db.getMultiSet(curSetName).getCards().get(atQuestionInd).getQuestion());
            setAnswers();
            for (RadioButton rb : answerChoices) {
                rb.setSelected(false);
            }
            if (atQuestionInd == db.getMultiSet(curSetName).getCards().size()-1){
                nextButtonTextMC.setText("Submit");
            }
        } else{
            openResultPage();
        }
    }
    /**
     * RESULT SCREEN
     */
    public void presentResult(){
        int correct = 0;
        resultSetText.setText("Result of " + curSetName);
        for (int i = 0; i < userAnswers.size(); i++) {
            Card curCard = null;
            switch (curSetType) {
                case FlashCard -> {
                    curCard = db.getFlashSet(curSetName).getCards().get(i);
                    resultFlowPane.getChildren().add(new ResultSingleListItem(this, curCard, curCard.getAnswers().equals(userAnswers.get(i))));
                }
                case Spelling -> {
                    curCard = db.getSpellingSet(curSetName).getCards().get(i);
                    if (curCard.getAnswers().equals(userAnswers.get(i))) {
                        correct++;
                    }
                    resultFlowPane.getChildren().add(new ResultSingleListItem(this, curCard, curCard.getAnswers().equals(userAnswers.get(i))));
                }
                case MultipleChoice -> {
                    curCard = db.getMultiSet(curSetName).getCards().get(i);
                    if (curCard.getAnswers().equals(userAnswers.get(i))) {
                        correct++;
                    }
                    resultFlowPane.getChildren().add(new ResultMultipleListItem(this, curCard, curCard.getAnswers().equals(userAnswers.get(i))));
                }
            }
            if (curCard.getAnswers().equals(userAnswers.get(i))){
                correct++;
            }
        }
        resultNumCorrect.setText("Correct answers: " + correct + "/" + userAnswers.size());
        if (userAnswers.size() > 0){
            resultPercentage.setText(correct/userAnswers.size() * 100 + "%");
        }
        // int wrong = db.getMultiSet(curSetName).getCards().size() - correct;
        // setText(correct/db.getMultiSet(curSetName).getCards().size());
    }

    public void playAgain(){
        switch (curSetType){
            case Spelling -> openPlaySpelling();
            case MultipleChoice -> openPlayMultipleChoice();
            case FlashCard -> openPlayFlashcard();
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
        editSingleSet(db.getFlashSet(curSetName));
        //editSingleSet(db.getMultiSet(curSetName));
        //editSingleSet(db.getSpellingSet(curSetName));
    }

    /** ____________SPELLING____________ */

    /**
     * Sets up Play Spelling when a set is chosen (getFlashSet will be replaced by getTextSet)
     */
    public void setUpSpellingPlay(){
        curSetType = Set.setType.Spelling;
        curSetName = "test spelling";
        atQuestionInd = 0;
        currentSetTextF.setText(curSetName);
        termS.setText("Playing spelling: " + db.getSpellingSet(curSetName).getCards().get(atQuestionInd).getQuestion());
    }

    /**
     * Switches to the previous Spelling unless the last is currently played
     */
    public void nextSpellingTerm(){
        if (db.getSpellingSet("test spelling").getCards().size()-1 > atQuestionInd){
            atQuestionInd += 1;
            termS.setText(db.getSpellingSet(curSetName).getCards().get(atQuestionInd).getQuestion());
            userAnswers.add(definitionAnswerS.getText());
            definitionAnswerS.setText("");
            if (atQuestionInd == db.getMultiSet(curSetName).getCards().size()-1){
                nextButtonTextMC.setText("Submit");
            }
        } else {
            openResultPage();
        }
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

    /** ____________MY SETS____________ */
    public void updateMySetsGridPane(Model.Set.setType type){
        mySetsFlowPane.getChildren().clear();
        ArrayList<Model.Set> sets = new ArrayList<>();
        switch (type){
            case FlashCard -> sets.addAll(db.getFlashSets().values());
            case MultipleChoice -> sets.addAll(db.getMultiSets().values());
            case Spelling -> sets.addAll(db.getSpellingSets().values());
            case Null -> {
                sets.addAll(db.getFlashSets().values());
                sets.addAll(db.getMultiSets().values());
                sets.addAll(db.getSpellingSets().values());
            }
        }

        for (Set set : sets) {
            mySetsFlowPane.getChildren().add(new MySetsGridItem(this, set));
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
    public void openResultPage(){
        viewResults();
        resultPage.toFront();
    }
}