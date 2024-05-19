package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class TankBullet extends Rectangle {
    private double vx, vy;
    private int lifeTime = 120;
    private Game game;
    public TankBullet (Game game, double x, double y, double vx, double vy) {
        super(x, y, 30, 15);
        double magnitude = Math.sqrt(vx * vx + vy * vy);
        vx *= 2 / magnitude;
        vy *= 2 / magnitude;
        this.vx = vx;
        this.vy = vy;
        this.game = game;
        setRotate(Math.toDegrees(Math.atan2(vy, vx)));
        setFill(new ImagePattern(new Image(TankBullet.class.getResource("/images/Game/tankbullet.png").toExternalForm())));
    }

    public boolean move() {
        lifeTime--;
        if (lifeTime == 0) {
            game.removeTankBullet(this);
            return false;
        }
        setX(getX() + vx);
        setY(getY() + vy);
        return true;
    }
}
