package Controller;

import Model.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ResultSingleListItem extends AnchorPane {

    FXController controller;
    String id;

    @FXML private Text termTextC;
    @FXML private Text defTextC;
    @FXML private ImageView correctAnswerImage;
    @FXML private ImageView wrongAnswerImage;

    /** Constructor for order with fxml */
    public ResultSingleListItem(FXController controller, boolean isCorrect) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("...fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        if (isCorrect) {
            correctAnswerImage.setVisible(true);
            correctAnswerImage.toFront();
        } else {
            wrongAnswerImage.setVisible(true);
            wrongAnswerImage.toFront();
        }

        this.controller = controller;
        this.id = id;
    }

    public ResultSingleListItem(FXController controller, Card card, boolean isCorrect) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("....fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        if (isCorrect) {
            correctAnswerImage.setVisible(true);
            correctAnswerImage.toFront();
        } else {
            wrongAnswerImage.setVisible(true);
            wrongAnswerImage.toFront();
        }

        this.controller = controller;
        this.id = id;
        termTextC.setText(card.getQuestion());
        defTextC.setText(card.getAnswers()[0]);
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
