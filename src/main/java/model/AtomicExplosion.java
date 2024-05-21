package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class AtomicExplosion extends Rectangle {
    private final double bottomY;
    private final double centerX;
    private int timeLeftToDisappear = 150;
    private double width = 50, height = 90;

    public AtomicExplosion(double x, double y) {
        super(x, y, 50, 90);
        bottomY = y + this.getHeight();
        centerX = x + this.getWidth() / 2;
        setFill(new ImagePattern(new Image(AtomicExplosion.class.getResource("/images/Game/atomicexplosion.png").toExternalForm())));
    }

    public boolean disappear() {
        width += 0.75;
        height = width * 1.5;
        setWidth(width);
        setHeight(height);
        setX(centerX - width / 2);
        setY(bottomY - height);
        timeLeftToDisappear--;
        return timeLeftToDisappear == 0;
    }
}
