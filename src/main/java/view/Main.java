package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = Main.class.getResource("/FXML/RegisterMenu.fxml");
        assert url != null;
        Pane root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}