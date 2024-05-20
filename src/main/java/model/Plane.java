package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Plane extends Rectangle {
    public static final double MAXSPEED = 2.2;
    private final double width = 120;
    private final double height = 120;
    private double vx, vy;
    private final double rotationSpeed = 0.5;
    private Game game;

    public Plane(Game game) {
        super(120, 120);
        this.game = game;
        setX(0);
        setY(30);
        vx = MAXSPEED;
        vy = 0;
        setFill(new ImagePattern(new Image(Plane.class.getResource("/images/Game/plane.png").toExternalForm())));
    }

    public void move() {
        if (getX() + vx < -width) {
            setX(game.getGameViewController().scene.getWidth() - 1);
        }
        if (getX() + vx > game.getGameViewController().scene.getWidth()) {
            setX(-width + 1);
        }
        if (getY() + vy < 0 || getY() + vy > game.getGameViewController().scene.getHeight() - height - 100) {
            vy = -vy;
            updateRotation();
        }
        setX(getX() + vx);
        setY(getY() + vy);
        checkIntersection();
    }

    private void checkIntersection() {
        if (game.getAtomicIcon() != null && game.getAtomicIcon().getBoundsInParent().intersects(getBoundsInParent())) {
            game.removeAtomicIcon();
            User.getCurrentUser().setRadioActiveBombs(User.getCurrentUser().getRadioActiveBombs() + 1);
            game.getGameViewController().updateAtomicNumber(User.getCurrentUser().getRadioActiveBombs());
        }
        if (game.getClusterIcon() != null && game.getClusterIcon().getBoundsInParent().intersects(getBoundsInParent())) {
            game.removeClusterIcon();
            User.getCurrentUser().setClusterBombs(User.getCurrentUser().getClusterBombs() + 1);
            game.getGameViewController().updateClusterNumber(User.getCurrentUser().getClusterBombs());
        }
    }

    public void changeDirUp() {
        vy -= rotationSpeed;
        double newSpeed = Math.sqrt(vx * vx + vy * vy);
        vx *= MAXSPEED / newSpeed;
        vy *= MAXSPEED / newSpeed;
        updateRotation();
    }

    public void changeDirDown() {
        vy += rotationSpeed;
        double newSpeed = Math.sqrt(vx * vx + vy * vy);
        vx *= MAXSPEED / newSpeed;
        vy *= MAXSPEED / newSpeed;
        updateRotation();
    }

    public void changeDirLeft() {
        vx -= rotationSpeed;
        double newSpeed = Math.sqrt(vx * vx + vy * vy);
        vx *= MAXSPEED / newSpeed;
        vy *= MAXSPEED / newSpeed;
        updateRotation();
    }

    public void changeDirRight() {
        vx += rotationSpeed;
        double newSpeed = Math.sqrt(vx * vx + vy * vy);
        vx *= MAXSPEED / newSpeed;
        vy *= MAXSPEED / newSpeed;
        updateRotation();
    }

    private void updateRotation() {
        double angle;
        if (vx == 0) {
            angle = vy > 0 ? 90 : -90;
        } else {
            angle = Math.toDegrees(Math.atan(vy / vx));
        }
        if (vx < 0) {
            angle += 180;
        }
        setRotate(angle);
        if (vx < 0) {
            setScaleY(-1);
        } else {
            setScaleY(1);
        }
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }
}
