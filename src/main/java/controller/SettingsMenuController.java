package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import model.User;
import view.Main;

import java.net.URL;

public class SettingsMenuController {
    public static Scene scene;
    @FXML
    private ChoiceBox<String> difficultyLevel;
    @FXML
    private CheckBox soundOn;
    @FXML
    private CheckBox isGrayScale;
    @FXML
    private CheckBox arrowKeys;

    public void initialize() {
        ApplicationController.playMusic();
        difficultyLevel.getItems().addAll("Easy", "Medium", "Hard");
        int difficultyInt = User.getCurrentUser().getDifficulty();
        switch (difficultyInt) {
            case 1:
                difficultyLevel.setValue("Easy");
                break;
            case 2:
                difficultyLevel.setValue("Medium");
                break;
            case 3:
                difficultyLevel.setValue("Hard");
                break;
        }
        difficultyLevel.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                switch (newValue) {
                    case "Easy":
                        User.getCurrentUser().setDifficulty(1);
                        break;
                    case "Medium":
                        User.getCurrentUser().setDifficulty(2);
                        break;
                    case "Hard":
                        User.getCurrentUser().setDifficulty(3);
                        break;
                }
        });
        soundOn.setSelected(ApplicationController.isMusicPlaying());
        soundOn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            ApplicationController.toggleMusic();
        });
        isGrayScale.setSelected(ApplicationController.getGrayScale());
        isGrayScale.selectedProperty().addListener((observable, oldValue, newValue) -> {
            ApplicationController.toggleGrayScale();
        });
        arrowKeys.setSelected(ApplicationController.getArrowKeys());
        arrowKeys.selectedProperty().addListener((observable, oldValue, newValue) -> {
            ApplicationController.toggleArrowKeys();
        });
    }
    public void start() {
        URL url = Main.class.getResource("/FXML/SettingsMenu.fxml");
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

    public void Back() {
        ApplicationController.setScene(MainMenuController.scene);
    }
}
