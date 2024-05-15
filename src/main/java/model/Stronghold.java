package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Stronghold extends StaticObjects {
    public Stronghold(Game game, int x) {
        super(game, x, 100, 180);
        setFill(new ImagePattern(new Image(Stronghold.class.getResource("/images/Game/stronghold.png").toExternalForm())));
    }
}
