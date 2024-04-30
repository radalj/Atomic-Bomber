package controller;

import model.User;
import view.ProfileMenuViewController;
import view.RegisterMenuViewController;

public class RegisterMenuController {
    public static void register(String username, String password) {
        if (User.getUser(username) != null) {
            RegisterMenuViewController.makeAlert("User Already Exists", "User Already Exists", "User with username " + username + " already exists.", "WARNING");
            return;
        }
        if (password.isEmpty()) {
            RegisterMenuViewController.makeAlert("Password Empty", "Password Empty", "Password cannot be empty.", "WARNING");
            return;
        }
        User.addUser(new User(username, password));
        RegisterMenuViewController.makeAlert("User Registered", "User Registered", "User with username " + username + " registered successfully.", "INFORMATION");
    }
    public static void signIn(String username, String password) {
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
        ProfileMenuViewController.start();
    }
    public static void enterAsGuest() {
        User.setCurrentUser(new User("Guest" + User.getGuestNumber(), "password"));
        User.increaseGuestNumber();
        ProfileMenuViewController.start();
    }
}
