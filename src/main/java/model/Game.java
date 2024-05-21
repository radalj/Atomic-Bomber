package model;

import controller.GameController;
import javafx.animation.PauseTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private final GameController gameController;
    private final Random random = new Random();
    private final int difficulty;
    private final int score = 0;
    private int kills = 0;
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
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private ArrayList<AtomicExplosion> atomicExplosions = new ArrayList<>();
    private ArrayList<ClusterExplosion> clusterExplosions = new ArrayList<>();
    private Building building;
    private Stronghold stronghold;
    private AtomicIcon atomicIcon = null;
    private ClusterIcon clusterIcon = null;
    private BurningBuilding burningBuilding = null;
    private BurningStronghold burningStronghold = null;
    private Mig mig = null;
    private int waveHits = 0, waveShots = 0;
    private int totalHits = 0, totalShots = 0;
    private int numberOfTanks, numberOfTrucks, numberOfShooterTanks;
    private DoubleProperty freezePercentage;
    private int freezeLeft = 0;
    private boolean isPauseTransitionRunning = false;

    public Game(int difficulty, GameController gameController) {
        this.difficulty = difficulty;
        this.gameController = gameController;
        numberOfTanks = 3;
        numberOfTrucks = 2;
        numberOfShooterTanks = 0;
        initiateWave();
        freezePercentage = new SimpleDoubleProperty(0);
    }

    private void initiateWave() {
        if (wave == 3) {
            timeLeftToMig = 100;
        }
        int buildingX = random.nextInt((int) (gameController.getWidth() - 90)) + 20;
        building = new Building(this, buildingX);
        int strongholdX;
        do {
            strongholdX = random.nextInt((int) (gameController.getWidth() - 90)) + 20;
        } while ((strongholdX >= buildingX - 50 && strongholdX <= buildingX + 50 + building.getWidth()) || (buildingX >= strongholdX - 50 && buildingX <= strongholdX + 150));
        stronghold = new Stronghold(this, strongholdX);
        int numberOfTrees = random.nextInt(3) + 1;
        trees = new ArrayList<>();
        for (int i = 0; i < numberOfTrees; i++) {
            int treeX = random.nextInt((int) (gameController.getWidth() - 90)) + 20;
            trees.add(new Tree(this, treeX));
        }
        addComponents();
    }

    public void addComponents() {
        if (wave == 1) gameController.addRectangle(plane);
        gameController.addRectangle(building);
        gameController.addRectangle(stronghold);
        for (Tree tree : trees) {
            gameController.addRectangle(tree);
        }
    }

    public void update() {
        if (isPauseTransitionRunning) return;
        if (!plane.move()) {
            gameController.endGame(false);
            return;
        }
        if (waveFinished()) {
            goToNextWave();
            return;
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
        for (int i = 0; i < explosions.size(); i++) {
            Explosion explosion = explosions.get(i);
            if (explosion.disappear()) {
                gameController.removeRectangle(explosion);
                explosions.remove(explosion);
                i--;
            }
        }
        for (int i = 0; i < atomicExplosions.size(); i++) {
            AtomicExplosion atomicExplosion = atomicExplosions.get(i);
            if (atomicExplosion.disappear()) {
                gameController.removeRectangle(atomicExplosion);
                atomicExplosions.remove(atomicExplosion);
                i--;
            }
        }
        for (int i = 0; i < clusterExplosions.size(); i++) {
            ClusterExplosion clusterExplosion = clusterExplosions.get(i);
            if (clusterExplosion.disappear()) {
                gameController.removeRectangle(clusterExplosion);
                clusterExplosions.remove(clusterExplosion);
                i--;
            }
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
                gameController.removeRectangle(burningTank);
                burningTanks.remove(burningTank);
                i--;
            }
        }
        for (int i = 0; i < burningTrucks.size(); i++) {
            BurningTruck burningTruck = burningTrucks.get(i);
            if (burningTruck.burn()) {
                gameController.removeRectangle(burningTruck);
                burningTrucks.remove(burningTruck);
                i--;
            }
        }
        if (burningBuilding != null) {
            if (burningBuilding.burn()) {
                gameController.removeRectangle(burningBuilding);
                burningBuilding = null;
            }
        }
        if (burningStronghold != null) {
            if (burningStronghold.burn()) {
                gameController.removeRectangle(burningStronghold);
                burningStronghold = null;
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
                gameController.removeRectangle(atomicIcon);
                atomicIcon = null;
            }
        }
        if (clusterIcon != null) {
            if (!clusterIcon.move()) {
                gameController.removeRectangle(clusterIcon);
                clusterIcon = null;
            }
        }
    }

    public GameController getGameController() {
        return gameController;
    }

    public Plane getPlane() {
        return plane;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void addTank(Tank tank) {
        tanks.add(tank);
        gameController.addRectangle(tank);
        numberOfTanks--;
    }

    public void addTruck(Truck truck) {
        trucks.add(truck);
        gameController.addRectangle(truck);
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
        gameController.addRectangle(bomb);
    }

    public void addAtomicIcon(double x, double y) {
        atomicIcon = new AtomicIcon(x, y);
        gameController.addRectangle(atomicIcon);
    }

    public void addAtomicBomb(AtomicBomb atomicBomb) {
        atomicBombs.add(atomicBomb);
        gameController.addRectangle(atomicBomb);
    }

    public void addClusterIcon(double x, double y) {
        clusterIcon = new ClusterIcon(x, y);
        gameController.addRectangle(clusterIcon);
    }

    public void addClusterBomb(ClusterBomb clusterBomb) {
        clusterBombs.add(clusterBomb);
        gameController.addRectangle(clusterBomb);
    }

    public void addBullet(ClusterBullet clusterBullet) {
        gameController.addRectangle(clusterBullet);
        clusterBullets.add(clusterBullet);
    }

    public void addShooterTank(ShooterTank shooterTank) {
        tanks.add(shooterTank);
        circles.add(shooterTank.getCircle());
        gameController.addRectangle(shooterTank);
        gameController.addCircle(shooterTank.getCircle());
        numberOfShooterTanks--;
    }

    public void addCircle(Circle circle) {
        circles.add(circle);
        gameController.addCircle(circle);
    }

    public void addTankBullet(TankBullet bullet) {
        tankBullets.add(bullet);
        gameController.addRectangle(bullet);
    }

    public void addBurningTank(BurningTank burningTank) {
        burningTanks.add(burningTank);
        gameController.addRectangle(burningTank);
    }

    public void addBurningTruck(BurningTruck burningTruck) {
        burningTrucks.add(burningTruck);
        gameController.addRectangle(burningTruck);
    }

    public void addExplosion(Explosion explosion) {
        explosions.add(explosion);
        gameController.addRectangle(explosion);
    }

    public void addBurningBuilding(BurningBuilding burningBuilding) {
        this.burningBuilding = burningBuilding;
        gameController.addRectangle(burningBuilding);
    }

    public void addBurningStronghold(BurningStronghold burningStronghold) {
        this.burningStronghold = burningStronghold;
        gameController.addRectangle(burningStronghold);
    }

    public void addAtomicExplosion(AtomicExplosion atomicExplosion) {
        atomicExplosions.add(atomicExplosion);
        gameController.addRectangle(atomicExplosion);
    }

    public void addClusterExplosion(ClusterExplosion clusterExplosion) {
        clusterExplosions.add(clusterExplosion);
        gameController.addRectangle(clusterExplosion);
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
        gameController.removeRectangle(tank);
        if (tank instanceof ShooterTank) {
            Circle circle = ((ShooterTank) tank).getCircle();
            circles.remove(circle);
            gameController.removeCircle(circle);
        }
    }

    public void removeTruck(Truck truck) {
        trucks.remove(truck);
        gameController.removeRectangle(truck);
    }

    public void removeTree(Tree tree) {
        trees.remove(tree);
        gameController.removeRectangle(tree);
    }

    public void removeBuilding() {
        gameController.removeRectangle(building);
        building = null;
    }

    public void removeStronghold() {
        gameController.removeRectangle(stronghold);
        stronghold = null;
    }

    public void removeBomb(Bomb bomb) {
        bombs.remove(bomb);
        atomicBombs.remove(bomb);
        clusterBombs.remove(bomb);
        clusterBullets.remove(bomb);
        gameController.removeRectangle(bomb);
    }

    public void removeAtomicIcon() {
        gameController.removeRectangle(atomicIcon);
        atomicIcon = null;
    }

    public void removeClusterIcon() {
        gameController.removeRectangle(clusterIcon);
        clusterIcon = null;
    }

    public void removeTankBullet(TankBullet tankBullet) {
        tankBullets.remove(tankBullet);
        gameController.removeRectangle(tankBullet);
    }

    public void removeMig(Mig mig) {
        gameController.removeRectangle(mig);
        this.mig = null;
    }

    public void removeCircle(Circle circle) {
        circles.remove(circle);
        gameController.removeCircle(circle);
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
        gameController.addRectangle(mig);
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
        gameController.freeze();
    }

    private void unFreeze() {
        gameController.unFreeze();
    }

    public int getTimeLeftToMig() {
        return timeLeftToMig;
    }

    public boolean waveFinished() {
        return tanks.isEmpty() && trucks.isEmpty() && atomicBombs.isEmpty() && clusterBombs.isEmpty() && clusterBullets.isEmpty()
                && tankBullets.isEmpty() && freezeLeft == 0 && building == null && stronghold == null && atomicIcon == null && clusterIcon == null && mig == null
                && circles.isEmpty() && burningTanks.isEmpty() && atomicExplosions.isEmpty() && clusterExplosions.isEmpty() &&
                burningTrucks.isEmpty() && numberOfTanks == 0 && numberOfTrucks == 0 && numberOfShooterTanks == 0;
    }

    public void goToNextWave() {
        if (isPauseTransitionRunning) return;
        clearEverything();
        gameController.startBetweenWaves();
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            wave++;
            if (wave == 4) {
                gameController.endGame(true);
                return;
            }
            numberOfTanks = 3 * wave;
            numberOfTrucks = 2 * wave;
            numberOfShooterTanks = 2;
            gameController.updateWave(wave);
            initiateWave();
            isPauseTransitionRunning = false;
            gameController.endBetweenWaves();
            waveHits = 0;
            waveShots = 0;
        });
        isPauseTransitionRunning = true;
        pause.play();
    }

    private void clearEverything() {
        while (!tanks.isEmpty()) {
            Tank tank = tanks.get(0);
            tanks.remove(tank);
            gameController.removeRectangle(tank);
        }
        while (!trucks.isEmpty()) {
            Truck truck = trucks.get(0);
            trucks.remove(truck);
            gameController.removeRectangle(truck);
        }
        while (!trees.isEmpty()) {
            Tree tree = trees.get(0);
            trees.remove(tree);
            gameController.removeRectangle(tree);
        }
        while (!bombs.isEmpty()) {
            Bomb bomb = bombs.get(0);
            bombs.remove(bomb);
            gameController.removeRectangle(bomb);
        }
        while (!atomicBombs.isEmpty()) {
            AtomicBomb atomicBomb = atomicBombs.get(0);
            atomicBombs.remove(atomicBomb);
            gameController.removeRectangle(atomicBomb);
        }
        while (!clusterBombs.isEmpty()) {
            ClusterBomb clusterBomb = clusterBombs.get(0);
            clusterBombs.remove(clusterBomb);
            gameController.removeRectangle(clusterBomb);
        }
        while (!clusterBullets.isEmpty()) {
            ClusterBullet clusterBullet = clusterBullets.get(0);
            clusterBullets.remove(clusterBullet);
            gameController.removeRectangle(clusterBullet);
        }
        while (!tankBullets.isEmpty()) {
            TankBullet tankBullet = tankBullets.get(0);
            tankBullets.remove(tankBullet);
            gameController.removeRectangle(tankBullet);
        }
        while (!circles.isEmpty()) {
            Circle circle = circles.get(0);
            circles.remove(circle);
            gameController.removeCircle(circle);
        }
        while (!burningTanks.isEmpty()) {
            BurningTank burningTank = burningTanks.get(0);
            burningTanks.remove(burningTank);
            gameController.removeRectangle(burningTank);
        }
        while (!burningTrucks.isEmpty()) {
            BurningTruck burningTruck = burningTrucks.get(0);
            burningTrucks.remove(burningTruck);
            gameController.removeRectangle(burningTruck);
        }
        while (!explosions.isEmpty()) {
            Explosion explosion = explosions.get(0);
            explosions.remove(explosion);
            gameController.removeRectangle(explosion);
        }
        while (!atomicExplosions.isEmpty()) {
            AtomicExplosion atomicExplosion = atomicExplosions.get(0);
            atomicExplosions.remove(atomicExplosion);
            gameController.removeRectangle(atomicExplosion);
        }
        while (!clusterExplosions.isEmpty()) {
            ClusterExplosion clusterExplosion = clusterExplosions.get(0);
            clusterExplosions.remove(clusterExplosion);
            gameController.removeRectangle(clusterExplosion);
        }
        if (burningBuilding != null) {
            gameController.removeRectangle(burningBuilding);
            burningBuilding = null;
        }
        if (burningStronghold != null) {
            gameController.removeRectangle(burningStronghold);
            burningStronghold = null;
        }
        freezeLeft = 0;
        if (building != null) {
            gameController.removeRectangle(building);
            building = null;
        }
        if (stronghold != null) {
            gameController.removeRectangle(stronghold);
            stronghold = null;
        }
        if (atomicIcon != null) {
            gameController.removeRectangle(atomicIcon);
            atomicIcon = null;
        }
        if (clusterIcon != null) {
            gameController.removeRectangle(clusterIcon);
            clusterIcon = null;
        }
        if (mig != null) {
            gameController.removeRectangle(mig);
            mig = null;
        }
        numberOfTanks = 0;
        numberOfTrucks = 0;
        numberOfShooterTanks = 0;
    }

    public void increaseWaveHits() {
        waveHits++;
        totalHits++;
    }

    public void increaseWaveShots() {
        waveShots++;
        totalShots++;
    }

    public int getWaveAccuracy() {
        if (waveShots == 0) return 0;
        return waveHits * 100 / waveShots;
    }

    public int getTotalAccuracy() {
        if (totalShots == 0) return 0;
        return totalHits * 100 / totalShots;
    }

    public int getKills() {
        return kills;
    }

    public void increaseKills(int killCount) {
        kills += killCount;
        gameController.updateKills(kills);
    }
}
