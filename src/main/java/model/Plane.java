package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Plane extends Rectangle {
    public static final double MAX_SPEED = 2.5;
    private final double width = 150;
    private final double height = 150;
    private double vx, vy;
    private final double rotationSpeed = 2;
    private double tetha;
    private Game game;
    public Plane(Game game) {
        super(150, 150);
        this.game = game;
        setX(0);
        setY(30);
        vx = MAX_SPEED;
        vy = 0;
        tetha = 0;
        setFill(new ImagePattern(new Image(Plane.class.getResource("/images/Game/plane.png").toExternalForm())));
    }
    public void move() {
        if (getX() + vx < -width) {
            setX(game.getGameViewController().scene.getWidth() - 1);
        }
        if (getX() + vx > game.getGameViewController().scene.getWidth()) {
            setX(-width + 1);
        }
        if (getY() + vy < 0 || getY() + vy > game.getGameViewController().scene.getHeight() - height) {
            vy = -vy;
            updateRotation();
        }
        setX(getX() + vx);
        setY(getY() + vy);
    }
    public void changeDirUp () {
        vy -= rotationSpeed;
        double newSpeed = Math.sqrt(vx * vx + vy * vy);
        vx *= MAX_SPEED / newSpeed;
        vy *= MAX_SPEED / newSpeed;
        updateRotation();
    }

    public void changeDirDown () {
        vy += rotationSpeed;
        double newSpeed = Math.sqrt(vx * vx + vy * vy);
        vx *= MAX_SPEED / newSpeed;
        vy *= MAX_SPEED / newSpeed;
        updateRotation();
    }

    public void changeDirLeft () {
        vx -= rotationSpeed;
        double newSpeed = Math.sqrt(vx * vx + vy * vy);
        vx *= MAX_SPEED / newSpeed;
        vy *= MAX_SPEED / newSpeed;
        updateRotation();
    }
    public void changeDirRight () {
        vx += rotationSpeed;
        double newSpeed = Math.sqrt(vx * vx + vy * vy);
        vx *= MAX_SPEED / newSpeed;
        vy *= MAX_SPEED / newSpeed;
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
    }
}
