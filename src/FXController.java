import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXController implements Initializable {
    /**
     * Play flashcards
     * The F at the end of each variable is short for "Flashcard"
     */
    @FXML private AnchorPane playFlashcardView;
    @FXML private Pane backButtonF;
    @FXML private Pane nextButtonF;
    @FXML private Pane previousButtonF;
    @FXML private Label QnALabelF;
    @FXML private Label instructionF;
    @FXML private Label currentSetLabelF;

    /**
     * Play MultipleChoice
     * The MC at the end of each variable is short for "MultipleChoice"
     */

    @FXML private AnchorPane playMultipleChoiceView;
    @FXML private Pane backButtonMC;
    @FXML private Pane nextButtonMC;
    @FXML private Pane previousButtonMC;
    @FXML private Label questionMC;
    @FXML private RadioButton answer1;
    @FXML private RadioButton answer2;
    @FXML private RadioButton answer3;
    @FXML private RadioButton answer4;
    //@FXML private Label answer1Label;
    //@FXML private Label answer2Label;
    //@FXML private Label answer3Label;
    //@FXML private Label answer4Label;
    //@FXML private List<Label> answerLabels = List.of(answer1Label, answer2Label, answer3Label, answer4Label);
    private List<RadioButton> answerChoices;
    { assert false; answerChoices = List.of(answer1, answer2, answer3, answer4); }

    @FXML private Label confirmAnswerMC;
    @FXML private Label guessResultMC;

    /**
     * Start Menu
     * The SM the end of each variable is short for "Start Menu"
     */
    @FXML private AnchorPane startMenu;
    @FXML private Pane createFolderButtonSM;
    @FXML private Pane createSetButtonSM;
    @FXML private Pane escapeHatchSM;
    @FXML private ImageView plusIcon1SM;
    @FXML private ImageView plusIcon2SM;
    @FXML private Label myFoldersLabelSM;
    @FXML private Label mySetsLabelSM;
    @FXML private Label newFolderLabelSM;
    @FXML private Label newSetsLabelSM;

    /**
     * Other pages
     */
    @FXML private AnchorPane createFolderMenu;
    @FXML private AnchorPane createSetView;
    @FXML private AnchorPane myFoldersView;
    @FXML private AnchorPane mySetsView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInstructionF("Click on a card to turn it");
    }

    /** -------------- Play flashcard methods -------------- */
    public void setQnATextF(String s){ QnALabelF.setText(s); }

    public void setInstructionF(String s){ instructionF.setText(s); }

    public void setCurrentSetF(String s){ currentSetLabelF.setText("You are running " + s + " as a flash set."); }


    /** -------------- Play Multiple Choice methods -------------- */
    public void setAnswers(List<String> answers){
        for (int i = 0; i < answers.size(); i++) {
            answerChoices.get(i).setSelected(false);
            answerChoices.get(i).setText(answers.get(i));
        }
    }

    /*public void setAnswerLabels(List<String> answers){
        for (int i = 0; i < answers.size(); i++) {
            answerLabels.get(i).setText(answers.get(i));
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

    public void selectAnswer(RadioButton choice){
        for (RadioButton rb : answerChoices) {
            rb.setSelected(rb.getText().equals(choice.getText()));
        }
    }

    public void selectFirstAnswer(){ selectAnswer(answer1); }
    public void selectSecondAnswer(){ selectAnswer(answer2); }
    public void selectThirdAnswer(){ selectAnswer(answer3); }
    public void selectFourthAnswer(){ selectAnswer(answer4); }

    public void setCurrentSetMC(String s){ currentSetLabelF.setText("You are running " + s + " as a multiple choice set."); }


    /** -------------- Methods to open pages -------------- */
    public void openPlayFlashcard(){
        playFlashcardView.toFront();
    }
    public void openPlayMultipleChoice(){ playMultipleChoiceView.toFront(); }
    public void openStartMenu(){ startMenu.toFront(); }
    public void openCreateFolder(){ createFolderMenu.toFront(); }
    public void openCreateSet(){ createSetView.toFront(); }
    public void openMyFolders(){ myFoldersView.toFront(); }
    public void openMySets(){ mySetsView.toFront(); }

}

