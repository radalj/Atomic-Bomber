package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Truck extends Vehicle {
    private static final int killCount = 3;

    public Truck(Game game, boolean enterFromLeft) {
        super(game, enterFromLeft);
        setFill(new ImagePattern(new Image(Truck.class.getResource("/images/Game/truck.png").toExternalForm())));
    }

    public int getKillCount() {
        return killCount;
    }
}
