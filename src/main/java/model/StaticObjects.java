package model;

import javafx.scene.shape.Rectangle;

public class StaticObjects extends Rectangle {
    protected Game game;
    protected double width, height;

    public StaticObjects(Game game, int x, double width, double height) {
        super(width, height);
        this.game = game;
        setX(x);
        setY(game.getGameController().getHeight() - 300);
        this.width = width;
        this.height = height;
    }
}
