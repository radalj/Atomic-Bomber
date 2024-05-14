package view;

import controller.ApplicationController;
import controller.ProfileMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Objects;

public class ProfileMenuViewController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    public static Scene scene;
    private ProfileMenuController profileMenuController = new ProfileMenuController();
    public void initialize(){
        ApplicationController.playMusic();
    }
    public void start() {
        URL url = Main.class.getResource("/FXML/ProfileMenu.fxml");
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
        profileMenuController = new ProfileMenuController();
    }

    public void saveUsername() {
        String username = usernameField.getText();
        profileMenuController.saveUsername(username);
    }

    public void savePassword() {
        String password = passwordField.getText();
        profileMenuController.savePassword(password);
    }

    public void removeAccount() {
        profileMenuController.removeAccount();
    }

    public void signOut() {
        profileMenuController.signOut();
    }

    public void avatarMenu() {
        profileMenuController.avatarMenu();
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
        alert.show();
    }

    public void back(ActionEvent actionEvent) {
        ApplicationController.setScene(MainMenuViewController.scene);
    }
}
