package controller;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.*;
import view.GameViewController;

import java.util.Random;

public class GameController {
    private Game game;
    private GameViewController gameViewController;
    private Timeline moveTimeline, respawnTimeline;
    private final Random random = new Random();
    private double width, height;

    public void start() {
        gameViewController = new GameViewController();
        gameViewController.start();
        width = gameViewController.scene.getWidth();
        height = gameViewController.scene.getHeight();
        game = new Game(User.getCurrentUser().getDifficulty(), this);
        User.getCurrentUser().setGame(game);
        gameViewController.setUpFreezeProgressBar(game.getFreezePercentageProperty());

        moveTimeline = new Timeline(new KeyFrame(Duration.millis(10), e -> game.update()));
        moveTimeline.setCycleCount(Timeline.INDEFINITE);
        moveTimeline.play();
        respawnTimeline = new Timeline(
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
        respawnTimeline.setCycleCount(Timeline.INDEFINITE);
        respawnTimeline.play();

        getInput();
    }

    private void getInput() {
        boolean arrowKeys = ApplicationController.getArrowKeys();

        gameViewController.scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                    if (arrowKeys)
                        game.getPlane().changeDirUp();
                    break;
                case W:
                    if (!arrowKeys)
                        game.getPlane().changeDirUp();
                    break;
                case DOWN:
                    if (arrowKeys)
                        game.getPlane().changeDirDown();
                    break;
                case S:
                    if (!arrowKeys)
                        game.getPlane().changeDirDown();
                    break;
                case LEFT:
                    if (arrowKeys)
                        game.getPlane().changeDirLeft();
                    break;
                case A:
                    if (!arrowKeys)
                        game.getPlane().changeDirLeft();
                    break;
                case RIGHT:
                    if (arrowKeys)
                        game.getPlane().changeDirRight();
                    break;
                case D:
                    if (!arrowKeys)
                        game.getPlane().changeDirRight();
                    break;
                case SPACE:
                    Bomb bomb = new Bomb(game, (int) game.getPlane().getX() + 50, (int) (game.getPlane().getY() + 50), game.getPlane().getVx(), game.getPlane().getVy(), 40, 20);
                    User.getCurrentUser().increaseNumberOfShots();
                    game.increaseWaveShots();
                    game.addBomb(bomb);
                    break;
                case R:
                    if (User.getCurrentUser().getRadioActiveBombs() > 0) {
                        AtomicBomb atomicBomb = new AtomicBomb(game, (int) game.getPlane().getX() + 50, (int) (game.getPlane().getY() + 50), game.getPlane().getVx(), game.getPlane().getVy(), 40, 20);
                        game.addAtomicBomb(atomicBomb);
                        User.getCurrentUser().increaseNumberOfShots();
                        game.increaseWaveShots();
                        User.getCurrentUser().setRadioActiveBombs(User.getCurrentUser().getRadioActiveBombs() - 1);
                    }
                    break;
                case C:
                    if (User.getCurrentUser().getClusterBombs() > 0) {
                        ClusterBomb clusterBomb = new ClusterBomb(game, (int) game.getPlane().getX() + 50, (int) (game.getPlane().getY() + 50), game.getPlane().getVx(), game.getPlane().getVy(), 40, 20);
                        game.addClusterBomb(clusterBomb);
                        User.getCurrentUser().increaseNumberOfShots();
                        game.increaseWaveShots();
                        User.getCurrentUser().setClusterBombs(User.getCurrentUser().getClusterBombs() - 1);
                    }
                    break;
                case TAB:
                    if (game.getFreezePercentageProperty().get() == 1)
                        game.freeze();
                    break;
                case G:
                    User.getCurrentUser().setRadioActiveBombs(User.getCurrentUser().getRadioActiveBombs() + 1);
                    break;
                case CONTROL:
                    User.getCurrentUser().setClusterBombs(User.getCurrentUser().getClusterBombs() + 1);
                    break;
                case T:
                    Tank tank = new Tank(game, random.nextBoolean());
                    tank.setX(random.nextInt() % gameViewController.scene.getWidth());
                    game.addTank(tank);
                    break;
                case P:
                    game.goToNextWave();
                    break;
            }
        });
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void addRectangle(Rectangle rectangle) {
        gameViewController.addChild(rectangle);
    }

    public void addCircle(Circle circle) {
        gameViewController.addChild(circle);
    }

    public void removeRectangle(Rectangle rectangle) {
        gameViewController.removeChild(rectangle);
    }

    public void removeCircle(Circle circle) {
        gameViewController.removeChild(circle);
    }

    public void freeze() {
        gameViewController.showFrozenImage();
    }

    public void unFreeze() {
        gameViewController.disableFrozenImage();
    }

    public void updateAtomicNumber(int atomic) {
        gameViewController.updateAtomicNumber(atomic);
    }

    public void updateClusterNumber(int cluster) {
        gameViewController.updateClusterNumber(cluster);
    }

    public void updateKills(int kills) {
        gameViewController.updateKillNumber(kills);
    }

    public void updateWave(int wave) {
        gameViewController.updateWave(wave);
    }

    public void startBetweenWaves() {
        gameViewController.startBetweenWaves(game.getWave(), game.getWaveAccuracy());
    }

    public void endBetweenWaves() {
        gameViewController.endBetweenWaves();
    }

    public void endGame(boolean win) {
        gameViewController.endGame(game.getKills(), game.getTotalAccuracy(), win);
        User.getCurrentUser().setWave(game.getWave() - 1);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(e -> ApplicationController.setScene(MainMenuController.scene));
        pauseTransition.play();
    }
}
