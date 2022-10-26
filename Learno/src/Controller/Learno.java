package Controller;
import View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Learno extends Application {

    public static void main(String[] args) {
        if(args.length != 0){
            if(args[0].equals("cmdView")){
                View view = new View();
                view.startMenu();
            }
        }
        else{
        launch(args);}
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Controller/learno.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }
}
