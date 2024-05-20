package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BurningBuilding extends Rectangle {
    private int timeLeftToBurn = 100;

    public BurningBuilding(double x, double y) {
        super(x, y, 100, 180);
        setFill(new ImagePattern(new Image(BurningBuilding.class.getResource("/images/Game/burningbuilding.png").toExternalForm())));
    }

    public boolean burn() {
        timeLeftToBurn--;
        return timeLeftToBurn == 0;
    }
}
