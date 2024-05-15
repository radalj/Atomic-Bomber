package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.User;
import view.RegisterMenuViewController;

public class RegisterMenuController {
    MainMenuController mainMenuController = new MainMenuController();
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    public void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (User.getUser(username) != null) {
            RegisterMenuViewController.makeAlert("User Already Exists", "User Already Exists", "User with username " + username + " already exists.", "WARNING");
            return;
        }
        if (password.isEmpty()) {
            RegisterMenuViewController.makeAlert("Password Empty", "Password Empty", "Password cannot be empty.", "WARNING");
            return;
        }
        new User(username, password);
        RegisterMenuViewController.makeAlert("User Registered", "User Registered", "User with username " + username + " registered successfully.", "INFORMATION");
    }

    public void signIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = User.getUser(username);
        if (user == null) {
            RegisterMenuViewController.makeAlert("User Not Found", "User Not Found", "User with username " + username + " not found.", "WARNING");
            return;
        }
        if (!user.getPassword().equals(password)) {
            RegisterMenuViewController.makeAlert("Incorrect Password", "Incorrect Password", "Incorrect password for user with username " + username + ".", "WARNING");
            return;
        }
        User.setCurrentUser(user);
        mainMenuController.start();
    }

    public void enterAsGuest() {
        User.setCurrentUser(new User("Guest_" + User.getGuestNumber(), "password"));
        User.increaseGuestNumber();
        mainMenuController.start();
    }
}
