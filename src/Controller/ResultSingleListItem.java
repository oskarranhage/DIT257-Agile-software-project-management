package Controller;

import Model.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ResultSingleListItem extends AnchorPane {

    FXController controller;
    String id;

    @FXML private Text termTextC;
    @FXML private Text defTextC;
    @FXML private Text userInputText;
    @FXML private ImageView correctAnswerImage;
    @FXML private ImageView wrongAnswerImage;
    @FXML private Pane borderPane;
    @FXML private Pane userInputPane;

    public ResultSingleListItem(FXController controller, Card card, String userInput) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resultSingleAnswer.fxml"));

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

    public String getTerm(){
        return termTextC.getText();
    }

    public String getDefinition(){
        return defTextC.getText();
    }


    public void removeCreateListItem(){
        controller.db.getCreateSingleListItems().removeIf(item -> id.equals(item.id));
        controller.updateCreateSingleFlowPane(controller.db.getCreateSingleListItems());
    }
}
