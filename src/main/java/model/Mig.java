package model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Mig extends Rectangle {
    private double vx = 0.80;
    private double radius = 110;
    private int reloadTime = 0;
    private Circle circle;
    private Game game;
    public Mig (Game game, Boolean enterFromLeft) {
        super(120, 30);
        setFill(new ImagePattern(new Image(Plane.class.getResource("/images/Game/mig.png").toExternalForm())));
        this.game = game;
        radius *= game.getDifficulty();
        vx *= game.getDifficulty();
        if (enterFromLeft) {
            setX(0);
            setY(20);
        } else {
            setX(game.getGameViewController().scene.getWidth() - 1);
            setY(20);
            setScaleX(-1);
            vx = -vx;
        }
        circle = new Circle(getX() + getWidth() / 2, getY() + getHeight() / 2, radius);
        circle.setStroke(new Color(1, 1, 1, 0.5));
        circle.setFill(Color.TRANSPARENT);
        game.addCircle(circle);
    }


    public void move() {
        if (reloadTime > 0)
            reloadTime--;
        if (getX() + vx < -getWidth() - circle.getRadius() || getX() + vx > game.getGameViewController().scene.getWidth() + circle.getRadius()) {
            game.removeMig(this);
            game.removeCircle(circle);
        }
        setX(getX() + vx);
        circle.setCenterX(getX() + getWidth() / 2);
        if (isPlaneInCircle() && reloadTime == 0) {
            shoot();
        }
    }
    private void shoot() {
        double x = getX() + getWidth() / 2 + vx * 4;
        double y = getY() + getHeight() / 2;
        double planeX = game.getPlane().getX() + game.getPlane().getWidth() / 2 + 100 / game.getDifficulty() * game.getPlane().getVx();
        double planeY = game.getPlane().getY() + game.getPlane().getHeight() / 2 + 100 / game.getDifficulty() * game.getPlane().getVy();
        double vx = planeX - x;
        double vy = planeY - y;
        TankBullet bullet = new TankBullet(game, x, y, vx, vy);
        game.addTankBullet(bullet);
        reloadTime = 100;
    }
    private boolean isPlaneInCircle() {
        return circle.contains(game.getPlane().getX() + game.getPlane().getWidth() / 2, game.getPlane().getY() + game.getPlane().getHeight() / 2);
    }
    public Circle getCircle() {
        return circle;
    }
}
