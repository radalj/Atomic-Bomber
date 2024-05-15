package model;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Bomb extends Rectangle {
    protected final double gravity = 0.04;
    protected Timeline timeline;
    protected Game game;
    protected double radius = 20;
    protected double width, height;
    protected double vx, vy;

    public Bomb(Game game, int x, int y, double vx, double vy, double width, double height) {
        super(width, height);
        this.game = game;
        setX(x);
        setY(y);
        this.vx = vx;
        this.vy = vy;
        this.width = width;
        this.height = height;
        setFill(new ImagePattern(new Image(Bomb.class.getResource("/images/Game/bomb.png").toExternalForm())));
        updateRotation();
    }

    public void move() {
        setX(getX() + vx);
        setY(getY() + vy);
        vy += gravity;
        updateRotation();
        if (getX() < -width || getX() > game.getGameViewController().scene.getWidth()) {
            game.getGameViewController().removeChild(this);
        }
        if (getY() > game.getGameViewController().scene.getHeight() - 160) {
            blast();
        }
    }

    private void updateRotation() {
        double angle;
        if (vx == 0) {
            angle = vy > 0 ? 90 : -90;
        } else {
            angle = Math.toDegrees(Math.atan(vy / vx));
        }
        if (vx < 0) {
            angle += 180;
        }
        setRotate(angle);
        if (vx < 0)
            setScaleY(-1);
        else
            setScaleY(1);
    }

    private void blast() {
        collisionWithBuilding();
        collisionWithStronghold();
        collisionWithTank();
        collisionWithTruck();
        collisionWithTree();
        timeline.stop();
        game.removeBomb(this);
    }

    private void collisionWithBuilding() {
        Building building = game.getBuilding();
        if (building == null)
            return;
        double x = getX();
        if (building.getX() - x < radius && building.getX() + building.getWidth() - x > -radius) {
            game.removeBuilding();
        }
    }

    private void collisionWithStronghold() {
        Stronghold stronghold = game.getStronghold();
        if (stronghold == null)
            return;
        double x = getX();
        if (stronghold.getX() - x < radius && stronghold.getX() + stronghold.getWidth() - x > -radius) {
            game.removeStronghold();
        }
    }

    private void collisionWithTank() {
        ArrayList<Tank> tanks = game.getTanks();
        double x = getX();
        boolean flag = false;
        for (Tank tank : tanks) {
            if (tank.getX() - x < radius && tank.getX() + tank.getWidth() - x > -radius) {
                game.removeTank(tank);
                flag = true;
                break;
            }
        }
        if (flag)
            collisionWithTank();
    }

    private void collisionWithTruck() {
        ArrayList<Truck> trucks = game.getTrucks();
        double x = getX();
        boolean flag = false;
        for (Truck truck : trucks) {
            if (truck.getX() - x < radius && truck.getX() + truck.getWidth() - x > -radius) {
                game.removeTruck(truck);
                flag = true;
                break;
            }
        }
        if (flag)
            collisionWithTruck();
    }

    private void collisionWithTree() {
        ArrayList<Tree> trees = game.getTrees();
        double x = getX();
        boolean flag = false;
        for (Tree tree : trees) {
            if (tree.getX() - x < radius && tree.getX() + tree.getWidth() - x > -radius) {
                game.removeTree(tree);
                flag = true;
                break;
            }
        }
        if (flag)
            collisionWithTree();
    }

    public void setTimeline(Timeline bombTimeline) {
        this.timeline = bombTimeline;
    }
}
