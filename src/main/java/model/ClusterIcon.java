package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class ClusterIcon extends Rectangle {
    private final double vy = 0.7;
    private final double width = 50, height = 50;

    public ClusterIcon(double x, double y) {
        super(50, 50);
        setX(x);
        setY(y);
        setFill(new ImagePattern(new Image(AtomicIcon.class.getResource("/images/Game/clustericon.png").toExternalForm())));
    }

    public boolean move() {
        setY(getY() - vy);
        return (getY() > -height);
    }
}
