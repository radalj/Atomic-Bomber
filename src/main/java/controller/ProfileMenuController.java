package controller;

import model.User;
import view.AvatarMenuViewController;
import view.ProfileMenuViewController;
import view.RegisterMenuViewController;

public class ProfileMenuController {
    private AvatarMenuViewController avatarMenuViewController = new AvatarMenuViewController();
    public void saveUsername(String username) {
        if (User.getUser(username) != null) {
            ProfileMenuViewController.makeAlert("Username Already Exists", "Username Already Exists", "Username" + username + " already exists.", "WARNING");
            return;
        }
        User.getCurrentUser().setUsername(username);
        ProfileMenuViewController.makeAlert("Username Changed", "Username Changed", "Username changed successfully.", "INFORMATION");
    }
    public void savePassword(String password) {
        User.getCurrentUser().setPassword(password);
        ProfileMenuViewController.makeAlert("Password Changed", "Password Changed", "Password changed successfully.", "INFORMATION");
    }
    public void removeAccount() {
        User.getUsers().remove(User.getCurrentUser());
        signOut();
    }
    public void signOut() {
        User.setCurrentUser(null);
        ApplicationController.setScene(RegisterMenuViewController.scene);
    }
    public void avatarMenu() {
        avatarMenuViewController.start();
    }
}
