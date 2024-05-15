package controller;

import javafx.event.ActionEvent;

public class SettingsMenuController {
    public void Back() {
        ApplicationController.setScene(MainMenuController.scene);
    }
}
