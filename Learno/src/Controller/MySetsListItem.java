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

/**
 * MySetsListItem is the panes that represents available sets in the my sets menu.
 */
public class MySetsListItem extends AnchorPane {

    FXController controller;
    Model.Set set;
    @FXML Text setName;
    @FXML ImageView setImageFlash;
    @FXML ImageView setImageSpelling;
    @FXML ImageView setImageMul;
    @FXML AnchorPane mySetListItem;

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

    /**
     * Method for playing set.
     */
    public void openSet(){
        controller.setCurSetName(set.getName());
        switch (set.getThisSetType()){
            case FlashCard -> controller.openPlayFlashcard();
            case MultipleChoice -> controller.openPlayMultipleChoice();
            case Spelling -> controller.openPlaySpelling();
        }
    }

    /**
     * Method for editing set.
     */
    public void editSet(){
        controller.setCurSetName(set.getName());
        controller.editCurrentSet();
    }

    /**
     * Method for removing set.
     */
    public void removeSet(){
        try {
            controller.removeSet(set.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * newImage
     */
    private Image newImage(String url){
        return new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(url)));
    }

    /**Getter for pane height*/
    public double getPaneHeight(){
        return mySetListItem.getHeight();
    }
}

