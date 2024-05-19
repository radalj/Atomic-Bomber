package view;

import controller.ApplicationController;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import model.*;

public class GameViewController {
    private Pane root;
    public Scene scene;
    private ProgressBar freezeProgressBar;
    private Label killNumber, Atomics, Clusters, Wave;

    public void start() {
        root = new Pane();
        setPaneSize();
        Wave = new Label("Wave : 1");
        Wave.getStyleClass().add("text-in-game");
        Wave.setLayoutX(550);
        Wave.setLayoutY(0);
        root.getChildren().add(Wave);
        killNumber = new Label("Kills : 0");
        killNumber.getStyleClass().add("text-in-game");
        killNumber.setLayoutY(30);
        root.getChildren().add(killNumber);
        Atomics = new Label("Atomic Bombs : 0");
        Atomics.getStyleClass().add("text-in-game");
        Atomics.setLayoutY(60);
        root.getChildren().add(Atomics);
        Clusters = new Label("Cluster Bombs : 0");
        Clusters.getStyleClass().add("text-in-game");
        Clusters.setLayoutY(90);
        root.getChildren().add(Clusters);
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/GameStyle.css").toExternalForm());
        ApplicationController.setScene(scene);
    }

    private void setPaneSize() {
        root.setPrefSize(ApplicationController.getStage().getWidth(), ApplicationController.getStage().getHeight());
    }
    public void setUpFreezeProgressBar(DoubleProperty freezePercentage) {
        freezeProgressBar = new ProgressBar();
        freezeProgressBar.progressProperty().bind(freezePercentage);
        freezeProgressBar.setPrefSize(200, 20);
        root.getChildren().add(freezeProgressBar);
    }
    public void updateKillNumber(int kills) {
        killNumber.setText("Kills : " + kills);
    }
    public void updateAtomicNumber(int atomic) {
        Atomics.setText("Atomic Bombs : " + atomic);
    }
    public void updateClusterNumber(int cluster) {
        Clusters.setText("Cluster Bombs : " + cluster);
    }
    public void updateWave(int wave) {
        Wave.setText("Wave : " + wave);
    }

    public void addChild(Plane plane) {
        root.getChildren().add(plane);
        refreshScene();
    }

    public void addChild(Vehicle vehicle) {
        root.getChildren().add(vehicle);
        refreshScene();
    }

    public void addChild(StaticObjects staticObject) {
        root.getChildren().add(staticObject);
        refreshScene();
    }

    public void addChild(Bomb bomb) {
        root.getChildren().add(bomb);
        refreshScene();
    }

    public void addChild(AtomicIcon atomicIcon) {
        root.getChildren().add(atomicIcon);
        refreshScene();
    }
    public void addChild(ClusterIcon clusterIcon) {
        root.getChildren().add(clusterIcon);
        refreshScene();
    }

    public void removeChild(Vehicle vehicle) {
        root.getChildren().remove(vehicle);
        refreshScene();
    }

    public void removeChild(StaticObjects staticObject) {
        root.getChildren().remove(staticObject);
        refreshScene();
    }

    public void removeChild(Bomb bomb) {
        root.getChildren().remove(bomb);
        refreshScene();
    }

    public void removeChild(AtomicIcon atomicIcon) {
        root.getChildren().remove(atomicIcon);
        refreshScene();
    }
    public void removeChild(ClusterIcon clusterIcon) {
        root.getChildren().remove(clusterIcon);
        refreshScene();
    }

    private void refreshScene() {
        scene.setRoot(root);
    }
}
