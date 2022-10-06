package Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Set;

public class MySetsGridItem extends AnchorPane {

    FXController controller;
    Model.Set set;
    @FXML Text setName;

    /** Constructor for order with fxml */
    public MySetsGridItem(FXController controller, Model.Set set) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mySetsGridItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.controller = controller;
        this.set = set;
        setName.setText(set.getName());
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
        switch (set.getThisSetType()){
            case FlashCard, Spelling -> controller.openCreateSetSingle();
            case MultipleChoice -> controller.openCreateSetMultiple();
        }
    }


}

