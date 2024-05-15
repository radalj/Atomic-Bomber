package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import model.Game;
import model.Plane;
import model.User;
import view.GameViewController;

public class GameController {
    private Game game;
    private GameViewController gameViewController;
    private Timeline timeline;
    public void start() {
        gameViewController = new GameViewController();
        game = new Game(User.getCurrentUser().getDifficulty(), gameViewController);
        gameViewController.start();
        timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> game.update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        game.addComponents();
        timeline.play();
        boolean arrowKeys = ApplicationController.getArrowKeys();

        gameViewController.scene.setOnKeyPressed(e -> {
            if ((e.getCode() == KeyCode.UP && arrowKeys) || (e.getCode() == KeyCode.W && !arrowKeys)) {
                game.getPlane().changeDirUp();
            }
            if ((e.getCode() == KeyCode.DOWN && arrowKeys) || (e.getCode() == KeyCode.S && !arrowKeys)) {
                game.getPlane().changeDirDown();
            }
            if ((e.getCode() == KeyCode.LEFT && arrowKeys) || (e.getCode() == KeyCode.A && !arrowKeys)) {
                game.getPlane().changeDirLeft();
            }
            if ((e.getCode() == KeyCode.RIGHT && arrowKeys) || (e.getCode() == KeyCode.D && !arrowKeys)) {
                game.getPlane().changeDirRight();
            }
        });
    }
}
