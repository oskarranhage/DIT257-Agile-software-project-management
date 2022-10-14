package Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;

public class MySetsListItem extends AnchorPane {

    FXController controller;
    Model.Set set;
    @FXML Text setName;
    @FXML ImageView setImageFlash;
    @FXML ImageView setImageSpelling;
    @FXML ImageView setImageMul;

    /** Constructor for order with fxml */
    public MySetsListItem(FXController controller, Model.Set set) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mySetsListItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setImageFlash.setVisible(false);
        setImageSpelling.setVisible(false);
        setImageMul.setVisible(false);

        this.controller = controller;
        this.set = set;
        setName.setText(set.getName());
        setName.setTextAlignment(TextAlignment.CENTER);
        switch (set.getThisSetType()){
            case FlashCard -> setImageFlash.setVisible(true);
            case Spelling -> setImageSpelling.setVisible(true);
            case MultipleChoice -> setImageMul.setVisible(true);
        }
    }

    public void openSet(){
        controller.setCurSetName(set.getName());
        switch (set.getThisSetType()){
            case FlashCard -> controller.openPlayFlashcard();
            case MultipleChoice -> controller.openPlayMultipleChoice();
            case Spelling -> controller.openPlaySpelling();
      }
    }

    public void editSet(){
        controller.setCurSetName(set.getName());
        controller.editCurrentSet();
    }

    public void removeSet(){

    }

    private Image newImage(String url){
        return new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(url)));
    }
}

