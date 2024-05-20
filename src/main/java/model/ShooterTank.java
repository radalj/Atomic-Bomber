package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ShooterTank extends Tank{
    private double radius = 110;
    private Circle circle;
    private int reloadTime = 0;
    public ShooterTank(Game game, boolean enterFromLeft) {
        super(game, enterFromLeft);
        radius *= game.getDifficulty();
        circle = new Circle(getX() + this.getWidth() / 2 + 20, getY() + this.getHeight() / 2, radius);
        circle.setStroke(new Color(1, 1, 1, 0.5));
        circle.setFill(Color.TRANSPARENT);
    }
    @Override
    public void move() {
        if (reloadTime > 0)
            reloadTime--;
        super.move();
        circle.setCenterX(getX() + this.getWidth() / 2);
        circle.setCenterY(getY() + this.getHeight() / 2);
        if (isPlaneInCircle() && reloadTime == 0) {
            shoot();
        }
    }

    private void shoot() {
        double vx = game.getPlane().getX() + game.getPlane().getWidth() / 2 - (getX() + getWidth() / 2);
        double vy = game.getPlane().getY() + game.getPlane().getHeight() / 2 - (getY() + getHeight() / 2);
        TankBullet bullet = new TankBullet(game,getX() + getWidth() / 2, getY() + getHeight() / 2, vx, vy);
        game.addTankBullet(bullet);
        reloadTime = 100;
    }

    private boolean isPlaneInCircle() {
        return circle.contains(game.getPlane().getX() + game.getPlane().getWidth() / 2, game.getPlane().getY() + game.getPlane().getHeight() / 2);
    }

    public Circle getCircle() {
        return circle;
    }
}
