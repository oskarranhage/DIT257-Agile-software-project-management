package Controller;

import Model.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * ResultListItem represents the panes that appear in the result screen.
 */
public class ResultListItem extends AnchorPane {

    FXController controller;
    String id;

    @FXML private Text termTextC;
    @FXML private Text defTextC;
    @FXML private Text userInputText;
    @FXML private ImageView correctAnswerImage;
    @FXML private ImageView wrongAnswerImage;
    @FXML private Pane borderPane;
    @FXML private Pane userInputPane;
    @FXML private AnchorPane resultListItem;

    /**
     * Constructor fpr ResultListItem.
     * @param controller Passes down the FXController object.
     * @param card The card that should be displayed in the pane.
     * @param userInput The answer that the user gave.
     */
    public ResultListItem(FXController controller, Card card, String userInput) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resultListItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        if (card.getCorrectAnswer().equals(userInput)) {
            borderPane.setStyle("-fx-background-color: #C8FDDD");
        } else {
            borderPane.setStyle("-fx-background-color: #FBC4BD");
            userInputPane.setStyle("-fx-border-color: ##880808");
        }

        this.controller = controller;
        termTextC.setText(card.getQuestion());
        defTextC.setText(card.getCorrectAnswer());
        userInputText.setText(userInput);
    }

    /**Getter*/
    public String getTerm(){
        return termTextC.getText();
    }
    /**Getter*/
    public String getDefinition(){
        return defTextC.getText();
    }

    /**Getter for pane size*/
    public double getPaneSize(){
        return resultListItem.getHeight();
    }
}
