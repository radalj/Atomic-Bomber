package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class ClusterExplosion extends Rectangle {
    private int timeLeftToDisappear = 15;

    public ClusterExplosion(double x, double y) {
        super(x, y, 70, 70);
        setFill(new ImagePattern(new Image(ClusterExplosion.class.getResource("/images/Game/clusterexplosion.png").toExternalForm())));
    }

    public boolean disappear() {
        timeLeftToDisappear--;
        return timeLeftToDisappear == 0;
    }
}
