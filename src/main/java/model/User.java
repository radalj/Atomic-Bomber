package model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class User {
    private static final ArrayList<User> users = new ArrayList<>();
    private Image image;
    private static User currentUser;
    private String username;
    private String password;
    private static int guestNumber = 0;
    private int score, kills, difficultyKills, wave, numberOfShots, numberOfHits, rank;
    private int difficulty;
    private int radioActiveBombs, clusterBombs;
    private Game game;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        String allImageNames[] = {"man1.jpg", "man2.jpg", "woman1.jpg", "woman2.jpg"};
        Random random = new Random();
        int index = random.nextInt(allImageNames.length);
        String path = "file:/home/radal/Atomic-Bomber/src/main/resources/images/avatars/" + allImageNames[index];
        image = new Image(path);
        score = 0;
        kills = 0;
        difficultyKills = 0;
        numberOfHits = 0;
        numberOfShots = 0;
        wave = 0;
        difficulty = 1;
        radioActiveBombs = 0;
        clusterBombs = 0;
        game = null;
        addUser(this);
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
        sortedUsers.sort(Comparator.comparingInt(User::getScore).thenComparingInt(User::getWave));
        Collections.reverse(sortedUsers);
        return sortedUsers;
    }

    public static ArrayList<User> sortUsersByKills() {
        ArrayList<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort(Comparator.comparingInt(User::getKills).thenComparingInt(User::getWave));
        Collections.reverse(sortedUsers);
        return sortedUsers;
    }

    public static ArrayList<User> sortUsersByDifficultKills() {
        ArrayList<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort(Comparator.comparingInt(User::getDifficultyKills).thenComparingInt(User::getWave));
        Collections.reverse(sortedUsers);
        return sortedUsers;
    }

    public static ArrayList<User> sortUsersByAccuracy() {
        ArrayList<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort(Comparator.comparingDouble(User::getAccuracy).thenComparingInt(User::getWave));
        Collections.reverse(sortedUsers);
        return sortedUsers;
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


    public int getKills() {
        return kills;
    }

    public void increaseKills(int kills) {
        this.kills += kills;
        this.difficultyKills += kills * difficulty;
        this.score += kills * difficulty;
    }

    public int getDifficultyKills() {
        return difficultyKills;
    }

    public int getAccuracy() {
        if (numberOfShots == 0)
            return 0;
        return (numberOfHits * 100 / numberOfShots);
    }

    public void increaseNumberOfShots() {
        numberOfShots++;
    }

    public void increaseNumberOfHits() {
        numberOfHits++;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getRadioActiveBombs() {
        return radioActiveBombs;
    }

    public void setRadioActiveBombs(int radioActiveBombs) {
        this.radioActiveBombs = radioActiveBombs;
        game.getGameController().updateAtomicNumber(radioActiveBombs);
    }

    public int getClusterBombs() {
        return clusterBombs;
    }

    public void setClusterBombs(int clusterBombs) {
        this.clusterBombs = clusterBombs;
        game.getGameController().updateClusterNumber(clusterBombs);
    }

    public void setGame(Game game) {
        this.game = game;
    }
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
}
