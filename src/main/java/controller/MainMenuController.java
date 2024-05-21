package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.User;
import view.Main;

import java.net.URL;
import java.util.Objects;

public class MainMenuController {
    public static Scene scene;
    private ImageView avatar;
    @FXML
    private Label usernameText;
    private ProfileMenuController profileMenuController;
    private RankingController rankingController;

    public void initialize() {
        ApplicationController.playMusic();
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
        avatar = new ImageView(User.getCurrentUser().getImage());
        avatar.setFitHeight(200);
        avatar.setFitWidth(200);
        avatar.setLayoutX(265);
        avatar.setLayoutY(250);
        root.getChildren().add(avatar);
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/Styles.css").toExternalForm());
        ApplicationController.setScene(scene);
    }

    public void newGame() {
        GameController gameController = new GameController();
        gameController.start();
    }

    public void resumeGame() {
    }

    public void profile() {
        profileMenuController.start(this);
    }

    public void leaderboard() {
        rankingController.start();
    }

    public void settings() {
        SettingsMenuController settingsMenuController = new SettingsMenuController();
        settingsMenuController.start();
    }

    public void exit() {
        Platform.exit();
    }
    public void changeAvatar() {
        Image image = User.getCurrentUser().getImage();
        System.err.println(image.getUrl());
        avatar.setImage(User.getCurrentUser().getImage());
    }
}
