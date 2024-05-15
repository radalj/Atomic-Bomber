package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.User;
import view.Main;

import java.net.URL;

public class MainMenuController {
    public static Scene scene;
    @FXML
    private ImageView avatar;
    @FXML
    private Label usernameText;
    private ProfileMenuController profileMenuController;
    private RankingController rankingController;

    public void initialize() {
        ApplicationController.playMusic();
        avatar.setImage(User.getCurrentUser().getImage());
        usernameText.setText(User.getCurrentUser().getUsername());
        profileMenuController = new ProfileMenuController();
        rankingController = new RankingController();
    }

    public void start() {
        URL url = Main.class.getResource("/FXML/MainMenu.fxml");
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

    public void newGame() {
        GameController gameController = new GameController();
        gameController.start();
    }

    public void resumeGame(ActionEvent actionEvent) {
    }

    public void profile(ActionEvent actionEvent) {
        profileMenuController.start();
    }

    public void leaderboard(ActionEvent actionEvent) {
        rankingController.start();
    }

    public void settings() {
        SettingsMenuController settingsMenuController = new SettingsMenuController();
        settingsMenuController.start();
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
