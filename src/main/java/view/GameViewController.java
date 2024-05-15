package view;

import controller.ApplicationController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import model.Plane;
import model.StaticObjects;
import model.Vehicle;

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

    private void refreshScene() {
        scene.setRoot(root);
    }
}
