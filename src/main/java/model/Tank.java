package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Tank extends Vehicle {
    public Tank(Game game, boolean enterFromLeft) {
        super(game, enterFromLeft);
        setFill(new ImagePattern(new Image(Tank.class.getResource("/images/Game/tank.png").toExternalForm())));
    }

}
