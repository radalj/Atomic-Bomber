package model;

import javafx.scene.shape.Rectangle;

public class Vehicle extends Rectangle {
    protected final double BasicSpeed = 0.5;
    protected final double width = 100, height = 100;
    protected double vx, vy;
    protected Game game;

    public Vehicle(Game game, boolean enterFromLeft) {
        super(70, 70);
        this.game = game;
        if (enterFromLeft) {
            vx = BasicSpeed * game.getDifficulty();
            setX(-width);
            setScaleX(-1);
        } else {
            vx = -BasicSpeed * game.getDifficulty();
            setX(game.getGameViewController().scene.getWidth());
            setScaleX(1);
        }
        setY(game.getGameViewController().scene.getHeight() - 180);
        vy = 0;
    }

    public void move() {
        if (getX() + vx < -width) {
            vx = -vx;
            setScaleX(-1);
        }
        if (getX() + vx > game.getGameViewController().scene.getWidth()) {
            vx = -vx;
            setScaleX(1);
        }
        if (getY() + vy < 0 || getY() + vy > game.getGameViewController().scene.getHeight() - height) {
            vy = -vy;
        }
        setX(getX() + vx);
        setY(getY() + vy);
    }
}
