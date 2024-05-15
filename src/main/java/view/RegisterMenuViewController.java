package view;

import controller.ApplicationController;
import controller.RegisterMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class RegisterMenuViewController extends Application {
    public static Scene scene;
    private RegisterMenuController registerMenuController = new RegisterMenuController();
    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ApplicationController.playMusic();
        ApplicationController.setStage(stage);
        URL url = Main.class.getResource("/FXML/RegisterMenu.fxml");
        assert url != null;
        Pane root;
        try {
            root = FXMLLoader.load(url);
        } catch (Exception e) {
            return;
        }
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());
        ApplicationController.setScene(scene);
    }

    public static void makeAlert(String title, String header, String content, String type) {
        Alert alert = switch (type) {
            case "ERROR" -> new Alert(Alert.AlertType.ERROR);
            case "INFORMATION" -> new Alert(Alert.AlertType.INFORMATION);
            case "WARNING" -> new Alert(Alert.AlertType.WARNING);
            case "CONFIRMATION" -> new Alert(Alert.AlertType.CONFIRMATION);
            default -> new Alert(Alert.AlertType.NONE);
        };
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage mainStage = ApplicationController.getStage();
        mainStage.setAlwaysOnTop(false);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.setAlwaysOnTop(true);
        alert.showAndWait();
        mainStage.setAlwaysOnTop(true);
    }
}
