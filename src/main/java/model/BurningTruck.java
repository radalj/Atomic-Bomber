package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BurningTruck extends Rectangle {
    private int timeLeftToBurn = 100;
    public BurningTruck(double x, double y) {
        super(x, y, 70, 70);
        setFill(new ImagePattern(new Image(BurningTruck.class.getResource("/images/Game/burningtruck.png").toExternalForm())));
    }
    public boolean burn() {
        timeLeftToBurn--;
        return timeLeftToBurn == 0;
    }
}
