package controller;

import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.security.spec.ECField;
import java.util.Objects;

public class ApplicationController {
    private static Stage stage;
    private static MediaPlayer mediaPlayer;

    public static void setStage(Stage stage) {
        ApplicationController.stage = stage;
        stage.setAlwaysOnTop(true);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }
    public static void playMusic() {
        if (mediaPlayer != null) {
            return;
        }
        String path = new File("src/main/resources/sounds/game.wav").toURI().toString();
        Media media = new Media(path);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

}
