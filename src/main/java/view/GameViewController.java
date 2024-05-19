package view;

import controller.ApplicationController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import model.*;

public class GameViewController {
    private Pane root;
    public Scene scene;

    public void start() {
        root = new Pane();
        setPaneSize();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/GameStyle.css").toExternalForm());
        ApplicationController.setScene(scene);
    }

    private void setPaneSize() {
        root.setPrefSize(ApplicationController.getStage().getWidth(), ApplicationController.getStage().getHeight());
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
