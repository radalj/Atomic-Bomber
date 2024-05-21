package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Explosion extends Rectangle {
    private int timeLeftToDisappear = 15;

    public Explosion(double x, double y) {
        super(x, y, 70, 70);
        setFill(new ImagePattern(new Image(Explosion.class.getResource("/images/Game/explosion.png").toExternalForm())));
    }

    public boolean disappear() {
        timeLeftToDisappear--;
        return timeLeftToDisappear == 0;
    }
}
