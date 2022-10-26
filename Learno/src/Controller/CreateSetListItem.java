package Controller;
import Model.Set.setType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

/**
 * CreateSetListItem represents the panel that is displayed when creating a new set.
 */
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
    @FXML private AnchorPane multipleListItem;
    @FXML private AnchorPane singleListItem;
    private final setType type;

    /** Constructor for order with fxml */
    public CreateSetListItem(FXController controller, String id, setType type) {
        this.type = type;
        this.controller = controller;
        this.id = id;

        switch (type){
            case Spelling, FlashCard -> loadFxml("createSetSingle.fxml");
            case MultipleChoice -> loadFxml("createSetMultiple.fxml");
        }
    }

    /**
     * Constructor for creating a create set list item. Uses a switch implementation for showing different
     * looking panes depending on the setType.
     * @param controller Passes down the FXController object.
     * @param id The id of the pane. All panes have different ids.
     * @param type The type of the current set. This decides if the pane should have one or more answers.
     * @param term The term on the pane, will be set to a value when editing a set.
     * @param answers The list of answers of the pane. Will be set to a value when editing a set.
     */
    public CreateSetListItem(FXController controller, String id, setType type, String term, String[] answers) {
        this.type = type;
        this.controller = controller;
        this.id = id;

        switch (type) {
            case FlashCard, Spelling -> {
                loadFxml("createSetSingle.fxml");
                termTextC.setText(term);
                defTextC.setText(answers[0]);
            }
            case MultipleChoice -> {
                loadFxml("createSetMultiple.fxml");
                questionTextC.setText(term);
                answerText1.setText(answers[0]);
                answerText2.setText(answers[1]);
                answerText3.setText(answers[2]);
                answerText4.setText(answers[3]);
            }
        }
    }

    /**Getter*/
    public String getTerm(){
        return termTextC.getText();
    }
    /**Getter*/
    public String getTermMCS() {return questionTextC.getText();}

    /**Getter, used when playing a flash and a spelling set.*/
    public String getDefinition(){return defTextC.getText();}

    /**Getter, used when playing a multiple choice set.*/
    public String[] getDefinitionsMCS() {
        return new String[]{answerText1.getText(),answerText2.getText(),answerText3.getText(),answerText4.getText()};}

    /**
     * Removes createSetListItem.
     */
    public void removeCreateListItem(){
        controller.db.getCreateSetListItems().removeIf(item -> id.equals(item.id));
        controller.updateCreateSingleFlowPane(controller.db.getCreateSetListItems());
    }

    /**
     * loads Fxml
     */
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

    /**
     * Gets the panel size.
     * @return Returns the panel size.
     */
    public double getPaneSize(){
        return switch (type) {
            case FlashCard, Spelling -> singleListItem.getPrefHeight();
            case MultipleChoice -> multipleListItem.getPrefHeight();
            default -> 0;
        };
    }
}