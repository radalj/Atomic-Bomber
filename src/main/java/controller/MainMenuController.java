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
        scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());
        ApplicationController.setScene(scene);
    }

    public void initialize() {
        ApplicationController.playMusic();
        avatar.setImage(User.getCurrentUser().getImage());
        usernameText.setText(User.getCurrentUser().getUsername());
        profileMenuController = new ProfileMenuController();
        rankingController = new RankingController();
    }

    public void newGame(ActionEvent actionEvent) {
    }

    public void resumeGame(ActionEvent actionEvent) {
    }

    public void profile(ActionEvent actionEvent) {
        profileMenuController.start();
    }

    public void leaderboard(ActionEvent actionEvent) {
        try {
            rankingController.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("fad");
        }
    }

    public void settings(ActionEvent actionEvent) {
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
