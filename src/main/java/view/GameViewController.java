package view;

import controller.ApplicationController;
import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameViewController {
    private final Pane root = new Pane();
    private final Pane backgroundPane = new Pane();
    private final BorderPane overlayPane = new BorderPane();
    private final VBox vBox = new VBox();
    public Scene scene;
    private ProgressBar freezeProgressBar;
    private ImageView frozenImage;
    private Label killNumber, Atomics, Clusters, Wave;
    private Label betweenWavesWave, betweenWavesAccuracy;

    public void start() {
        setPaneSize();
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(backgroundPane);
        root.getChildren().add(overlayPane);
        Wave = new Label("Wave : 1");
        Wave.getStyleClass().add("text-in-game");
        Wave.setLayoutX(550);
        Wave.setLayoutY(0);
        backgroundPane.getChildren().add(Wave);
        killNumber = new Label("Kills : 0");
        killNumber.getStyleClass().add("text-in-game");
        killNumber.setLayoutY(30);
        backgroundPane.getChildren().add(killNumber);
        Atomics = new Label("Atomic Bombs : 0");
        Atomics.getStyleClass().add("text-in-game");
        Atomics.setLayoutY(60);
        backgroundPane.getChildren().add(Atomics);
        Clusters = new Label("Cluster Bombs : 0");
        Clusters.getStyleClass().add("text-in-game");
        Clusters.setLayoutY(90);
        backgroundPane.getChildren().add(Clusters);
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/GameStyle.css").toExternalForm());
        ApplicationController.setScene(scene);
        this.setFrozenImage(new ImageView(new Image(getClass().getResource("/images/backgrounds/frozen.png").toExternalForm())));
    }

    private void setPaneSize() {
        root.setPrefSize(ApplicationController.getStage().getWidth(), ApplicationController.getStage().getHeight());
        backgroundPane.setPrefSize(ApplicationController.getStage().getWidth(), ApplicationController.getStage().getHeight());
        overlayPane.setPrefSize(ApplicationController.getStage().getWidth(), ApplicationController.getStage().getHeight());
    }

    public void setUpFreezeProgressBar(DoubleProperty freezePercentage) {
        freezeProgressBar = new ProgressBar();
        freezeProgressBar.progressProperty().bind(freezePercentage);
        freezeProgressBar.setPrefSize(200, 20);
        backgroundPane.getChildren().add(freezeProgressBar);
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
        backgroundPane.getChildren().add(circle);
        refreshScene();
    }

    public void addChild(Rectangle rectangle) {
        backgroundPane.getChildren().add(rectangle);
        refreshScene();
    }

    public void removeChild(Circle circle) {
        backgroundPane.getChildren().remove(circle);
        refreshScene();
    }

    public void removeChild(Rectangle rectangle) {
        backgroundPane.getChildren().remove(rectangle);
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
        backgroundPane.getChildren().add(frozenImage);
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

    public void startBetweenWaves(int wave, double accuracy) {
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(10);
        backgroundPane.setEffect(blur);

        betweenWavesWave = new Label("Wave " + wave);
        betweenWavesWave.getStyleClass().add("big-text-in-game");
        vBox.getChildren().add(betweenWavesWave);
        betweenWavesAccuracy = new Label("Accuracy : " + accuracy + "%");
        betweenWavesAccuracy.getStyleClass().add("big-text-in-game");
        vBox.getChildren().add(betweenWavesAccuracy);
        overlayPane.setCenter(vBox);
    }

    public void endBetweenWaves() {
        backgroundPane.setEffect(null);
        vBox.getChildren().remove(betweenWavesWave);
        vBox.getChildren().remove(betweenWavesAccuracy);
        overlayPane.setCenter(null);
        betweenWavesWave = null;
        betweenWavesAccuracy = null;
    }

    public void endGame(int kills, double accuracy, boolean win) {
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(10);
        backgroundPane.setEffect(blur);

        vBox.getChildren().clear();
        Label endGameLabel = new Label(win ? "You Won!" : "You Lost!");
        endGameLabel.getStyleClass().add("big-text-in-game");
        vBox.getChildren().add(endGameLabel);
        Label accuracyLabel = new Label("Accuracy : " + accuracy + "%");
        accuracyLabel.getStyleClass().add("big-text-in-game");
        vBox.getChildren().add(accuracyLabel);
        Label killsLabel = new Label("Kills : " + kills);
        killsLabel.getStyleClass().add("big-text-in-game");
        vBox.getChildren().add(killsLabel);
        overlayPane.setCenter(vBox);
    }
}
