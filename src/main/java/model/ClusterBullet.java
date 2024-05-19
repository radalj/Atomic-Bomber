package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class ClusterBullet extends Bomb{
    public ClusterBullet(Game game, int x, int y, double vx, double vy, double width, double height) {
        super(game, x, y, vx, vy, width, height);
        setFill(new ImagePattern(new Image(ClusterBullet.class.getResource("/images/Game/clusterbullet.png").toExternalForm())));
    }
}
