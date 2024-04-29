package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;

public class RegisterMenuViewController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    public void register(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (User.getUser(username) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User Already Exists");
            alert.setHeaderText("User Already Exists");
            alert.setContentText("User with username " + username + " already exists.");
            alert.showAndWait();
            return;
        }
        if (password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Empty");
            alert.setHeaderText("Password Empty");
            alert.setContentText("Password cannot be empty.");
            alert.showAndWait();
            return;
        }
        User.addUser(new User(username, password));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Registered");
        alert.setHeaderText("User Registered");
        alert.setContentText("User with username " + username + " registered successfully.");
        alert.showAndWait();
    }

    public void signIn(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = User.getUser(username);
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User Not Found");
            alert.setHeaderText("User Not Found");
            alert.setContentText("User with username " + username + " not found.");
            alert.showAndWait();
            return;
        }
        if (!user.getPassword().equals(password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Password");
            alert.setHeaderText("Incorrect Password");
            alert.setContentText("Incorrect password for user with username " + username + ".");
            alert.showAndWait();
            return;
        }
        User.setCurrentUser(user);
        //TODO : change scene
    }

    public void enterAsGuest(MouseEvent mouseEvent) {

    }
}
