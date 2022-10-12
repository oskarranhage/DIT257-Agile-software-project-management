package Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.List;

public class MultipleAnswerListItem extends AnchorPane {

   FXController controller;
   String id;

   @FXML
   private TextField questionTextC;
   @FXML
   private TextField answerText1;
   @FXML
   private TextField answerText2;
   @FXML
   private TextField answerText3;
   @FXML
   private TextField answerText4;


   /**
    * Constructor for order with fxml
    */
   public MultipleAnswerListItem(FXController controller, String id) {
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

   public MultipleAnswerListItem(FXController controller, String id, String term, List<String> answers) {
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
      questionTextC.setText(term);

      answerText1.setText(answers.get(0));
      answerText2.setText(answers.get(1));
      answerText3.setText(answers.get(2));
      answerText4.setText(answers.get(3));
   }

   public String getTerm(){
      return questionTextC.getText();
   }

   public void removeCreateListItem(){
      controller.db.getCreateSingleListItems().removeIf(item -> id.equals(item.id));
      controller.updateCreateSingleFlowPane(controller.db.getCreateSingleListItems());
   }
}
