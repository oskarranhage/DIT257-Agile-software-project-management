package Controller;

import Model.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class FXController implements Initializable {
    private DataBase db;
    /**
     * Play flashcards
     * The F at the end of each variable is short for "Flashcard"
     */
    @FXML private AnchorPane playFlashcardView;
    @FXML private Button backButtonF;
    @FXML private Button nextButtonF;
    @FXML private Button previousButtonF;
    @FXML private Text QnATextF;
    @FXML private Text instructionF;
    @FXML private Text currentSetTextF;

    private int atCardInd = 0;
    private Set currentFlashSet;
    private boolean atQuestion;
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
    //@FXML private Text answer1Text;
    //@FXML private Text answer2Text;
    //@FXML private Text answer3Text;
    //@FXML private Text answer4Text;
    //@FXML private List<Text> answerTexts = List.of(answer1Text, answer2Text, answer3Text, answer4Text);
    //private List<RadioButton> answerChoices; { assert false; answerChoices = List.of(answer1, answer2, answer3, answer4); }

    @FXML private Text confirmAnswerMC;
    @FXML private Text guessResultMC;

    /**
     * Start Menu
     * The SM the end of each variable is short for "Start Menu"
     */
    @FXML private AnchorPane startMenu;
    @FXML private Button createFolderButtonSM;
    @FXML private Button createSetButtonSM;
    @FXML private Circle escapeHatchSM;
    @FXML private Text plusIcon1SM;
    @FXML private Button plusIcon2SM;
    @FXML private Text myFoldersTextSM;
    @FXML private Text mySetsTextSM;
    @FXML private Text newFolderTextSM;
    @FXML private Text newSetsTextSM;

    /**
     * Other pages
     */
    @FXML private AnchorPane createFolderMenu;
    @FXML private AnchorPane createSetView;
    @FXML private AnchorPane myFoldersView;
    @FXML private AnchorPane mySetsView;

    public FXController() throws IOException {
    }

    //private final Model model = Model.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DataBase();
    }

    /** -------------- Play flashcard methods -------------- */
    public void setQnATextF(){ QnATextF.setText("Fjäril"); }

    public void setUpFlashcardPlay(){
        currentFlashSet = db.getFlashSets().get(0);
        atCardInd = 0;
        atQuestion = true;
        currentSetTextF.setText("You are running " + currentFlashSet.getName() + " as a flash set.");
        instructionF.setText("Click to turn");
        QnATextF.setText(currentFlashSet.getCards().get(atCardInd).getQuestion());
    }

    public void nextFlashCard(){
        if (currentFlashSet.getCards().size()-1 != atCardInd){
            atCardInd += 1;
            atQuestion = true;
            QnATextF.setText(currentFlashSet.getCards().get(atCardInd).getQuestion());
        }
    }

    public void previousFlashCard(){
        if (atCardInd != 0){
            atCardInd -= 1;
            atQuestion = true;
            QnATextF.setText(currentFlashSet.getCards().get(atCardInd).getQuestion());
        }
    }

    public void showAnswer(){
        if(atQuestion){
             atQuestion = false;
             QnATextF.setText(currentFlashSet.getCards().get(atCardInd).getAnswers()[0]);
        }
    }
    /** -------------- Play Multiple Choice methods -------------- */
    /*public void setAnswers(List<String> answers){
        for (int i = 0; i < answers.size(); i++) {
            answerChoices.get(i).setSelected(false);
            answerChoices.get(i).setText(answers.get(i));
        }
    }*/

    /*public void setAnswerTexts(List<String> answers){
        for (int i = 0; i < answers.size(); i++) {
            answerTexts.get(i).setText(answers.get(i));
        }
    }*/

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

    public void setCurrentSetMC(String s){ currentSetTextF.setText("You are running " + s + " as a multiple choice set."); }


    /** -------------- Methods to open pages -------------- */
    public void openPlayFlashcard(){
        setUpFlashcardPlay();
        playFlashcardView.toFront();
    }
    public void openPlayMultipleChoice(){ playMultipleChoiceView.toFront(); }
    public void openStartMenu(){ startMenu.toFront(); }
    public void openCreateFolder(){ createFolderMenu.toFront(); }
    public void openCreateSet(){ createSetView.toFront(); }
    public void openMyFolders(){ myFoldersView.toFront(); }
    public void openMySets(){ mySetsView.toFront(); }

}

