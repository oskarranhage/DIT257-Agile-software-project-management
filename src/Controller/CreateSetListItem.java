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

    /** Constructor for order with fxml */
    public CreateSetListItem(FXController controller, String id) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createNewSet.fxml"));

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

    public String getDefinition(){
        return defTextC.getText();
    }


    public void removeCreateListItem(){
        controller.db.getCreateSetItems().removeIf(item -> id.equals(item.id));
        controller.updateCreateSetFlowPane(controller.db.getCreateSetItems());
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

