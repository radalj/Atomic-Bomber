package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Bomb extends Rectangle {
    protected final double gravity = 0.04;
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

    public boolean move() {
        setX(getX() + vx);
        setY(getY() + vy);
        vy += gravity;
        updateRotation();
        if (getX() < -width || getX() > game.getGameViewController().scene.getWidth()) {
            game.removeBomb(this);
            return false;
        }
        if (getY() > game.getGameViewController().scene.getHeight() - 160) {
            blast();
            return false;
        }
        return true;
    }

    protected void updateRotation() {
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
        game.removeBomb(this);
    }

    protected void collisionWithBuilding() {
        Building building = game.getBuilding();
        if (building == null)
            return;
        if (checkIntersection(building)) {
            game.addAtomicIcon(building.getX(), building.getY());
            game.removeBuilding();
            User.getCurrentUser().setKills(User.getCurrentUser().getKills() + 1);
        }
    }

    protected void collisionWithStronghold() {
        Stronghold stronghold = game.getStronghold();
        if (stronghold == null)
            return;
        if (checkIntersection(stronghold)) {
            game.removeStronghold();
            User.getCurrentUser().setKills(User.getCurrentUser().getKills() + 2);
        }
    }

    protected void collisionWithTank() {
        ArrayList<Tank> tanks = game.getTanks();
        double x = getX();
        boolean flag = false;
        for (Tank tank : tanks) {
            if (checkIntersection(tank)) {
                game.removeTank(tank);
                flag = true;
                User.getCurrentUser().setKills(User.getCurrentUser().getKills() + 4);
                break;
            }
        }
        if (flag)
            collisionWithTank();
    }

    protected void collisionWithTruck() {
        ArrayList<Truck> trucks = game.getTrucks();
        double x = getX();
        boolean flag = false;
        for (Truck truck : trucks) {
            if (checkIntersection(truck)) {
                game.removeTruck(truck);
                flag = true;
                User.getCurrentUser().setKills(User.getCurrentUser().getKills() + 3);
                break;
            }
        }
        if (flag)
            collisionWithTruck();
    }

    protected void collisionWithTree() {
        ArrayList<Tree> trees = game.getTrees();
        double x = getX();
        boolean flag = false;
        for (Tree tree : trees) {
            if (checkIntersection(tree)) {
                game.removeTree(tree);
                flag = true;
                break;
            }
        }
        if (flag)
            collisionWithTree();
    }

    protected boolean checkIntersection(Rectangle rectangle) {
        if (this.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
            return true;
        }
        return false;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
