package Controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.IOException;

public class FlashcardAnimation extends AnchorPane {

    FXController controller;
    @FXML
    Text QnATextListItem;

    public FlashcardAnimation(FXController controller, String answer) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("flashcardAnimationItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.controller = controller;
        QnATextListItem.setText(answer);
    }

    /** ____________FLASHCARDS____________ */

    /**
     * Switches to the previous flashcard unless the last is currently played
     */
    public void nextFlashCard(){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(this);
        translate.setDuration(Duration.millis(1500));
        translate.setCycleCount(1);
        translate.setByX(700);
        translate.setByY(-350);
        translate.setAutoReverse(true);
        translate.play();

        // rotate
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(this);
        rotate.setDuration(Duration.millis(1500));
        rotate.setCycleCount(1);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(205);
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.play();

        // fade
        FadeTransition fade = new FadeTransition();
        fade.setNode(this);
        fade.setDelay(Duration.millis(700));
        fade.setDuration(Duration.millis(700));
        fade.setCycleCount(1);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();

        ScaleTransition scale = new ScaleTransition();
        scale.setNode(this);
        scale.setDuration(Duration.millis(2000));
        scale.setCycleCount(1);
        scale.setInterpolator(Interpolator.EASE_OUT);
        scale.setByX(-1.0);
        scale.setByY(-1.0);
        scale.setAutoReverse(false);
        scale.play();
    }
}
