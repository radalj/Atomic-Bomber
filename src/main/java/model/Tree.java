package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Tree extends StaticObjects{
    public Tree(Game game, int x) {
        super(game, x, 150, 100);
        setY(game.getGameController().getHeight() - 200);
        setFill(new ImagePattern(new Image(Tree.class.getResource("/images/Game/treefrozen.png").toExternalForm())));
    }
}
