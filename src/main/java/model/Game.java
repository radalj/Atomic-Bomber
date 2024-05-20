package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import view.GameViewController;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private final GameViewController gameViewController;
    private final Random random = new Random();
    private final int difficulty, score = 0, kills = 0;
    private int wave = 1;
    private int timeLeftToMig = 0;
    private final Plane plane = new Plane(this);
    private ArrayList<Tank> tanks = new ArrayList<>();
    private ArrayList<BurningTank> burningTanks = new ArrayList<>();
    private ArrayList<Truck> trucks = new ArrayList<>();
    private ArrayList<BurningTruck> burningTrucks = new ArrayList<>();
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private ArrayList<AtomicBomb> atomicBombs = new ArrayList<>();
    private ArrayList<ClusterBomb> clusterBombs = new ArrayList<>();
    private ArrayList<ClusterBullet> clusterBullets = new ArrayList<>();
    private ArrayList<Circle> circles = new ArrayList<>();
    private ArrayList<TankBullet> tankBullets = new ArrayList<>();
    private Building building;
    private Stronghold stronghold;
    private AtomicIcon atomicIcon = null;
    private ClusterIcon clusterIcon = null;
    private Mig mig = null;
    private int numberOfTanks, numberOfTrucks, numberOfShooterTanks;
    private DoubleProperty freezePercentage;
    private int freezeLeft = 0;
    private ImageView frozenImage;

    public Game(int difficulty, GameViewController gameViewController) {
        this.difficulty = difficulty;
        this.gameViewController = gameViewController;
        numberOfTanks = 3;
        numberOfTrucks = 2;
        numberOfShooterTanks = 0;
        initiateWave();
        freezePercentage = new SimpleDoubleProperty(0);
        frozenImage = new ImageView(new Image(getClass().getResourceAsStream("/images/backgrounds/frozen.png")));
        gameViewController.setFrozenImage(frozenImage);
    }

    private void initiateWave() {
        if (wave == 3) {
            timeLeftToMig = 100;
        }
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
        if (freezeLeft == 100) {
            unFreeze();
            freezeLeft--;
            return;
        }
        if (freezeLeft > 0) {
            freezeLeft--;
            return;
        }
        for (int i = 0; i < burningTanks.size(); i++) {
            BurningTank burningTank = burningTanks.get(i);
            if (burningTank.burn()) {
                gameViewController.removeChild(burningTank);
                burningTanks.remove(burningTank);
                i--;
            }
        }
        for (int i = 0; i < burningTrucks.size(); i++) {
            BurningTruck burningTruck = burningTrucks.get(i);
            if (burningTruck.burn()) {
                gameViewController.removeChild(burningTruck);
                burningTrucks.remove(burningTruck);
                i--;
            }
        }
        if (timeLeftToMig > 0 && mig == null) timeLeftToMig--;
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
        if (mig != null) {
            mig.move();
        }
        if (atomicIcon != null) {
            if (!atomicIcon.move()) {
                gameViewController.removeChild(atomicIcon);
                atomicIcon = null;
            }
        }
        if (clusterIcon != null) {
            if (!clusterIcon.move()) {
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
        circles.add(shooterTank.getCircle());
        gameViewController.addChild(shooterTank);
        gameViewController.addChild(shooterTank.getCircle());
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

    public void addBurningTank(BurningTank burningTank) {
        burningTanks.add(burningTank);
        gameViewController.addChild(burningTank);
    }
    public void addBurningTruck(BurningTruck burningTruck) {
        burningTrucks.add(burningTruck);
        gameViewController.addChild(burningTruck);
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

    public void removeTankBullet(TankBullet tankBullet) {
        tankBullets.remove(tankBullet);
        gameViewController.removeChild(tankBullet);
    }

    public void removeMig(Mig mig) {
        gameViewController.removeChild(mig);
        this.mig = null;
    }
    public void removeCircle(Circle circle) {
        circles.remove(circle);
        gameViewController.removeChild(circle);
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

    public int getWave() {
        return wave;
    }

    public Mig getMig() {
        return mig;
    }

    public void setMig(Mig mig) {
        this.mig = mig;
        gameViewController.addChild(mig);
        timeLeftToMig = 400 - 100 * (difficulty - 1);
    }

    public DoubleProperty getFreezePercentageProperty() {
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
        gameViewController.showFrozenImage();
    }
    private void unFreeze() {
        gameViewController.disableFrozenImage();
    }

    public boolean waveFinished() {
        return tanks.isEmpty() && trucks.isEmpty() && atomicBombs.isEmpty() && clusterBombs.isEmpty() && clusterBullets.isEmpty()
                && tankBullets.isEmpty() && freezeLeft == 0 && building == null && stronghold == null && atomicIcon == null && clusterIcon == null && mig == null
                && circles.isEmpty() && burningTanks.isEmpty() && burningTrucks.isEmpty() && numberOfTanks == 0 && numberOfTrucks == 0 && numberOfShooterTanks == 0;
    }

    public int getTimeLeftToMig() {
        return timeLeftToMig;
    }
}
