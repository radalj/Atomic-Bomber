package view;

import controller.ApplicationController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.User;

import java.net.URL;

public class MainMenuViewController {
    public static Scene scene;
    @FXML
    private ImageView avatar;
    @FXML
    private Label usernameText;
    private ProfileMenuViewController profileMenuViewController;
    private RankingViewController rankingViewController;
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
    public void initialize(){
        ApplicationController.playMusic();
        avatar.setImage(User.getCurrentUser().getImage());
        usernameText.setText(User.getCurrentUser().getUsername());
        profileMenuViewController = new ProfileMenuViewController();
        rankingViewController = new RankingViewController();
    }
    public void newGame(ActionEvent actionEvent) {
    }

    public void resumeGame(ActionEvent actionEvent) {
    }

    public void profile(ActionEvent actionEvent) {
        profileMenuViewController.start();
    }

    public void leaderboard(ActionEvent actionEvent) {
        try {
            rankingViewController.start();
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
