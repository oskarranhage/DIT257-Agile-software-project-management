package Controller;
import Model.Card;
import Model.DataBase;
import Model.Set;
import View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static javafx.application.Application.launch;

public class Learno extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Controller/learno.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }
}
