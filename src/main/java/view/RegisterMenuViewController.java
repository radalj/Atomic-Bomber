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
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    public static Scene scene;
    private RegisterMenuController registerMenuController = new RegisterMenuController();
    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
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
        ApplicationController.setScene(scene);
    }

    public void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        registerMenuController.register(username, password);
    }

    public void signIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        registerMenuController.signIn(username, password);
    }

    public void enterAsGuest() {
        registerMenuController.enterAsGuest();
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
        alert.showAndWait();
    }
}
