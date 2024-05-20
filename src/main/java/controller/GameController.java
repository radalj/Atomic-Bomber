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
    private Timeline planeTimeline, tankTimeline;
    private Random random = new Random();

    public void start() {
        gameViewController = new GameViewController();
        gameViewController.start();
        game = new Game(User.getCurrentUser().getDifficulty(), gameViewController);
        User.getCurrentUser().setGame(game);
        gameViewController.setUpFreezeProgressBar(game.getFreezePercentageProperty());

        planeTimeline = new Timeline(new KeyFrame(Duration.millis(10), e -> game.update()));
        planeTimeline.setCycleCount(Timeline.INDEFINITE);
        planeTimeline.play();
        tankTimeline = new Timeline(
                new KeyFrame(Duration.seconds(4), e -> {
                    boolean tankOrTruck = random.nextBoolean();
                    if (game.getTimeLeftToMig() == 0 && game.getWave() == 3 && game.getMig() == null) {
                        Mig mig = new Mig(game, random.nextBoolean());
                        game.setMig(mig);
                    }
                    if (game.getNumberOfTanks() > 0 && (tankOrTruck || game.getNumberOfTrucks() == 0)) {
                        boolean shooter = random.nextBoolean();
                        if (game.getNumberOfTanks() > 0 && (!shooter || game.getNumberOfShooterTanks() == 0)) {
                            Tank tank = new Tank(game, random.nextBoolean());
                            game.addTank(tank);
                        } else if (game.getNumberOfShooterTanks() > 0) {
                            ShooterTank shooterTank = new ShooterTank(game, random.nextBoolean());
                            game.addShooterTank(shooterTank);
                        }
                    } else if (game.getNumberOfTrucks() > 0) {
                        Truck truck = new Truck(game, random.nextBoolean());
                        game.addTruck(truck);
                    }
                }));
        tankTimeline.setCycleCount(Timeline.INDEFINITE);
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
            if (e.getCode() == KeyCode.SPACE) {
                Bomb bomb = new Bomb(game, (int) game.getPlane().getX() + 50, (int) (game.getPlane().getY() + 50), game.getPlane().getVx(), game.getPlane().getVy(), 40, 20);
                game.addBomb(bomb);
            }
            if (e.getCode() == KeyCode.R && User.getCurrentUser().getRadioActiveBombs() > 0) {
                AtomicBomb atomicBomb = new AtomicBomb(game, (int) game.getPlane().getX() + 50, (int) (game.getPlane().getY() + 50), game.getPlane().getVx(), game.getPlane().getVy(), 40, 20);
                game.addAtomicBomb(atomicBomb);
                User.getCurrentUser().setRadioActiveBombs(User.getCurrentUser().getRadioActiveBombs() - 1);
            }
            if (e.getCode() == KeyCode.C && User.getCurrentUser().getClusterBombs() > 0) {
                ClusterBomb clusterBomb = new ClusterBomb(game, (int) game.getPlane().getX() + 50, (int) (game.getPlane().getY() + 50), game.getPlane().getVx(), game.getPlane().getVy(), 40, 20);
                game.addClusterBomb(clusterBomb);
                User.getCurrentUser().setClusterBombs(User.getCurrentUser().getClusterBombs() - 1);
            }
            if (e.getCode() == KeyCode.TAB && game.getFreezePercentageProperty().get() == 1) {
                game.freeze();
            }
            if (e.getCode() == KeyCode.G) {
                User.getCurrentUser().setRadioActiveBombs(User.getCurrentUser().getRadioActiveBombs() + 1);
            }
            if (e.getCode() == KeyCode.CONTROL) {
                User.getCurrentUser().setClusterBombs(User.getCurrentUser().getClusterBombs() + 1);
            }
            if (e.getCode() == KeyCode.T) {
                game.addTank(new Tank(game, random.nextBoolean()));
            }
        });
    }
}
