package view;

import controller.ApplicationController;
import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameViewController {
    private Pane root;
    public Scene scene;
    private ProgressBar freezeProgressBar;
    private ImageView frozenImage;
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
        this.setFrozenImage(new ImageView(new Image(getClass().getResource("/images/backgrounds/frozen.png").toExternalForm())));
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

    public void addChild(Circle circle) {
        root.getChildren().add(circle);
        refreshScene();
    }

    public void addChild(Rectangle rectangle) {
        root.getChildren().add(rectangle);
        refreshScene();
    }

    public void removeChild(Circle circle) {
        root.getChildren().remove(circle);
        refreshScene();
    }

    public void removeChild(Rectangle rectangle) {
        root.getChildren().remove(rectangle);
        refreshScene();
    }

    private void refreshScene() {
        scene.setRoot(root);
    }

    public void setFrozenImage(ImageView frozenImage) {
        this.frozenImage = frozenImage;
        frozenImage.setFitWidth(ApplicationController.getStage().getWidth());
        frozenImage.setFitHeight(ApplicationController.getStage().getHeight());
        frozenImage.setOpacity(0);
        root.getChildren().add(frozenImage);
    }

    public void showFrozenImage() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), frozenImage);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(0.5);
        fadeTransition.play();
    }

    public void disableFrozenImage() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), frozenImage);
        fadeTransition.setFromValue(0.5);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }
}
