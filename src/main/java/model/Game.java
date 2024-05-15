package model;

import controller.ProfileMenuController;
import view.GameViewController;

import java.util.Objects;

public class Game {
    private GameViewController gameViewController;
    private int difficulty;
    private int score;
    private int kills;
    private int wave;
    private Plane plane;
    public Game (int difficulty, GameViewController gameViewController) {
        this.difficulty = difficulty;
        this.gameViewController = gameViewController;
        score = 0;
        kills = 0;
        wave = 1;
        plane = new Plane(this);
    }
    public void addComponents() {
        gameViewController.addChild(plane);
    }

    public void update() {
        plane.move();
    }

    public GameViewController getGameViewController() {
        return gameViewController;
    }
}
