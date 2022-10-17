package Controller;
import Model.Set.setType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class CreateSetListItem extends AnchorPane {

    FXController controller;
    String id;

    @FXML private TextField termTextC;
    @FXML private TextField defTextC;
    @FXML private TextField questionTextC;
    @FXML private TextField answerText1;
    @FXML private TextField answerText2;
    @FXML private TextField answerText3;
    @FXML private TextField answerText4;

    /** Constructor for order with fxml */
    public CreateSetListItem(FXController controller, String id, setType type) {
        switch (type){
            case Spelling, FlashCard -> loadFxml("createSetSingle.fxml");
            case MultipleChoice -> loadFxml("createSetMultiple.fxml");
        }
        this.controller = controller;
        this.id = id;
    }

    public CreateSetListItem(FXController controller, String id, setType type, String term, String[] answers) {
        switch (type){
            case FlashCard, Spelling:
                loadFxml("createSetSingle.fxml");
                termTextC.setText(term);
                defTextC.setText(answers[0]);
            case MultipleChoice:
                questionTextC.setText(term);
                answerText1.setText(answers[0]);
                answerText2.setText(answers[1]);
                answerText3.setText(answers[2]);
                answerText4.setText(answers[3]);
                loadFxml("createSetMultiple.fxml");
        }

        this.controller = controller;
        this.id = id;
    }

    public String getTerm(){
        return termTextC.getText();
    }

    public String getTermMCS() {return questionTextC.getText();}

    public String getDefinition(){
        return defTextC.getText();
    }
    public String[] getDefinitionsMCS() {
        String[] out = {answerText1.getText(),answerText2.getText(),answerText3.getText(),answerText4.getText()};
        return out;}


    public void removeCreateListItem(){
        controller.db.getCreateSetListItems().removeIf(item -> id.equals(item.id));
        controller.updateCreateSingleFlowPane(controller.db.getCreateSetListItems());
    }

    private void loadFxml(String url){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}