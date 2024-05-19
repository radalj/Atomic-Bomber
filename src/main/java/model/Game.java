package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import view.GameViewController;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private final GameViewController gameViewController;
    private final Random random = new Random();
    private final int difficulty;
    private final int score;
    private final int kills;
    private int wave;
    private final Plane plane;
    private ArrayList<Tank> tanks;
    private ArrayList<Truck> trucks;
    private ArrayList<Tree> trees;
    private ArrayList<Bomb> bombs;
    private ArrayList<AtomicBomb> atomicBombs;
    private ArrayList<ClusterBomb> clusterBombs;
    private ArrayList<ClusterBullet> clusterBullets;
    private ArrayList<Circle> circles;
    private ArrayList<TankBullet> tankBullets;
    private Building building;
    private Stronghold stronghold;
    private AtomicIcon atomicIcon;
    private ClusterIcon clusterIcon;
    private int numberOfTanks, numberOfTrucks, numberOfShooterTanks;
    private DoubleProperty freezePercentage;
    private int freezeLeft;

    public Game(int difficulty, GameViewController gameViewController) {
        this.difficulty = difficulty;
        this.gameViewController = gameViewController;
        tanks = new ArrayList<>();
        trucks = new ArrayList<>();
        score = 0;
        kills = 0;
        wave = 1;
        numberOfTanks = 3;
        numberOfTrucks = 2;
        numberOfShooterTanks = 0;
        plane = new Plane(this);
        initiateWave();
        bombs = new ArrayList<>();
        atomicBombs = new ArrayList<>();
        clusterBombs = new ArrayList<>();
        clusterBullets = new ArrayList<>();
        circles = new ArrayList<>();
        tankBullets = new ArrayList<>();
        atomicIcon = null;
        clusterIcon = null;
        freezePercentage = new SimpleDoubleProperty(0);
        freezeLeft = 0;
    }

    private void initiateWave() {
        int buildingX = random.nextInt((int) (gameViewController.scene.getWidth() - 90)) + 20;
        building = new Building(this, buildingX);
        int strongholdX;
        do {
            strongholdX = random.nextInt((int) (gameViewController.scene.getWidth() - 90)) + 20;
        } while ((strongholdX >= buildingX - 50 && strongholdX <= buildingX + 50 + building.getWidth()) || (buildingX >= strongholdX - 50 && buildingX <= strongholdX + 150));
        stronghold = new Stronghold(this, strongholdX);
        int numberOfTrees = random.nextInt(3) + 1;
        trees = new ArrayList<>();
        for (int i = 0; i < numberOfTrees; i++) {
            int treeX = random.nextInt((int) (gameViewController.scene.getWidth() - 90)) + 20;
            trees.add(new Tree(this, treeX));
        }
        addComponents();
    }

    public void addComponents() {
        if (wave == 1) gameViewController.addChild(plane);
        gameViewController.addChild(building);
        gameViewController.addChild(stronghold);
        for (Tree tree : trees) {
            gameViewController.addChild(tree);
        }
    }

    public void update() {
        plane.move();
        if (waveFinished()) {
            wave++;
            gameViewController.updateWave(wave);
            numberOfTanks = 3 * wave;
            numberOfTrucks = 2 * wave;
            numberOfShooterTanks = 2;
            initiateWave();
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (!bomb.move()) i--;
        }
        for (int i = 0; i < atomicBombs.size(); i++) {
            AtomicBomb atomicBomb = atomicBombs.get(i);
            if (!atomicBomb.move()) i--;
        }
        for (int i = 0; i < clusterBombs.size(); i++) {
            ClusterBomb clusterBomb = clusterBombs.get(i);
            if (!clusterBomb.move()) i--;
        }
        for (int i = 0; i < clusterBullets.size(); i++) {
            ClusterBullet clusterBullet = clusterBullets.get(i);
            if (!clusterBullet.move()) i--;
        }
        if (freezeLeft > 0) {
            freezeLeft--;
            return;
        }
        for (Tank tank : tanks) {
            tank.move();
        }
        for (Truck truck : trucks) {
            truck.move();
        }
        for (int i = 0; i < tankBullets.size(); i++) {
            TankBullet tankBullet = tankBullets.get(i);
            if (!tankBullet.move()) i--;
        }
        if (atomicIcon != null) {
            if (!atomicIcon.move()) {
                gameViewController.removeChild(atomicIcon);
                atomicIcon = null;
            }
        }
        if (clusterIcon != null) {
            if (!clusterIcon.move()) {
                System.err.println("Cluster bomb exploded");
                gameViewController.removeChild(clusterIcon);
                clusterIcon = null;
            }
        }
    }

    public GameViewController getGameViewController() {
        return gameViewController;
    }

    public Plane getPlane() {
        return plane;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void addTank(Tank tank) {
        tanks.add(tank);
        gameViewController.addChild(tank);
        numberOfTanks--;
    }

    public void addTruck(Truck truck) {
        trucks.add(truck);
        gameViewController.addChild(truck);
        numberOfTrucks--;
    }

    public int getNumberOfTanks() {
        return numberOfTanks;
    }

    public int getNumberOfTrucks() {
        return numberOfTrucks;
    }

    public int getNumberOfShooterTanks() {
        return numberOfShooterTanks;
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
        gameViewController.addChild(bomb);
    }

    public void addAtomicIcon(double x, double y) {
        atomicIcon = new AtomicIcon(x, y);
        gameViewController.addChild(atomicIcon);
    }
    public void addAtomicBomb(AtomicBomb atomicBomb) {
        atomicBombs.add(atomicBomb);
        gameViewController.addChild(atomicBomb);
    }
    public void addClusterIcon(double x, double y) {
        clusterIcon = new ClusterIcon(x, y);
        gameViewController.addChild(clusterIcon);
    }

    public void addClusterBomb(ClusterBomb clusterBomb) {
        clusterBombs.add(clusterBomb);
        gameViewController.addChild(clusterBomb);
    }

    public void addBullet(ClusterBullet clusterBullet) {
        gameViewController.addChild(clusterBullet);
        clusterBullets.add(clusterBullet);
    }

    public void addShooterTank(ShooterTank shooterTank) {
        tanks.add(shooterTank);
        gameViewController.addChild(shooterTank);
        numberOfShooterTanks--;
    }

    public void addCircle(Circle circle) {
        circles.add(circle);
        gameViewController.addChild(circle);
    }

    public void addTankBullet(TankBullet bullet) {
        tankBullets.add(bullet);
        gameViewController.addChild(bullet);
    }

    public ArrayList<Tank> getTanks() {
        return tanks;
    }

    public ArrayList<Truck> getTrucks() {
        return trucks;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public Building getBuilding() {
        return building;
    }

    public Stronghold getStronghold() {
        return stronghold;
    }

    public void removeTank(Tank tank) {
        tanks.remove(tank);
        gameViewController.removeChild(tank);
        if (tank instanceof ShooterTank) {
            Circle circle = ((ShooterTank) tank).getCircle();
            circles.remove(circle);
            gameViewController.removeChild(circle);
        }
    }
    public void removeTruck(Truck truck) {
        trucks.remove(truck);
        gameViewController.removeChild(truck);
    }

    public void removeTree(Tree tree) {
        trees.remove(tree);
        gameViewController.removeChild(tree);
    }

    public void removeBuilding() {
        gameViewController.removeChild(building);
        building = null;
    }

    public void removeStronghold() {
        gameViewController.removeChild(stronghold);
        stronghold = null;
    }

    public void removeBomb(Bomb bomb) {
        bombs.remove(bomb);
        atomicBombs.remove(bomb);
        clusterBombs.remove(bomb);
        clusterBullets.remove(bomb);
        gameViewController.removeChild(bomb);
    }

    public void removeAtomicIcon() {
        gameViewController.removeChild(atomicIcon);
        atomicIcon = null;
    }

    public void removeClusterIcon() {
        gameViewController.removeChild(clusterIcon);
        clusterIcon = null;
    }
    public int getScore() {
        return score;
    }

    public AtomicIcon getAtomicIcon() {
        return atomicIcon;
    }

    public ClusterIcon getClusterIcon() {
        return clusterIcon;
    }
    public  DoubleProperty getFreezePercentageProperty() {
        return freezePercentage;
    }

    public void incrementFreezePercentage() {
        if (freezePercentage.get() + 0.2 <= 1.0)
            freezePercentage.set(freezePercentage.get() + 0.2);
        else
            freezePercentage.set(1.0);
    }

    public void freeze() {
        freezePercentage.set(0.0);
        freezeLeft = 500;
    }

    public boolean waveFinished() {
        return tanks.isEmpty() && trucks.isEmpty() && atomicBombs.isEmpty() && clusterBombs.isEmpty() && clusterBullets.isEmpty() && freezeLeft == 0 && building == null && stronghold == null;
    }

    public void removeTankBullet(TankBullet tankBullet) {
        tankBullets.remove(tankBullet);
        gameViewController.removeChild(tankBullet);
    }
}
