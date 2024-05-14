package model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class User {
    private static final ArrayList<User> users = new ArrayList<>();
    private Image image;
    private static User currentUser;
    private String username;
    private String password;
    private static int guestNumber = 0;
    private int score, kills, difficultyKills, wave, rank;
    private double accuracy;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        String path = "file:/home/radal/Atomic-Bomber/src/main/resources/images/avatars/woman1.jpg";
        image = new Image(path);
        score = 0;
        kills = 0;
        difficultyKills = 0;
        accuracy = 0;
        wave = 0;
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
    public static int getGuestNumber() {
        return guestNumber;
    }

    public static void increaseGuestNumber() {
        guestNumber++;
    }
    public static ArrayList<User> sortUsersByScore() {
        ArrayList<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort(Comparator.comparingInt(User::getScore).reversed().thenComparingInt(User::getWave));
        return sortedUsers;
    }

    public static ArrayList<User> sortUsersByKills() {
        ArrayList<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort(Comparator.comparingInt(User::getKills).reversed().thenComparingInt(User::getWave));
        return sortedUsers;
    }

    public static ArrayList<User> sortUsersByDifficultKills() {
        ArrayList<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort(Comparator.comparingInt(User::getDifficultyKills).reversed().thenComparingInt(User::getWave));
        return sortedUsers;
    }

    public static ArrayList<User> sortUsersByAccuracy() {
        ArrayList<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort(Comparator.comparingDouble(User::getAccuracy).reversed().thenComparingInt(User::getWave));
        return sortedUsers;
    }
    public int getRank() {
        return rank;
    }
    public void setRank(int i) {
        this.rank = i;
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
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getKills() {
        return kills;
    }
    public void setKills(int kills) {
        this.kills = kills;
    }
    public int getDifficultyKills() {
        return difficultyKills;
    }
    public void setDifficultyKills(int difficultKills) {
        this.difficultyKills = difficultKills;
    }
    public double getAccuracy() {
        return accuracy;
    }
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }
}
