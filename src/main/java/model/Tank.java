package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Tank extends Vehicle {
    private static final int killCount = 4;
    public Tank(Game game, boolean enterFromLeft) {
        super(game, enterFromLeft);
        setFill(new ImagePattern(new Image(Tank.class.getResource("/images/Game/tank.png").toExternalForm())));
    }
    public int getKillCount() {
        return killCount;
    }

}
