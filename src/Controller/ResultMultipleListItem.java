package Controller;

import Model.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class ResultMultipleListItem extends AnchorPane {
    FXController controller;

    @FXML private Text questionTextC;
    @FXML private Text answerText1;
    @FXML private Text answerText2;
    @FXML private Text answerText3;
    @FXML private Text answerText4;
    @FXML private ImageView correctAnswerImage;
    @FXML private ImageView wrongAnswerImage;

    public ResultMultipleListItem(FXController controller, Card card, boolean isCorrect) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("....fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.controller = controller;
        questionTextC.setText(card.getQuestion());

        answerText1.setText(card.getAnswers()[0]);
        answerText2.setText(card.getAnswers()[1]);
        answerText3.setText(card.getAnswers()[2]);
        answerText4.setText(card.getAnswers()[3]);

        if (isCorrect) {
            correctAnswerImage.setVisible(true);
            correctAnswerImage.toFront();
        } else {
            wrongAnswerImage.setVisible(true);
            wrongAnswerImage.toFront();
        }
    }
}
