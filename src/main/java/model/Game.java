package model;

import view.GameViewController;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private final GameViewController gameViewController;
    private final Random random = new Random();
    private final int difficulty;
    private final int score;
    private final int kills;
    private final int wave;
    private final Plane plane;
    private  ArrayList<Tank> tanks;
    private ArrayList<Truck> trucks;
    private ArrayList<Tree> trees;
    private final Building building;
    private Stronghold stronghold;
    private int numberOfTanks, numberOfTrucks;

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
        plane = new Plane(this);
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
    }

    public void addComponents() {
        gameViewController.addChild(plane);
        gameViewController.addChild(building);
        gameViewController.addChild(stronghold);
        for (Tree tree : trees) {
            gameViewController.addChild(tree);
        }
    }

    public void update() {
        plane.move();
        for (Tank tank : tanks) {
            tank.move();
        }
        for (Truck truck : trucks) {
            truck.move();
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
}
