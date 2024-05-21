package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Building extends StaticObjects {
    private static final int killCount = 1;
    public Building(Game game, int x) {
        super(game, x, 100, 180);
        setFill(new ImagePattern(new Image(Building.class.getResource("/images/Game/building.png").toExternalForm())));
    }
    public int getKillCount() {
        return killCount;
    }
}
