package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class AtomicBomb extends Bomb {
    public AtomicBomb(Game game, int x, int y, double vx, double vy, double width, double height) {
        super(game, x, y, vx, vy, width, height);
        setFill(new ImagePattern(new Image(AtomicBomb.class.getResource("/images/Game/atomic.png").toExternalForm())));
        updateRotation();
        this.setRadius(100);
    }

    @Override
    protected boolean checkIntersection(Rectangle rectangle) {
        double dx = ((getX() + getWidth()) - (rectangle.getX() + rectangle.getWidth())) / 2;
        double dy = ((getY() + getHeight()) - (rectangle.getY() + rectangle.getHeight())) / 2;
        return Math.sqrt(dx * dx + dy * dy) < getRadius();
    }

}
