package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateSetMultipleAnswer extends AnchorPane {

    FXController controller;
    String id;

    @FXML private TextField termTextC;
    @FXML private TextField defText1C;
    @FXML private TextField defText2C;
    @FXML private TextField defText3C;
    @FXML private TextField defText4C;

    /** Constructor for order with fxml */
    public CreateSetMultipleAnswer(FXController controller, String id) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createSetMultipleAnswer.fxml"));

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

    public String getTerm(){
        return termTextC.getText();
    }

    public String getDefinition(int n){
        return switch (n) {
            case 1 -> defText1C.getText();
            case 2 -> defText2C.getText();
            case 3 -> defText3C.getText();
            case 4 -> defText4C.getText();
            default -> "";
        };
    }

    public List<String> getDefinitions(){
        List<String> defs = new ArrayList<>();
        defs.add(defText1C.getText());
        defs.add(defText2C.getText());
        defs.add(defText3C.getText());
        defs.add(defText4C.getText());
        return defs;
    }


    public void removeCreateListItem(){
        controller.db.getCreateSingleListItems().removeIf(item -> id.equals(item.id));
        controller.updateCreateSingleFlowPane(controller.db.getCreateSingleListItems());
    }
}

/*
    public void makeFile()  {
        try{
            File mySet = new File ("filename.txt");
            if (mySet.createNewFile()) {
                System.out.println("File created: " + mySet.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFile(String name, String string) {
        Path path = Path.of("./Sets/"+ name + ".txt");
        try {
            Files.writeString(path, string, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }

    }*/

