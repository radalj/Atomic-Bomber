package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BurningStronghold extends Rectangle {
    private int timeLeftToBurn = 100;

    public BurningStronghold(double x, double y) {
        super(x, y, 100, 180);
        setFill(new ImagePattern(new Image(BurningStronghold.class.getResource("/images/Game/burningstronghold.png").toExternalForm())));
    }

    public boolean burn() {
        timeLeftToBurn--;
        return timeLeftToBurn == 0;
    }
}
