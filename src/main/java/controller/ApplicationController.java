package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationController {
    private static Stage stage;
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

}
