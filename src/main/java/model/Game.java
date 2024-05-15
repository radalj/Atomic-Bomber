package model;

import view.GameViewController;

import java.util.ArrayList;

public class Game {
    private GameViewController gameViewController;
    private int difficulty;
    private int score;
    private int kills;
    private int wave;
    private Plane plane;
    private ArrayList<Tank> tanks;
    private ArrayList<Truck> trucks;
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
    }

    public void addComponents() {
        gameViewController.addChild(plane);
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
