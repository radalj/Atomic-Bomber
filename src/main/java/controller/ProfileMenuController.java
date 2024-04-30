package controller;

import model.User;
import view.ProfileMenuViewController;

public class ProfileMenuController {
    public static void saveUsername(String username) {
        if (User.getUser(username) != null) {
            ProfileMenuViewController.makeAlert("Username Already Exists", "Username Already Exists", "Username" + username + " already exists.", "WARNING");
            return;
        }
        User.getCurrentUser().setUsername(username);
        ProfileMenuViewController.makeAlert("Username Changed", "Username Changed", "Username changed successfully.", "INFORMATION");
    }
    public static void savePassword(String password) {
        User.getCurrentUser().setPassword(password);
        ProfileMenuViewController.makeAlert("Password Changed", "Password Changed", "Password changed successfully.", "INFORMATION");
    }
    public static void removeAccount() {
        User.getUsers().remove(User.getCurrentUser());
        User.setCurrentUser(null);
    }
    public static void signOut() {
        User.setCurrentUser(null);
    }
    public static void avatarMenu() {
        //TODO : change scene
    }
}
