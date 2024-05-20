package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BurningTank extends Rectangle {
    private int timeLeftToBurn = 100;

    public BurningTank(double x, double y) {
        super(x, y, 70, 70);
        setFill(new ImagePattern(new Image(BurningTank.class.getResource("/images/Game/burningtank.png").toExternalForm())));
    }

    public boolean burn() {
        timeLeftToBurn--;
        return timeLeftToBurn == 0;
    }
}
