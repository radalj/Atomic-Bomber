package view;

import controller.ApplicationController;
import controller.RankingController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import model.User;

import java.net.URL;

public class RankingViewController {
    public static Scene scene;
    public TableView<User> rankingTable;
    public ChoiceBox<String> sortType;

    RankingController rankingController;

    public void initialize(){
        ApplicationController.playMusic();
        rankingController = new RankingController();
        rankingController.buildRanking(rankingTable);

        rankingTable.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    if (getIndex() == 0 && !empty) {
                        setStyle("-fx-background-color: #FFD700;");
                    } else if (getIndex() == 1 && !empty) {
                        setStyle("-fx-background-color: #C0C0C0;");
                    } else if (getIndex() == 2 && !empty) {
                        setStyle("-fx-background-color: #CD7F32;");
                    } else {
                        setStyle("");
                    }
                }
            };
            row.setPrefHeight(50);
            return row;
        });
        sortType.getItems().addAll("Score", "Kills", "Difficulty Kills", "Accuracy");
        sortType.setValue("Score");
        sortType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Score")) {
                rankingController.setSortType(0);
            } else if (newValue.equals("Kills")) {
                rankingController.setSortType(1);
            } else if (newValue.equals("Difficulty Kills")) {
                rankingController.setSortType(2);
            } else {
                rankingController.setSortType(3);
            }
            rankingController.buildRanking(rankingTable);
        });
    }

    public void start() {
        URL url = Main.class.getResource("/FXML/Ranking.fxml");
        assert url != null;
        Pane root;
        try {
            root = FXMLLoader.load(url);
        } catch (Exception e) {
            return;
        }
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/styles.css").toExternalForm());
        ApplicationController.setScene(scene);
    }

    public void back(ActionEvent actionEvent) {
        ApplicationController.setScene(MainMenuViewController.scene);
    }
}
