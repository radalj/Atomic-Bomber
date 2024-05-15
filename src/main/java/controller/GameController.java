package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
        timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> game.update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        game.addComponents();
        timeline.play();
    }
}
