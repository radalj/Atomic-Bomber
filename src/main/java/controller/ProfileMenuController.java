package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.User;
import view.Main;
import view.ProfileMenuViewController;
import view.RegisterMenuViewController;

import java.net.URL;

public class ProfileMenuController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    public static Scene scene;

    public void initialize() {
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
        scene.getStylesheets().add(getClass().getResource("/CSS/Styles.css").toExternalForm());
        ApplicationController.setScene(scene);
    }

    public void saveUsername() {
        String username = usernameField.getText();
        if (User.getUser(username) != null) {
            ProfileMenuViewController.makeAlert("Username Already Exists", "Username Already Exists", "Username" + username + " already exists.", "WARNING");
            return;
        }
        User.getCurrentUser().setUsername(username);
        ProfileMenuViewController.makeAlert("Username Changed", "Username Changed", "Username changed successfully.", "INFORMATION");
    }

    public void savePassword() {
        String password = passwordField.getText();
        User.getCurrentUser().setPassword(password);
        ProfileMenuViewController.makeAlert("Password Changed", "Password Changed", "Password changed successfully.", "INFORMATION");
    }

    public void removeAccount() {
        User.getUsers().remove(User.getCurrentUser());
        signOut();
    }

    public void signOut() {
        User.setCurrentUser(null);
        ApplicationController.setScene(RegisterMenuViewController.scene);
    }

    public void avatarMenu() {
        AvatarMenuController avatarMenuController = new AvatarMenuController();
        avatarMenuController.start();
    }

    public void back() {
        ApplicationController.setScene(MainMenuController.scene);
    }
}
