package view;

import controller.ApplicationController;
import controller.ProfileMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;

public class ProfileMenuViewController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    public static Scene scene;

    public static void start() {
        URL url = Main.class.getResource("/FXML/ProfileMenu.fxml");
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

    public void saveUsername() {
        String username = usernameField.getText();
        ProfileMenuController.saveUsername(username);
    }

    public void savePassword() {
        String password = passwordField.getText();
        ProfileMenuController.savePassword(password);
    }

    public void removeAccount() {
        ProfileMenuController.removeAccount();
        signOut();
    }

    public void signOut() {
        ProfileMenuController.signOut();
        ApplicationController.setScene(RegisterMenuViewController.scene);
    }

    public void avatarMenu(MouseEvent mouseEvent) {
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
}
