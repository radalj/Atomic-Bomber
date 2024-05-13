package model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private static final ArrayList<User> users = new ArrayList<>();
    private Image image;
    private static User currentUser;
    private String username;
    private String password;
    private static int guestNumber = 0;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        String path = "file:/home/radal/Atomic-Bomber/src/main/resources/images/avatars/woman1.jpg";
        image = new Image(path);
    }
    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }
    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String text) {
        username = text;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String text) {
        password = text;
    }
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public static int getGuestNumber() {
        return guestNumber;
    }

    public static void increaseGuestNumber() {
        guestNumber++;
    }
}
