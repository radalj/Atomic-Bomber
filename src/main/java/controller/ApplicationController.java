package controller;

import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.security.spec.ECField;
import java.util.Objects;

public class ApplicationController {
    private static Stage stage;
    private static MediaPlayer mediaPlayer;
    private static boolean isMusicPlaying = true;
    private static boolean isGrayScale = false;
    private static boolean arrowKeys = true;

    public static void setStage(Stage stage) {
        ApplicationController.stage = stage;
        stage.setAlwaysOnTop(true);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setScene(Scene scene) {
        if (isGrayScale) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(-1);
            scene.getRoot().setEffect(colorAdjust);
        } else {
            scene.getRoot().setEffect(null);
        }
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
    public static boolean isMusicPlaying() {
        return isMusicPlaying;
    }
    public static void toggleMusic() {
        if (isMusicPlaying) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
        isMusicPlaying = !isMusicPlaying;
    }
    public static void toggleGrayScale() {
        isGrayScale = !isGrayScale;
        if (isGrayScale) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(-0.7);
            stage.getScene().getRoot().setEffect(colorAdjust);
        } else {
            stage.getScene().getRoot().setEffect(null);
        }
    }
    public static boolean getGrayScale() {
        return isGrayScale;
    }
    public static void toggleArrowKeys() {
        arrowKeys = !arrowKeys;
    }
    public static boolean getArrowKeys() {
        return arrowKeys;
    }

}
