package Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class SingleAnswerListItem extends AnchorPane {

    FXController controller;
    String id;

    @FXML private TextField termTextC;
    @FXML private TextField defTextC;

    /** Constructor for order with fxml */
    public SingleAnswerListItem(FXController controller, String id) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createSetSingleAnswer.fxml"));

                fxmlLoader.setRoot(this);
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }

        this.controller = controller;
        this.id = id;
    }

    public SingleAnswerListItem(FXController controller, String id, String term, String def) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createSetSingleAnswer.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.controller = controller;
        this.id = id;
        termTextC.setText(term);
        defTextC.setText(def);
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
