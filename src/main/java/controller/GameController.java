package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import model.*;
import view.GameViewController;

import java.util.Random;

public class GameController {
    private Game game;
    private GameViewController gameViewController;
    private Timeline timeline, tankTimeline;
    private Random random = new Random();
    public void start() {
        gameViewController = new GameViewController();
        gameViewController.start();
        game = new Game(User.getCurrentUser().getDifficulty(), gameViewController);
        timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> game.update()));
        tankTimeline = new Timeline(
                new KeyFrame(Duration.seconds(4), e -> {
                    boolean tankOrTruck = random.nextBoolean();
                    if (game.getNumberOfTanks() > 0 && (tankOrTruck || game.getNumberOfTrucks() == 0)) {
                        Tank tank = new Tank(game, random.nextBoolean());
                        game.addTank(tank);
                    }
                    else if (game.getNumberOfTrucks() > 0) {
                        Truck truck = new Truck(game, random.nextBoolean());
                        game.addTruck(truck);
                    }
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        tankTimeline.setCycleCount(Timeline.INDEFINITE);
        game.addComponents();
        timeline.play();
        tankTimeline.play();
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
